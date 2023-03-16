package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.repo.AtmRepository;
import com.example.wsd.repo.impl.AtmRepositoryImpl;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import picocli.CommandLine.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Command(
        name = "logout",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "Logs out of the current customer",
        header = "Logout Command",
        footer = "By Wibowo Putra",
        optionListHeading = "%nOption are:%n"
)

public  class LogoutCommand implements Callable<Integer>
{
    AtmRepository atmRepository=null;
    AtmService atmService=null;
    public LogoutCommand(){
        this.atmRepository = new AtmRepositoryImpl();
        this.atmService = AtmFactory.getService();
    }

    final Integer SUCCESS = 0;
    final Integer FAILED = 1;


    @Override
    public Integer call() throws Exception {

        if (this.atmService.isAtmInUse())
        {
            ATM atm = this.atmService.getActiveUserInATM(1L);
            ATM result =  this.atmService.doLogout(atm);
            System.out.printf("Goodbye, %s! \n",result.getLoginName());
        }else{
            System.out.println("There is no user, using this ATM now");
        }
        return SUCCESS;
    }
}
//public class LoginCommand implements Runnable{
//    @Option(names = {"-n","--name"},description = "Name Of Customer")
//    String name = "";
//
//    @Override
//    public void run() {
//        // formatPrint(name);
//        //LoginCommand::formatPrint;
//        //System.out.println("hello..."+ formatPrint(name));
//        System.out.println("hello..."+ name);
//    }
//
//    static private String formatPrint(final String _name){
//        return Ansi.AUTO.string(String.format(
//                "@|bold,fg(green) %s|@",_name)
//        );
//    }
//}
