package org.unibl.etf.virtual_bank.models.requests;

import org.unibl.etf.virtual_bank.models.entities.enums.CardType;

import java.sql.Date;

public class PurchaseRequest {
    private String name;
    private String surname;
    private String cardNumber;
    private CardType cardType;
    private Date expirationDate;
    private String pin;
    private Double requestedAmount;

    public PurchaseRequest(String name, String surname, String cardNumber, CardType cardType, Date expirationDate, String pin, Double reqeustedAmount) {

        this.name = name;
        this.surname = surname;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.requestedAmount = reqeustedAmount;
    }

    public PurchaseRequest() {
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

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    @Override
    public String toString() {
        return "PurchaseRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType=" + cardType +
                ", expirationDate=" + expirationDate +
                ", pin='" + pin + '\'' +
                ", requestedAmount=" + requestedAmount +
                '}';
    }
}
