package org.unibl.etf.controllers;

import com.itextpdf.text.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.models.entities.MuseumEntity;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.requests.MuseumRequest;
import org.unibl.etf.models.requests.PurchaseRequest;
import org.unibl.etf.models.responses.VirtualVisitResponse;
import org.unibl.etf.services.LogService;
import org.unibl.etf.services.MuseumService;
import org.unibl.etf.services.VirtualBankService;
import org.unibl.etf.services.VirtualVisitService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/museums")
public class MuseumController {

    private final MuseumService museumService;
    private final VirtualBankService virtualBankService;
    private final LogService logService;
    private final VirtualVisitService virtualVisitService;
    private final ModelMapper modelMapper;

    public MuseumController(MuseumService museumService, VirtualBankService virtualBankService, LogService logService, VirtualVisitService virtualVisitService, ModelMapper modelMapper) {
        this.museumService = museumService;
        this.virtualBankService = virtualBankService;
        this.logService = logService;
        this.virtualVisitService = virtualVisitService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<MuseumEntity> addMuseum(@RequestBody MuseumEntity museum, Authentication principal) {
        logService.log(principal.getName(), "ADD_MUSEUM", "User added museum: " + museum.getName(),
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(museumService.addMuseum(museum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editMuseum(@PathVariable Integer id, @RequestBody @Valid MuseumRequest museumRequest,
                                        Authentication principal) {
        MuseumEntity museum = modelMapper.map(museumRequest, MuseumEntity.class);
        museum.setId(id);
        museumService.editMuseum(museum);
        logService.log(principal.getName(),"EDIT_MUSEUM", "User edited museum: " + museum.getName(), new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMuseum(@PathVariable Integer id, Authentication principal) {
        museumService.deleteMuseum(id);
        logService.log(principal.getName(),"DELETE_MUSEUM", "User deleted museum with ID: " + id, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MuseumEntity>> getAllMuseums(Authentication principal) {

        logService.log(principal.getName(),"LIST_MUSEUMS", "User listed museums", new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(museumService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuseumEntity> getMuseumById(@PathVariable Integer id, Authentication principal) {
        logService.log(principal.getName(),"GET_MUSEUM", "User requested museum by id: " + id, new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(museumService.getMuseumById(id));
    }

    @PostMapping("/{id}/visits")
    public ResponseEntity<VirtualVisitResponse> addVirtualVisit(
            @PathVariable("id") Integer museumId,
            @RequestParam("files[]") MultipartFile[] images,
            @RequestParam("date") String date,
            @RequestParam("startTime") String startTime,
            @RequestParam("duration") Integer duration,
            @RequestParam("price") Double price,
            @RequestParam(value = "mp4Video", required = false) MultipartFile mp4Video,
            @RequestParam(value = "ytVideo", required = false) String ytVideoLink,
            Authentication principal
    ) throws ParseException, IOException {

        VirtualVisitEntity virtualVisitRequest = new VirtualVisitEntity();

        virtualVisitRequest.setDate(Date.valueOf(date));
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        long ms = sdf.parse(startTime).getTime();
        virtualVisitRequest.setStartTime(new Time(ms));

        virtualVisitRequest.setDuration(duration);
        virtualVisitRequest.setPrice(price);

        MuseumEntity museum = new MuseumEntity();
        museum.setId(museumId);
        virtualVisitRequest.setMuseum(museum);

        logService.log(principal.getName(),"ADD_VISIT", "User added virtual visit in museum: " + museumId, new Timestamp(System.currentTimeMillis()));

        return ResponseEntity.ok(virtualVisitService.addVirtualVisit(virtualVisitRequest, images, mp4Video, ytVideoLink));
    }

    @GetMapping("/{id}/visits")
    public ResponseEntity<List<VirtualVisitResponse>> getFutureAndActiveVirtualVisitsInMuseum(@PathVariable Integer id, Authentication principal) {
        logService.log(principal.getName(),"GET_VISITS", "User requested future and active visits of museum with id:  " + id, new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(museumService.getFutureAndActiveVirtualVisitsInMuseum(id));
    }

    @PostMapping("/{museum_id}/visits/{visit_id}")
    @ResponseBody
    public ResponseEntity<?> purchaseTicket(@RequestBody @Valid PurchaseRequest purchaseRequest,
                                            @PathVariable Integer museum_id,
                                            @PathVariable Integer visit_id,
                                            HttpServletResponse response,
                                            Authentication principal) throws IOException, DocumentException, MessagingException {
//        response.setHeader("Location", "http://localhost:8080/api/bank/accounts/payment");
//        response.setStatus(307); //307 resends POSt, 302 sends GetMapping
        virtualBankService.requestPurchase(purchaseRequest);
        museumService.processPurchase(museum_id, visit_id, principal, purchaseRequest);

        logService.log(principal.getName(),"PURCHASE_TICKET", "User purchased ticket for visit: " + visit_id, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
