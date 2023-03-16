package com.example.wsd.model;

import java.io.Serializable;
import java.util.Date;

public class Holder implements Serializable {

    private Long id;
    private  Long userId;
    private  String name;

    private  double amountHold;

    public Holder(){
    }
    public Holder(Long userId, double amountHold,String name){
        this.userId = userId;
        this.amountHold = amountHold;
        this.name = name;
    }

    public double getAmountHold() {
        return amountHold;
    }

    public void setAmountHold(double amountHold) {
        this.amountHold = amountHold;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Holder{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", amountHold=" + amountHold +
                '}';
    }
}
