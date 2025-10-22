package logisticsapp.commands.show;

import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.models.contracts.Truck;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowTruckInfoCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String TRUCK_ID_PARAMETER = "Truck ID";

    private final PrintModelInfoService printModelInfoService;
    private final TruckRepository truckRepository;

    private Truck truck;

    public ShowTruckInfoCommand(PrintModelInfoService printModelInfoService, TruckRepository truckRepository) {
        this.printModelInfoService = printModelInfoService;
        this.truckRepository = truckRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        return printModelInfoService.printTruckInfo(truck);
    }

    private void parseParameters(List<String> parameters) {
        int truckId = ParsingHelper.tryParseInteger(parameters.get(0), TRUCK_ID_PARAMETER);

        truck = truckRepository.findTruckById(truckId);
    }
}