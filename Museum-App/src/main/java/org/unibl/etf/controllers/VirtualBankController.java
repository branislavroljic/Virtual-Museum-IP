package org.unibl.etf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.models.requests.PurchaseRequest;
import org.unibl.etf.services.LogService;
import org.unibl.etf.services.VirtualBankService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/virtual_bank")
public class VirtualBankController {

    private final VirtualBankService  virtualBankService;
    private final LogService logService;

    public VirtualBankController(VirtualBankService virtualBankService, LogService logService) {
        this.virtualBankService = virtualBankService;
        this.logService = logService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> purchaseTicket(@RequestBody @Valid PurchaseRequest purchaseRequest,
                                            HttpServletResponse response, Authentication authentication) throws IOException {
//        response.setHeader("Location", "http://localhost:8080/api/bank/accounts/payment");
//        response.setStatus(307); //307 resends POSt, 302 sends GetMapping
        virtualBankService.requestPurchase(purchaseRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
