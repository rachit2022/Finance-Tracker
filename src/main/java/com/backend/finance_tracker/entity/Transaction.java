package com.backend.finance_tracker.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "TRANSACTION")
public class Transaction {

    @Column(name = "TRANSACTION_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transactionId;

    @Column(name = "AMOUNT")
    private long amount;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

    @Column(name = "TRANSACTION_TYPE")
    private String transactionType;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
