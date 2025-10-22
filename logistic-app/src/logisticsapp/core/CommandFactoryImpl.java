package logisticsapp.core;

import logisticsapp.commands.ChangeStateCommand;
import logisticsapp.commands.assign.AssignTruckCommand;
import logisticsapp.commands.contracts.Command;
import logisticsapp.commands.create.*;
import logisticsapp.commands.saveload.LoadHistoryCommand;
import logisticsapp.commands.saveload.SaveHistoryCommand;
import logisticsapp.commands.show.*;
import logisticsapp.core.contracts.CommandFactory;
import logisticsapp.core.contracts.repositories.*;
import logisticsapp.commands.enums.CommandType;
import logisticsapp.commands.assign.AssignPackageCommand;
import logisticsapp.core.exceptions.InvalidUserInputException;
import logisticsapp.core.services.*;
import logisticsapp.utils.helpers.ParsingHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.lang.String.format;

public class CommandFactoryImpl implements CommandFactory {

    private static final String INVALID_COMMAND_ERR = "Invalid command name: %s!";
    private final Map<CommandType, Supplier<Command>> commandMap = new HashMap<>();

    public CommandFactoryImpl(RouteRepository routeRepository,
                              LocationRepository locationRepository,
                              TruckRepository truckRepository,
                              DeliveryPackageRepository deliveryPackageRepository,
                              CustomerRepository customerRepository,
                              CreateModelService createModelService,
                              TruckAssignmentService truckAssignmentService,
                              DeliveryPlannerService deliveryPlannerService,
                              HistoryService historyService,
                              PrintModelInfoService printModelInfoService,
                              ChangeStateService changeStateService) {

        commandMap.put(CommandType.CREATECUSTOMER, () -> new CreateCustomerCommand(createModelService));
        commandMap.put(CommandType.CREATEPACKAGE, () -> new CreateDeliveryPackageCommand(createModelService,
                customerRepository, locationRepository, deliveryPlannerService));
        commandMap.put(CommandType.CREATELOCATION, () -> new CreateLocationCommand(createModelService));
        commandMap.put(CommandType.CREATEROUTE, () -> new CreateRouteCommand(createModelService, locationRepository));
        commandMap.put(CommandType.CREATETRUCK, () -> new CreateTruckCommand(createModelService));

        commandMap.put(CommandType.ASSIGNPACKAGE, () -> new AssignPackageCommand(truckAssignmentService));
        commandMap.put(CommandType.ASSIGNTRUCK, () -> new AssignTruckCommand(routeRepository, truckRepository,
                truckAssignmentService));

        commandMap.put(CommandType.CHANGESTATE, () -> new ChangeStateCommand(changeStateService));

        commandMap.put(CommandType.SAVEHISTORY, () -> new SaveHistoryCommand(historyService));
        commandMap.put(CommandType.LOADHISTORY, () -> new LoadHistoryCommand(historyService));

        commandMap.put(CommandType.SHOWCUSTOMERINFO, () -> new ShowCustomerInfoCommand(printModelInfoService,
                customerRepository));
        commandMap.put(CommandType.SHOWDELIVERYPACKAGEINFO, () -> new ShowDeliveryPackageInfoCommand(printModelInfoService,
                deliveryPackageRepository));
        commandMap.put(CommandType.SHOWTRUCKINFO, () -> new ShowTruckInfoCommand(printModelInfoService, truckRepository));
        commandMap.put(CommandType.SHOWROUTEINFO, () -> new ShowRouteInfoCommand(printModelInfoService, routeRepository));
        commandMap.put(CommandType.SHOWALLCUSTOMERS, () -> new ShowAllCustomersCommand(printModelInfoService,
                customerRepository));
        commandMap.put(CommandType.SHOWALLDELIVERYPACKAGES, () -> new ShowAllDeliveryPackagesCommand(printModelInfoService,
                deliveryPackageRepository));
        commandMap.put(CommandType.SHOWALLTRUCKS, () -> new ShowAllTrucksCommand(printModelInfoService, truckRepository));
        commandMap.put(CommandType.SHOWALLROUTES, () -> new ShowAllRoutesCommand(printModelInfoService, routeRepository));

    }

    public Command createCommandFromCommandName(String commandName) {
        CommandType commandType = ParsingHelper.tryParseEnum(commandName, CommandType.class, format(INVALID_COMMAND_ERR, commandName));

        Supplier<Command> commandSupplier = commandMap.get(commandType);

        if (commandSupplier == null) {
            throw new InvalidUserInputException(format(INVALID_COMMAND_ERR, commandName));
        }

        return commandSupplier.get();
    }
}