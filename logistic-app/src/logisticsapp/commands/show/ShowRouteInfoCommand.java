package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.models.contracts.Route;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowRouteInfoCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String ROUTE_ID_PARAMETER = "Route ID";

    private final RouteRepository routeRepository;
    private final PrintModelInfoService printModelInfoService;

    private Route route;

    public ShowRouteInfoCommand(PrintModelInfoService printModelInfoService, RouteRepository routeRepository){
        this.printModelInfoService = printModelInfoService;
        this.routeRepository = routeRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        tryParseParameters(parameters);

        return printModelInfoService.printRouteInfo(route);
    }

    private void tryParseParameters(List<String> parameters) {
        int routeId = ParsingHelper.tryParseInteger(parameters.get(0), ROUTE_ID_PARAMETER);

        route = routeRepository.findRouteById(routeId);
    }
}
