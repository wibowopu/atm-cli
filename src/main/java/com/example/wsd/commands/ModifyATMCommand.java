package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import picocli.CommandLine;

import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "modify",
        aliases = {"change", "alter"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'atm' and modifies the task message",
        header = "Modify ATM SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%n")
public class ModifyATMCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            description = "Provide the task id which needs to be updated",
            required = true
    )
    Long id;

    @CommandLine.Option(
            names = {"--message"},
            description = "Provide the message which needs to be updated with",
            required = true
    )
    String message;

    AtmService atmService = null;

    public ModifyATMCommand() {
        this.atmService = AtmFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

//        ATM atm = atmService.updateMessage(id, message);
//        if (Objects.nonNull(atm)) {
//            System.out.println("Updated Successfully");
//        } else {
//            System.out.println("Task Not Found !!!");
//        }

        return 0;
    }
}
