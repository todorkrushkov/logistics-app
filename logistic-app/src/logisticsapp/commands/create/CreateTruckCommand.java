package logisticsapp.commands.create;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.CreateModelService;
import logisticsapp.models.contracts.Truck;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

import static java.lang.String.format;

public class CreateTruckCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;
    private static final String CAPACITY_PARAMETER = "capacity";
    private static final String RANGE_PARAMETER = "range";
    private static final String ITEM = "Truck";

    private final CreateModelService createModelService;

    private String model;
    private double capacity;
    private int range;

    public CreateTruckCommand(CreateModelService createModelService) {
        this.createModelService = createModelService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        Truck createdTruck = createModelService.createTruck(model, capacity, range);

        return format(CommandsConstants.ITEM_CREATED_MESSAGE, ITEM, createdTruck.getId());
    }

    private void parseParameters(List<String> parameters) {
        model = parameters.get(0);
        capacity = ParsingHelper.tryParseDouble(parameters.get(1), CAPACITY_PARAMETER);
        range = ParsingHelper.tryParseInteger(parameters.get(2), RANGE_PARAMETER);
    }

}
