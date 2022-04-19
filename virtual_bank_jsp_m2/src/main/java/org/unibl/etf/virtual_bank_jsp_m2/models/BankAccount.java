package org.unibl.etf.virtual_bank_jsp_m2.models;

import org.unibl.etf.virtual_bank_jsp_m2.models.enums.CardType;

import java.io.Serializable;
import java.sql.Date;

public class BankAccount implements Serializable {

    private Integer id;
    private String name;
    private String surname;
    private String cardNumber;
    private CardType cardType;
    private Date expirationDate;
    private String pin;
    private Double balance;
    private Boolean blocked;

    public BankAccount(Integer id, String name, String surname, String cardNumber, CardType cardType, Date expirationDate, String pin, Double balance, Boolean blocked) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.pin = pin;
        this.balance = balance;
        this.blocked = blocked;
    }

//    public BankAccount(String name, String surname, String cardNumber, CardType cardType, Date expirationDate, String pin) {
//        this.name = name;
//        this.surname = surname;
//        this.cardNumber = cardNumber;
//        this.cardType = cardType;
//        this.expirationDate = expirationDate;
//        this.pin = pin;
//    }

    public BankAccount() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
