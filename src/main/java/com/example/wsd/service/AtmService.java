package com.example.wsd.service;

import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import com.example.wsd.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AtmService {
    boolean isAtmInUse();

    ATM getActiveUserInATM(Long id) throws Exception;
    ATM doLogin(String name) throws Exception;
    ATM doDeposit(Long taskId, Double amount) throws Exception;
    ATM doTransfer(Long taskId, Double amount,String nameToTranfer) throws Exception;
    ATM doLogout(ATM atmActive);

}
