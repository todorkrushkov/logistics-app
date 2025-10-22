package logisticsapp.commands.create;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.CreateModelService;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

import static java.lang.String.format;

public class CreateLocationCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String ITEM = "Location";

    private final CreateModelService createModelService;

    City city;

    public CreateLocationCommand(CreateModelService createModelService) {
        this.createModelService = createModelService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        Location createdLocation = createModelService.createLocation(city);

        return format(CommandsConstants.ITEM_CREATED_MESSAGE, ITEM, createdLocation.getId());
    }

    private void parseParameters(List<String> parameters) {
        city = City.valueOf(parameters.get(0));
    }
}
