package com.example.wsd.commands;

import com.example.wsd.factory.AtmFactory;
import com.example.wsd.model.Bank;
import com.example.wsd.model.User;
import com.example.wsd.repo.AtmRepository;
import com.example.wsd.repo.UserRepository;
import com.example.wsd.repo.impl.AtmRepositoryImpl;
import com.example.wsd.repo.impl.UserRepositoryImpl;
import com.example.wsd.service.AtmService;
import com.example.wsd.model.ATM;
import com.example.wsd.model.Status;
import org.jetbrains.annotations.NotNull;
import picocli.CommandLine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "list",
        aliases = {"ls", "show"},
        version = "1.0.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        description = "This a Sub Command and lists all the tasks as per the parameters",
        header = "List ATM SubCommand",
        optionListHeading = "%nOptions are:%n")
public class ListATMCommand implements Callable<Integer> {

    @CommandLine.Option(
            names = {"-f", "--format"},
            description = "Formatting the ATM and the default value is ${DEFAULT-VALUE} %nAll Formats are ${COMPLETION-CANDIDATES}",
            defaultValue = "DEFAULT",
            required = false
    )
    ListFormat format;

    @CommandLine.Option(
            names = {"-s", "--status"},
            description = "Lists the atm by Status %nAvailable Statuses are ${COMPLETION-CANDIDATES}",
            required = false
    )
    Status status;

    @CommandLine.Option(
            names = {"--short", "--compact"},
            description = "Lists the atm in SHORT format",
            required = false
    )
    boolean compact;

    @CommandLine.Option(
            names = {"--completed", "--done"},
            description = "Lists the atm which are either completed or not completed",
            negatable = true,
            required = false
    )
    Boolean completed = null;
    @CommandLine.Option(
            names = {"-lu", "--users"},
            description = "Lists the user atm ",
            negatable = true,
            required = false
    )
    Boolean listUser = null;
    @CommandLine.Option(
            names = {"-ua", "--userActive"},
            description = "Lists the user atm ",
            negatable = true,
            required = false
    )
    Boolean userActive = null;

    @CommandLine.Option(
            names = {"--id"},
            description = "Shows the atms for the given ID",
            required = false,
            split = ","
    )
    Long[] id;


    UserRepository userRepository = null;
    AtmRepository atmRepository = null;
    AtmService atmService = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public ListATMCommand() {
        this.atmRepository = new AtmRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
        this.atmService = AtmFactory.getService();
    }

    @Override
    public Integer call() throws Exception {
        if (compact) {
            format = ListFormat.SHORT;
        }

        List<ATM> atmListActivity = new ArrayList<>();

        if (Objects.nonNull(id)) {
            atmListActivity = atmRepository.findByIds(Arrays.asList(id));
        } else if (Objects.isNull(completed)) {
            if (Objects.isNull(status)) {
                atmListActivity = atmRepository.findAll();
            } else {
                atmListActivity = atmRepository.findByStatus(status);
            }
        } else {
            List<Status> statuses = new ArrayList<>();

            if (completed) {
                statuses.add(Status.COMPLETED);
            } else {
                statuses.add(Status.CREATED);
            }

            atmListActivity = atmRepository.findByStatus(statuses);
        }

        if (Objects.nonNull(atmListActivity) && atmListActivity.size() > 0) {
            atmListActivity.stream().forEach(atm ->
                    {
                        System.out.println("Print ATM Active");

                        User userActive = null;
                        try {
                            userActive = userRepository.findByName(atm.getLoginName());
                            atm.setActiveUser(userActive);

                            List<User> usersBank = userRepository.findAll();
                            atm.setUsersBank(usersBank);

                            printATM(format, atm);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } else {
            System.out.println("No Tasks to display !!!");
        }

        return 0;
    }

    private void printATM(ListFormat format, ATM atm) {
        if (format == ListFormat.SHORT) {
            System.out.println(String.format("%4d %3s %10s %s",
                    atm.getId(),
                    this.getStatus(atm),
                    this.dateFormat.format(atm.getCreatedOn()),
                    atm.getLoginName()
            ));
        } else {
            System.out.println("ID      = " + atm.getId());
            System.out.println("LoginName = " + atm.getLoginName());
            System.out.println("Status  = " + atm.getStatus());
            //System.out.println("Created = " + atm.getCreatedOn() + "\n");
            System.out.println("UserActive = " + atm.getActiveUser().toString() + "\n");
            System.out.println("List Users Bank = \n" );
                    atm.getUsersBank().stream().forEach(
                            user -> {
                        System.out.printf("%s \n",user.toString());
                    }
                    );
        }
        }

    private String getStatus(ATM atm) {
        if (atm.getStatus() == Status.COMPLETED)
            return "[x]";
        if (atm.getStatus() == Status.IN_PROGRESS)
            return "[/]";

        return "[ ]";
    }
}