package com.example.wsd.service.impl;

import com.example.wsd.model.*;
import com.example.wsd.repo.AccountRepository;
import com.example.wsd.repo.AtmRepository;
import com.example.wsd.repo.BankRepository;
import com.example.wsd.repo.UserRepository;
import com.example.wsd.repo.impl.AccountRepositoryImpl;
import com.example.wsd.repo.impl.AtmRepositoryImpl;
import com.example.wsd.repo.impl.BankRepositoryImpl;
import com.example.wsd.repo.impl.UserRepositoryImpl;
import com.example.wsd.service.AtmService;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AtmServiceImpl implements AtmService {

    private AtmRepository atmRepository= new AtmRepositoryImpl();
    private BankRepository bankRepository= new BankRepositoryImpl();
    private AccountRepository accountRepository= new AccountRepositoryImpl();
    private UserRepository userRepository = new UserRepositoryImpl();
    Holder holderToUserTranfer = new Holder();
    Holder holderActiveUser = new Holder();

    double currentAmountUserActive = 0;
    double currentAmountUserTranfer = 0;
    double positifAmount=0;
    double currentOwnedUserActive = 0;

    @Override
    public boolean isAtmInUse() {
        int index = this.atmRepository.findAll().size();
        if (index > 0) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ATM getActiveUserInATM(Long id) throws Exception {
        ATM userActiveInATM = this.atmRepository.findAll().get(0);
        User activeUser = this.userRepository.findByName(userActiveInATM.getLoginName());
        userActiveInATM.setActiveUser(activeUser);
        return userActiveInATM;
    }

    @Override
    public ATM doLogin(String name) throws Exception {
        //if New Record
        //ATM atmActive = new ATM();
        ATM atm = new ATM(name);
        Bank bank = new Bank("Bank ABC");

            atm = this.atmRepository.createATM(name);

            User newActiveUser = new User(name,bank);
            //Find Data User Account di Bank
            if(this.userRepository.isUser(name)){
                newActiveUser = this.userRepository.findByName(name);
            }else{
                //create user
                newActiveUser = this.userRepository.createUser(newActiveUser,bank);
            }
            atm.setActiveUser(newActiveUser);
        //}
        return atm;
    }

    @Override
    public ATM doLogout(ATM atmActive)
    {
        this.atmRepository.deleteById(atmActive.getId());
        return atmActive;
    }

    @Override
    public ATM doDeposit(Long taskId, Double amount) throws Exception {
        ATM atm = new ATM();
        Bank bank = new Bank("Bank ABC");
        atm = this.atmRepository.findAll().get(0);

        User activeUser = this.userRepository.findByName(atm.getLoginName());
        List<User> usersBank = this.userRepository.findAll();

         double currentAmount = activeUser.getAccount().getAmount();
         currentAmount += amount;

         //validate holder
         activeUser.getHolder().getAmountHold();
         //do updatebalance
         activeUser.getAccount().setAmount(currentAmount);
         atm.setActiveUser(activeUser);

         this.userRepository.updateAmount(activeUser.getId(),activeUser);

        return atm;
    }

    @Override
    public ATM doTransfer(Long taskId, Double amount,String nameToTranfer) throws Exception {
        Bank bank = new Bank("Bank ABC");

        ATM atm = this.atmRepository.findAll().get(0);

        User activeUser = this.userRepository.findByName(atm.getLoginName());
        User tranferToUser = this.userRepository.findByName(nameToTranfer);

        atm.setActiveUser(activeUser);

            if(activeUser.getHolder().getUserId()!=null)
            {
                return putHolder(atm,activeUser,tranferToUser,amount);
            }
            else if(activeUser.getOwned().getUserId()!=null)
            {
                return putOwned(atm,activeUser,tranferToUser,amount);
            }
            else
            {
                return putCleanHolder(atm,activeUser,tranferToUser,amount);
            }
    }

    private  ATM putCleanHolder(ATM atm,User activeUser,User tranferToUser,double amount){
        Holder holderToUserTranfer = new Holder();
        Holder holderActiveUser = new Holder();

        double currentAmountUserActive = 0;
        double currentAmountUserTranfer = 0;
        double positifAmount=0;

        currentAmountUserActive = activeUser.getAccount().getAmount();
        currentAmountUserActive -= amount;
        System.out.println("currentAmountUserActive " +currentAmountUserActive);

        currentAmountUserTranfer = tranferToUser.getAccount().getAmount();
        currentAmountUserTranfer += amount;

        System.out.println("currentAmountUserTranfer " +currentAmountUserTranfer);
        if(currentAmountUserActive<0)
        {

            positifAmount = currentAmountUserActive*(-1);
            holderToUserTranfer = new Holder(tranferToUser.getId(),
                    positifAmount, tranferToUser.getName());

            holderActiveUser = new Holder(activeUser.getId(),
                    positifAmount, activeUser.getName());
            currentAmountUserActive = 0;
        }else{
            System.out.println("else " +currentAmountUserActive);
        }

        activeUser.getAccount().setAmount(currentAmountUserActive);
        this.userRepository.updateAmount(atm.getActiveUser().getId(), activeUser);

        tranferToUser.getAccount().setAmount(currentAmountUserTranfer);
        this.userRepository.updateAmount(tranferToUser.getId(), tranferToUser);

        //set holder
        activeUser.setHolder(holderToUserTranfer);
        this.userRepository.updateHolder(activeUser.getId()
                , holderToUserTranfer);

        activeUser.setOwned(holderActiveUser);
        this.userRepository.updateOwned(tranferToUser.getId()
                , holderActiveUser);

        atm.setActiveUser(activeUser);
        return atm;
    }
    private ATM putHolder(ATM atm,User activeUser,User tranferToUser,double amount )
    {

        currentAmountUserActive = activeUser.getAccount().getAmount();
        currentAmountUserActive -= amount;
        currentAmountUserTranfer = tranferToUser.getAccount().getAmount();
        currentAmountUserTranfer += amount;

        if(currentAmountUserActive<0) {
            positifAmount = currentAmountUserActive * (-1);

            double holdAmount = activeUser.getHolder().getAmountHold();
            double newHoldAmount = holdAmount - positifAmount;
            double resultAmountHold = 0;
            currentAmountUserActive= 0;

            if (newHoldAmount < 0) {
                resultAmountHold = newHoldAmount * (-1);
            } else {
                resultAmountHold = newHoldAmount;
            }

            holderToUserTranfer = new Holder(tranferToUser.getId(),
                    resultAmountHold, tranferToUser.getName());

            holderActiveUser = new Holder(activeUser.getId(),
                    resultAmountHold, activeUser.getName());
            //set amount
            activeUser.getAccount().setAmount(currentAmountUserActive);
            this.userRepository.updateAmount(atm.getActiveUser().getId(), activeUser);

            tranferToUser.getAccount().setAmount(currentAmountUserTranfer);
            this.userRepository.updateAmount(tranferToUser.getId(), tranferToUser);

            //set holder
            activeUser.setHolder(holderToUserTranfer);
            this.userRepository.updateHolder(activeUser.getId()
                    , holderToUserTranfer);

            activeUser.setOwned(holderActiveUser);
            this.userRepository.updateOwned(tranferToUser.getId()
                    , holderActiveUser);

            atm.setActiveUser(activeUser);
        }
        else if(currentAmountUserActive==0)
        {
            System.out.println("currentAmountUserActive==0");
            holderToUserTranfer = new Holder();
            holderActiveUser = new Holder();
            //set holder
            activeUser.setHolder(holderToUserTranfer);
            this.userRepository.updateHolder(activeUser.getId()
                    , holderToUserTranfer);

            activeUser.setOwned(holderActiveUser);
            this.userRepository.updateOwned(tranferToUser.getId()
                    , holderActiveUser);

            atm.setActiveUser(activeUser);
        }
        return atm;
    }
    private ATM putOwned(ATM atm,User activeUser,User tranferToUser,double amount )
    {

        currentOwnedUserActive = activeUser.getOwned().getAmountHold();
        currentOwnedUserActive -= amount;
        if(currentOwnedUserActive<0)
        {
            System.out.println("nagative case");
            positifAmount = currentOwnedUserActive * (-1);

            double holdAmount = activeUser.getHolder().getAmountHold();
            double newHoldAmount = holdAmount - positifAmount;
            double resultAmountHold = 0;

            if (newHoldAmount < 0) {
                resultAmountHold = newHoldAmount * (-1);
            } else {
                resultAmountHold = newHoldAmount;
            }

            holderToUserTranfer = new Holder(tranferToUser.getId(),
                    resultAmountHold, tranferToUser.getName());

            holderActiveUser = new Holder(activeUser.getId(),
                    resultAmountHold, activeUser.getName());

            //set owned to ActiveUser
            activeUser.setHolder(holderToUserTranfer);
            this.userRepository.updateOwned(activeUser.getId()
                    , holderToUserTranfer);

            //set holder to TranferUser
            activeUser.setOwned(holderActiveUser);
            this.userRepository.updateHolder(tranferToUser.getId()
                    , holderActiveUser);

            atm.setActiveUser(activeUser);
        }else{
            double resultAmountHold = 0;

            if (currentOwnedUserActive < 0) {
                resultAmountHold = currentOwnedUserActive * (-1);
            } else {
                resultAmountHold = currentOwnedUserActive;
            }

            //Holder nya UserTranfer - saldo
            holderToUserTranfer = new Holder(tranferToUser.getId(),
                    resultAmountHold, tranferToUser.getName());

            //Owned nya ActiveUser - Saldo
            holderActiveUser = new Holder(activeUser.getId(),
                    resultAmountHold, activeUser.getName());

            //set owned to ActiveUser
            activeUser.setOwned(holderToUserTranfer);
            this.userRepository.updateOwned(activeUser.getId()
                    , holderToUserTranfer);

            this.userRepository.updateHolder(tranferToUser.getId()
                    , holderActiveUser);

            atm.setActiveUser(activeUser);

        }
        return atm;
    }



}
