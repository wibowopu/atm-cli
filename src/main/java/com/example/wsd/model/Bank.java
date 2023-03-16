package com.example.wsd.model;

import com.fasterxml.uuid.Generators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Bank  implements Serializable {
    Long id;
    private String name;

    Date createdOn = new Date();
    Date modifiedOn = new Date();

    private ArrayList<User> dataUsers;

    public Bank(String name){
        this.name = name;
    }


    public String getUserUUID(){
        UUID uuid= Generators.timeBasedGenerator().generate();
        return  uuid.toString();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<User> getDataUsers() {
        return dataUsers;
    }

    public void setDataUsers(ArrayList<User> dataUsers) {
        this.dataUsers = dataUsers;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", dataUsers=" + dataUsers +
                '}';
    }

}
