package org.unibl.etf.virtual_bank.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.virtual_bank.models.requests.PurchaseRequest;
import org.unibl.etf.virtual_bank.services.BankAccountService;

@RestController
@RequestMapping("/api/bank/accounts")
public class BankController {

    private final BankAccountService bankAccountService;

    public BankController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> withdrawMoney(@RequestBody PurchaseRequest request){
        bankAccountService.withdrawMoneyFromAccount(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
