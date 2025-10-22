package logisticsapp.core;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.CommandFactory;
import logisticsapp.core.contracts.Engine;
import logisticsapp.core.contracts.repositories.*;
import logisticsapp.core.repositories.*;
import logisticsapp.core.services.*;
import logisticsapp.utils.helpers.DeliveryPackageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;

public class LogisticsEngineImpl implements Engine {
    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERR = "Command cannot be empty.";

    private static final String FILE_PATH = "./data/history.json";
    public static final String HISTORY_LOADED_SUCCESSFULLY = "History loaded successfully.";
    public static final String HISTORY_FILE_NOT_FOUND_ERR = "No valid history file found. Skipping load.";

    private static final String BOOT_IMAGE = String.format(
                    "                                     %n" +
                    "    {---------}         {---------}  %n" +
                    "   {           }       {           } %n" +
                    "   |[##]----- {---------} -----[##]| %n" +
                    "   |******** {           } ********| %n" +
                    "  [|        |[##]-----[##]|        |]%n" +
                    "  [|____    |*************|    ____|]%n" +
                    "   |____}-$[|             |]$-{____| %n" +
                    "   |###|_{_[|____     ____|]_}_|###| %n" +
                    "   [___|____|____}-$-{____|____|___] %n" +
                    "   |___|    |###|_{_}_|###|    |___| %n" +
                    "            [___|_____|___]          %n" +
                    "            |___|     |___|          %n" +
                    "                                     %n"
    );

    private final CommandFactory commandFactory;
    private final LogisticsRepository logisticsRepository;
    private final RouteRepository routeRepository;
    private final LocationRepository locationRepository;
    private final TruckRepository truckRepository;
    private final DeliveryPackageRepository deliveryPackageRepository;
    private final CustomerRepository customerRepository;

    private final CreateModelService createModelService;
    private final TruckAssignmentService truckAssignmentService;
    private final DeliveryPlannerService deliveryPlannerService;
    private final PrintModelInfoService printModelInfoService;
    private final HistoryService historyService;
    private final ChangeStateService changeStateService;

    public LogisticsEngineImpl() {
        this.routeRepository = new RouteRepositoryImpl();
        this.locationRepository = new LocationRepositoryImpl();
        this.truckRepository = new TruckRepositoryImpl();
        this.deliveryPackageRepository = new DeliveryPackageRepositoryImpl();
        this.customerRepository = new CustomerRepositoryImpl();
        this.logisticsRepository = new LogisticsRepositoryImpl(routeRepository, truckRepository);
        this.historyService = new HistoryService(
                locationRepository,
                truckRepository,
                routeRepository,
                customerRepository,
                deliveryPackageRepository,
                logisticsRepository,
                new DeliveryPackageLoader(customerRepository, locationRepository));

        this.createModelService = new CreateModelService(
                customerRepository,
                deliveryPackageRepository,
                locationRepository,
                routeRepository,
                truckRepository);
        this.truckAssignmentService = new TruckAssignmentService(
                logisticsRepository,
                truckRepository,
                deliveryPackageRepository);
        this.deliveryPlannerService = new DeliveryPlannerService(truckAssignmentService, routeRepository);
        this.printModelInfoService = new PrintModelInfoService(logisticsRepository, deliveryPackageRepository,
                truckRepository, locationRepository);
        this.changeStateService = new ChangeStateService(truckAssignmentService, deliveryPackageRepository);
        this.commandFactory = new CommandFactoryImpl(routeRepository,
                locationRepository, truckRepository,
                deliveryPackageRepository, customerRepository,
                createModelService, truckAssignmentService,
                deliveryPlannerService, historyService, printModelInfoService, changeStateService);
    }

    public void printBootImage () {
        System.out.println(BOOT_IMAGE);
    }

    public void load() {
        File file = new File(FILE_PATH);
        if (file.exists() && file.length() > 0) {
            historyService.load(FILE_PATH);
            System.out.println(HISTORY_LOADED_SUCCESSFULLY);
        } else {
            System.out.println(HISTORY_FILE_NOT_FOUND_ERR);
        }
    }

    public void save() {
        historyService.save(FILE_PATH);
    }

    @Override
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    System.out.println(EMPTY_COMMAND_ERR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
                System.out.println(CommandsConstants.JOIN_DELIMITER);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    System.out.println(ex.getMessage());
                } else {
                    System.out.println(ex.toString());
                }
                System.out.println(CommandsConstants.JOIN_DELIMITER);
            }
        }
    }

    private void processCommand (String inputLine) {
        String commandName = extractCommandName(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName);
        List<String> parameters = extractCommandParameters(inputLine);
        String executionResult = command.execute(parameters);
        System.out.println(executionResult);
    }

    private String extractCommandName (String inputLine) {
        return inputLine.split(" ")[0];
    }

    private List<String> extractCommandParameters (String inputLine) {
        String[] commandParts = inputLine.split(" ");
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }
}
