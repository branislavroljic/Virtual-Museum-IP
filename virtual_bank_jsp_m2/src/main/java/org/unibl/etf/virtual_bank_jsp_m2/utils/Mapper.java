package org.unibl.etf.virtual_bank_jsp_m2.utils;

import org.unibl.etf.virtual_bank_jsp_m2.models.BankAccount;
import org.unibl.etf.virtual_bank_jsp_m2.models.requests.BankAccountRequest;

final public class Mapper {

    public static BankAccount from(BankAccountRequest request){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(request.getName());
        bankAccount.setSurname(request.getSurname());
        bankAccount.setCardNumber(request.getCardNumber());
        bankAccount.setCardType(request.getCardType());
        bankAccount.setExpirationDate(request.getExpirationDate());
        bankAccount.setPin(request.getPin());
        return bankAccount;
    }
}
