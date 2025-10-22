package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowAllRoutesCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    private final PrintModelInfoService printModelInfoService;
    private final RouteRepository routeRepository;

    public ShowAllRoutesCommand(PrintModelInfoService printModelInfoService, RouteRepository routeRepository) {
        this.printModelInfoService = printModelInfoService;
        this.routeRepository = routeRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        return printModelInfoService.printAllRoutes(routeRepository.getRoutes());
    }
}
