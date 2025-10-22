package logisticsapp.commands.show;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.services.PrintModelInfoService;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class ShowDeliveryPackageInfoCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private static final String DELIVERY_PACKAGE_ID_PARAMETER = "Delivery Package ID";

    private final DeliveryPackageRepository deliveryPackageRepository;
    private final PrintModelInfoService printModelInfoService;

    private DeliveryPackage deliveryPackage;

    public ShowDeliveryPackageInfoCommand(PrintModelInfoService printModelInfoService, DeliveryPackageRepository deliveryPackageRepository) {
        this.printModelInfoService = printModelInfoService;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        return printModelInfoService.printDeliveryPackageInfo(deliveryPackage);
    }

    private void parseParameters(List<String> parameters) {
        int deliveryPackageId = ParsingHelper.tryParseInteger(parameters.get(0), DELIVERY_PACKAGE_ID_PARAMETER);

        deliveryPackage = deliveryPackageRepository.findDeliveryPackageById(deliveryPackageId);
    }
}
