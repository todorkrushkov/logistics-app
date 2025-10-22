package logisticsapp.commands.create;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.core.services.CreateModelService;
import logisticsapp.models.contracts.Route;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class CreateRouteCommand implements Command {

    public static final int EXPECTED_MINIMUM_NUMBER_OF_ARGUMENTS = 2;
    private static final String ITEM = "Route";

    private final CreateModelService createModelService;
    private final LocationRepository locationRepository;

    List<Integer> locationStops = new ArrayList<>();

    public CreateRouteCommand(CreateModelService createModelService, LocationRepository locationRepository) {
        this.createModelService = createModelService;
        this.locationRepository = locationRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateMinimumArgumentsCount(parameters, EXPECTED_MINIMUM_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        Route createdRoute = createModelService.createRoute(locationStops);

        return format(CommandsConstants.ITEM_CREATED_MESSAGE, ITEM, createdRoute.getId());
    }

    private void parseParameters(List<String> parameters) {
        int bufferLocationId = 0;

        for(String parameter: parameters) {
            bufferLocationId = locationRepository.findIdByLocation(parameter);
            locationStops.add(bufferLocationId);
        }
    }
}
