package org.unibl.etf.services;

import org.unibl.etf.models.requests.PurchaseRequest;

public interface VirtualBankService {

    void requestPurchase(PurchaseRequest purchaseRequest);
}
