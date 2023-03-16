package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add",
        aliases = {"create", "plus"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'atm' and adds the task to the list",
        header = "Add ATM SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%n")
public class AddATMCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-m", "--message"},
            required = true,
            description = "a ATM Note or a Message")
    String[] message;

    @CommandLine.Option(
            names = {"--create-date"},
            required = false,
            description = "Created date for the ATM[s]"
    )
    Date createdDate;

    AtmService atmService = null;

    public AddATMCommand() {
        this.atmService = AtmFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        if (Objects.isNull(createdDate)) {
            Arrays.asList(message).stream()
                    .forEach(atmMessage -> {
                        ATM atm = null;
                        try {
                            atm = this.atmService.doLogin(atmMessage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("New Task ID is " + atm.getId());
                    });
        } else {
            Arrays.asList(message).stream()
                    .forEach(atmMessage -> {
                        ATM atm = null;
                        try {
                            atm = this.atmService.doLogin(atmMessage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("New Task ID is " + atm.getId());
                    });
        }

        return 0;
    }
}
