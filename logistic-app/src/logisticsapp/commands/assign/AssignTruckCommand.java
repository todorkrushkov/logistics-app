package logisticsapp.commands.assign;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;
import logisticsapp.core.services.TruckAssignmentService;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class AssignTruckCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String TRUCK_ASSIGNED_MESSAGE = "Truck %d assigned to Route %d.";
    private static final String TRUCK_ID_PARAMETER = "truck ID";
    private static final String ROUTE_ID_PARAMETER = "route ID";

    private final RouteRepository routeRepository;
    private final TruckRepository truckRepository;
    private final TruckAssignmentService truckAssignmentService;

    int truckId;
    int routeId;

    public AssignTruckCommand(RouteRepository routeRepository, TruckRepository truckRepository, TruckAssignmentService truckAssignmentService) {
        this.routeRepository = routeRepository;
        this.truckRepository = truckRepository;
        this.truckAssignmentService = truckAssignmentService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        Truck truck = truckRepository.findTruckById(truckId);
        Route route = routeRepository.findRouteById(routeId);

        truckAssignmentService.assignTruckToRoute(truck, route);

        return String.format(TRUCK_ASSIGNED_MESSAGE, truckId, routeId);
    }

    private void parseParameters(List<String> parameters) {
        truckId = ParsingHelper.tryParseInteger(parameters.get(0), TRUCK_ID_PARAMETER);
        routeId = ParsingHelper.tryParseInteger(parameters.get(1), ROUTE_ID_PARAMETER);
    }
}
