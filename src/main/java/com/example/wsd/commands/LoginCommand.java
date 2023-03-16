package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.repo.AtmRepository;
import com.example.wsd.repo.UserRepository;
import com.example.wsd.repo.impl.AtmRepositoryImpl;
import com.example.wsd.repo.impl.UserRepositoryImpl;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import picocli.CommandLine;

import java.util.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "login",
        aliases = {"create2", "plus2"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Logs in as this customer and creates the customer if not exist",
        header = "Add ATM SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%n")
public class LoginCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-n","--name"},
            description = "User Name when do login",
            required = true,
            paramLabel = "<User Name>")
    String name = "";

    @CommandLine.Option(
            names = {"--create-date"},
            required = false,
            description = "Created date for the ATM[s]"
    )
    Date createdDate;

    UserRepository userRepository = null;
    AtmRepository atmRepository = null;
    AtmService atmService = null;

    public LoginCommand() {
        this.atmRepository = new AtmRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.atmService = AtmFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        //validate Login
        if (this.atmService.isAtmInUse())
        {
            System.out.println("Sorry ATM already Use by Another User ");
        }
        else
        {
            ATM atm = this.atmService.doLogin(name);
            if(atm.getActiveUser().getOwned().getUserId()!=null)
            {
                System.out.printf("Hello, %s!\n",atm.getLoginName());
                System.out.printf("Your balance is $%s\n",atm.getActiveUser().getAccount().getAmount());
                System.out.printf("Owed $%s from %s\n",
                        atm.getActiveUser().getOwned().getAmountHold(),
                        atm.getActiveUser().getOwned().getName());

            }else if(atm.getActiveUser().getHolder().getUserId()!=null)
            {
                System.out.printf("Hello, %s!\n",atm.getLoginName());
                System.out.printf("Your balance is $%s\n",atm.getActiveUser().getAccount().getAmount());
                System.out.printf("Owed $%s to %s\n",
                        atm.getActiveUser().getHolder().getAmountHold(),
                        atm.getActiveUser().getHolder().getName());

            }else{
                System.out.printf("Hello, %s!\n",atm.getLoginName());
                System.out.printf("Your balance is $%s\n",atm.getActiveUser().getAccount().getAmount());
            }
        }


        return 0;
    }

}
