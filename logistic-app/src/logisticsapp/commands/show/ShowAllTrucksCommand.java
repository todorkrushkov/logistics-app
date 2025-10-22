package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowAllTrucksCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    private final PrintModelInfoService printModelInfoService;
    private final TruckRepository truckRepository;

    public ShowAllTrucksCommand(PrintModelInfoService printModelInfoService, TruckRepository truckRepository) {
        this.printModelInfoService = printModelInfoService;
        this.truckRepository = truckRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        return printModelInfoService.printAllTrucks(truckRepository.getTrucks());
    }
}
