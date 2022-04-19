package org.unibl.etf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.exceptions.NotFoundException;
import org.unibl.etf.models.dto.Artifact;
import org.unibl.etf.models.entities.VirtualVisitEntity;
import org.unibl.etf.models.requests.StartVisitRequest;
import org.unibl.etf.models.responses.VirtualVisitResponse;
import org.unibl.etf.services.LogService;
import org.unibl.etf.services.MuseumService;
import org.unibl.etf.services.VirtualVisitService;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/visits")
public class VisitsController {

   private final
    VirtualVisitService virtualVisitService;
   private final LogService logService;
   private final MuseumService museumService;

    public VisitsController(VirtualVisitService virtualVisitService, LogService logService, MuseumService museumService) {
        this.virtualVisitService = virtualVisitService;
        this.logService = logService;
        this.museumService = museumService;
    }

    @GetMapping
    public ResponseEntity<List<VirtualVisitResponse>> getAllVisits(Authentication principal){
        logService.log(principal.getName(),"GET_VISITS", "User listed visits", new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(virtualVisitService.getAll());
    }

    @DeleteMapping("/{visit_id}")
    public ResponseEntity<?> deleteVirtualVisit(@PathVariable Integer visit_id, Authentication principal) throws IOException {
        virtualVisitService.deleteVirtualVisit(visit_id);
        logService.log(principal.getName(),"DELETE_VISIT", "User deleted virtual visit with ID: " + visit_id, new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{visit_id}/remaining_time")
    public ResponseEntity<Long> getRemainingTimeForVirtualVisit(@PathVariable Integer visit_id, Authentication principal){
        logService.log(principal.getName(),"GET_REMAINING_TIME_VISIT", "User requested remaining time for virtual " +
                "visit by ID: " + visit_id, new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(virtualVisitService.getVisitRemainingTime(visit_id));
    }

    @GetMapping("/{visit_id}/artifacts")
    @ResponseBody
    public ResponseEntity<List<Artifact>> getVirtualVisitArtifacts(@PathVariable Integer visit_id, Authentication principal) {
        logService.log(principal.getName(),"GET_ARTIFACTS",
                "User requested for artifacts of visit with ID: " + visit_id,
                new Timestamp(System.currentTimeMillis()));
        return ResponseEntity.ok(virtualVisitService.getVirtualVisitArtifacts(visit_id, principal));
    }

    @PostMapping("/{visit_id}/tickets")
    public ResponseEntity<?> checkIfTicketNumberIsValid(@RequestBody @Valid StartVisitRequest startVisitRequest,
                                                        @PathVariable Integer visit_id, Authentication principal) {
        logService.log(principal.getName(),"CHECK_TICKET_VALIDITY",
                "User checked ticket validity. Ticket number:  " + startVisitRequest.getTicketNumber(), new Timestamp(System.currentTimeMillis()));
        if (museumService.ticketExists(visit_id, principal, startVisitRequest.getTicketNumber())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else throw new NotFoundException("Ticker number is not valid!");
    }
}
