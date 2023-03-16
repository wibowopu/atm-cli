package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "deposit",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Deposits this amount to the logged in customer",
        header = "Deposit Command",
        footer = "By Wibowo Putra",
        optionListHeading = "%nOption are:%n"
)
public  class DepositCommand implements Callable<Integer>
{
    AtmService atmService=null;
    public DepositCommand(){
        this.atmService = AtmFactory.getService();
    }
    final Integer SUCCESS = 0;
    final Integer FAILED = 1;

    @Option(names = {"-amt","--amount"},
            description = "put some of amount",
            required = true,
            paramLabel = "<put amount>")
    String amount = "";

    @Override
    public Integer call() throws Exception {
        //validate Login
        if (this.atmService.isAtmInUse())
        {
            double d = Double.parseDouble(amount);
            ATM atmActive =  this.atmService.getActiveUserInATM(1l);
                if(atmActive.getActiveUser().getHolder().getAmountHold()>0)
                {
                    //ada utang
                    String nameTranfer = atmActive.getActiveUser().getHolder().getName();
                    double currentHolder =  atmActive.getActiveUser().getHolder().getAmountHold();
                    double currentAmount = d-currentHolder;
                        if(currentAmount<0)
                        {
                            double negativeAmount = d*(-1);
                            ATM atmTranferNegatif = this.atmService.doTransfer(1L,d,nameTranfer);

                            System.out.printf("Transferred $%s to %s\n",d,nameTranfer);
                            System.out.printf("Your balance is $%s\n",atmTranferNegatif.getActiveUser().getAccount().getAmount());
                            System.out.printf("Owed $%s to %s\n",atmTranferNegatif.getActiveUser().getHolder().getAmountHold(),nameTranfer);

                        }
                        else
                        {
                            System.out.printf("current Amount Positif\n");
                            ATM atmTranferPositif = this.atmService.doTransfer(1L,currentHolder,nameTranfer);
                            ATM atmDeposit = this.atmService.doDeposit(1L,currentAmount);
                            System.out.printf("Transferred $%s to %s\n",currentHolder,nameTranfer);
                            System.out.printf("Your balance is $%s\n",currentAmount);
//                            if()
//                            System.out.printf("Owed $%s to %s\n",atmTranferPositif.getActiveUser().getHolder().getAmountHold(),nameTranfer);
                        }
                }else{
                    ATM atm = this.atmService.doDeposit(1L,d);
                    System.out.println("1. Your balance is $"+atm.getActiveUser().getAccount().getAmount()+"\n");
                }
            }

        else{
            System.out.println("Cannot do Deposit, becouse empty active user");
        }
        return SUCCESS;
    }
}
