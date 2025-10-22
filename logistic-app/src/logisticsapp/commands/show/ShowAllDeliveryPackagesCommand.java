package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowAllDeliveryPackagesCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;

    private final PrintModelInfoService printModelInfoService;
    private final DeliveryPackageRepository deliveryPackageRepository;

    public ShowAllDeliveryPackagesCommand(PrintModelInfoService printModelInfoService, DeliveryPackageRepository deliveryPackageRepository) {
        this.printModelInfoService = printModelInfoService;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        return printModelInfoService.printAllDeliveryPackages(deliveryPackageRepository.getDeliveryPackages());
    }
}
