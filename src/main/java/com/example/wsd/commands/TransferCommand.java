package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.repo.AtmRepository;
import com.example.wsd.repo.impl.AtmRepositoryImpl;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import com.example.wsd.model.User;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "transfer",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Transfers this amount from the logged in customer to the target customer",
        header = "Transfer Command",
        footer = "By Wibowo Putra",
        optionListHeading = "%nOption are:%n"
)
public  class TransferCommand implements Callable<Integer>
{
    AtmRepository atmRepository=null;
    AtmService atmService=null;
    public TransferCommand(){
        this.atmRepository = new AtmRepositoryImpl();
        this.atmService = AtmFactory.getService();
    }
    final Integer SUCCESS = 0;
    final Integer FAILED = 1;


    @Option(names = {"-n","-to","--name"},
            description = "put username to transfer",
            required = true,
            paramLabel = "<put username>")
    String name = "";

    @Option(names = {"-amt","--amount"},
            description = "put some of amount transfer",
            required = true,
            paramLabel = "<put amount transfer>")
    String amount = "";

    @Override
    public Integer call() throws Exception {

        if (this.atmService.isAtmInUse())
        {
            double d = Double.parseDouble(amount);
            ATM atmActive =  this.atmService.getActiveUserInATM(1l);

            if(atmActive.getActiveUser().getAccount().getAmount()<=0)
            {
                System.out.printf("Your Acount balance $%s to Transfer %s\n",
                        atmActive.getActiveUser().getAccount().getAmount(),
                        name);

            }else{
                ATM atm = this.atmService.doTransfer(1l,d,name);

                if(atm.getActiveUser().getHolder().getUserId()!=null)
                {
                    double currentAmount = atmActive.getActiveUser().getAccount().getAmount();
                    System.out.printf("Transferred $%s to %s\n",
                            currentAmount,name);
                    System.out.printf("Your balance is $%s\n", atm.getActiveUser().getAccount().getAmount());
                    System.out.printf("Owed $%s to %s\n"
                            ,atm.getActiveUser().getHolder()
                                    .getAmountHold()
                            ,atm.getActiveUser().getHolder()
                                    .getName());
                }else if(atm.getActiveUser().getOwned().getUserId()!=null)
                {
                    double currentAmount = atmActive.getActiveUser().getAccount().getAmount();
                    System.out.printf("Transferred $%s to %s\n",
                            d,name);
                    System.out.printf("Your balance is $%s\n", atm.getActiveUser().getAccount().getAmount());
                    System.out.printf("Owed $%s from %s\n"
                            ,atm.getActiveUser().getOwned()
                                    .getAmountHold()
                            ,atm.getActiveUser().getOwned()
                                    .getName());
                }else{
                    System.out.printf("Transferred $%s to %s\n",amount,name);
                    System.out.printf("Your balance is $%s\n", atm.getActiveUser().getAccount().getAmount());

                }
            }

        }
        else{
            System.out.println("Cannot do Tranfer, becouse empty active user");
        }
        return SUCCESS;
    }
}
