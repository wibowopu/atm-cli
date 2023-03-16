package com.example.wsd.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ATM implements Serializable {
    Long id;
    String loginName;
    Status status = Status.CREATED;
    Date createdOn = new Date();
    Date modifiedOn = new Date();

    User activeUser ;

    List<User> usersBank;

    public ATM() {
        usersBank = new ArrayList<>();
        activeUser = new User();
    }

    public ATM(String loginName) {
        activeUser = new User();
        usersBank = new ArrayList<>();
        this.loginName = loginName;
    }

    public ATM(String loginName, Date createdOn) {
        this.loginName = loginName;
        this.createdOn = createdOn;
        activeUser = new User();
        usersBank = new ArrayList<>();
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MMM/DD HH:MM");
        return "ATM{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", status=" + status +
                ", createdOn=" + simpleDateFormat.format(createdOn) +
                ", modifiedOn=" + simpleDateFormat.format(modifiedOn) +
                ", UserActive=" + activeUser.toString() +
//                ", DataBank=" + dataBank.toString() +

                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public List<User> getUsersBank() {
        return usersBank;
    }

    public void setUsersBank(List<User> usersBank) {
        this.usersBank = usersBank;
    }
}

