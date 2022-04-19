package org.unibl.etf.virtual_bank_jsp_m2.models.requests;

import org.unibl.etf.virtual_bank_jsp_m2.models.enums.CardType;

import java.sql.Date;

public class BankAccountRequest{
    private String name;
    private String surname;
    private String cardNumber;
    private CardType cardType;
    private Date expirationDate;
    private String pin;

    public BankAccountRequest() {
    }

    public BankAccountRequest(String name, String surname, String cardNumber, CardType cardType, Date expirationDate, String pin) {
        this.name = name;
        this.surname = surname;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
