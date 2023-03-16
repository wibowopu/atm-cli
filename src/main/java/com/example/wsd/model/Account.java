package com.example.wsd.model;

import com.fasterxml.uuid.Generators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Account implements Serializable {
    private Long id;
    private String uuid;
    private String name;
    private double amount;
    private Bank bank;
    private ArrayList<Transaction> transactions;

    Date createdOn = new Date();
    Date modifiedOn = new Date();


    public Account(String name,Bank bank){
        this.uuid = this.getAccountUUID();
        this.name = name;
        this.bank = bank;
        this.transactions = new ArrayList<Transaction>();
    }

    public Account(String name){
        this.uuid = this.getAccountUUID();
        this.name = name;
        this.transactions = new ArrayList<Transaction>();
    }

    public String getAccountUUID(){
        UUID uuid= Generators.timeBasedGenerator().generate();
        return  uuid.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", transactions=" + transactions +
                //", createdOn=" + createdOn +
                //", modifiedOn=" + modifiedOn +
                '}';
    }
}
