package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.service.AtmService;
import picocli.CommandLine;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "complete",
        aliases = {"done"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This is a Sub Command to 'atm' and marks the atm as completed",
        header = "Complete ATM SubCommand",
        optionListHeading = "%nOptions are:%n",
        footerHeading = "%nCopyright",
        footer = "%n")
public class CompleteATMCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--id"},
            description = "Provide the task ids which needs to be marked as Completed",
            required = true
    )
    List<Long> id;

    AtmService atmService = null;

    public CompleteATMCommand() {
        this.atmService = AtmFactory.getService();
    }

    @Override
    public Integer call() throws Exception {

        if (Objects.nonNull(id) && id.size() > 0) {
//            id.stream().forEach(_id -> atmService.markCompletedById(_id));
        }

        System.out.println("Request has been submitted!!!");

        return 0;
    }
}
