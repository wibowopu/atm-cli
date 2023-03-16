package com.example.wsd.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction  implements Serializable {
    private double amount;
    private Date timestamp;
    private Account inAccount;

    public Transaction(double amount,Account inAccount)
    {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();

    }
}
