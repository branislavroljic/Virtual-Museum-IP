package org.unibl.etf.virtual_bank_jsp_m2.models;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable {

    private Integer id;
    private Date date;
    private Double amount;
    private String description;
    private Integer bankAccountId;

    public Transaction(Integer id, Date date, Double amount, String description, Integer bankAccountId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.bankAccountId = bankAccountId;
    }

    public Transaction(Date date, Double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
