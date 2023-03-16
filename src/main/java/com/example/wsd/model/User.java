package com.example.wsd.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private Long id;
    private String uuid;
    private String name;

    Date createdOn = new Date();
    Date modifiedOn = new Date();
    private Account account;
    private Holder holder;

    private Holder owned;

    public User(){

    }

    public User(String name, Bank bank){
        this.uuid = bank.getUserUUID();
        this.name = name;
        this.account = new Account(name,bank);
        this.holder = new Holder();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public Holder getOwned() {
        return owned;
    }

    public void setOwned(Holder owned) {
        this.owned = owned;
    }

    public String getBalance() {
        String amountBalance = String.valueOf(this.account.getAmount());
        return amountBalance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                //", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                //", createdOn=" + createdOn +
                //", modifiedOn=" + modifiedOn +
                ", account=" + account.toString() + "\n" +
                ", Holder=" + holder.toString() + "\n" +
                ", Owned =" + owned.toString() + "\n" +

                '}';
    }

}
