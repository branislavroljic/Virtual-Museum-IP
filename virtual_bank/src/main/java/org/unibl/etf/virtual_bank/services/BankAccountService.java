package org.unibl.etf.virtual_bank.services;

import org.unibl.etf.virtual_bank.models.requests.PurchaseRequest;

public interface BankAccountService {

    void withdrawMoneyFromAccount(PurchaseRequest request);
}
