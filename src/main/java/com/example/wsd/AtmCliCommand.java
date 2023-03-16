package com.example.wsd;

import com.example.wsd.commands.*;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "atm-cli",
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is ATM tool command",
        header = "ATM Command",
        footer = "By Wibowo Putra",
        optionListHeading = "%nOption are:%n",
        commandListHeading = "%nSubCommand are:%n",
        subcommands =
                {
                        LoginCommand.class,
                        DepositCommand.class,
                        TransferCommand.class,
                        LogoutCommand.class,
                        AddATMCommand.class,
                        ListATMCommand.class,
                        ModifyATMCommand.class,
                        DeleteATMCommand.class,
                        CompleteATMCommand.class
                }
)
public class AtmCliCommand implements Callable<Integer> {
    final Integer SUCCESS = 0;
    final Integer FAILED = 1;


    public static void main(String[] args) throws Exception {
        int exitStatus = new CommandLine(new AtmCliCommand())
                .setCaseInsensitiveEnumValuesAllowed(true)
                .execute(args);
        System.exit(exitStatus);      //PicocliRunner.run(AtmCliCommand.class, args);
    }


    @Override
    public Integer call() throws Exception {
        System.out.println("[atm-cli] welcome in atm CLI");
        return SUCCESS;
    }
}
