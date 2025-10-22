package logisticsapp.commands.assign;

import logisticsapp.commands.contracts.Command;
import logisticsapp.core.services.TruckAssignmentService;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

public class AssignPackageCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String DELIVERY_PACKAGE_ASSIGNED_MESSAGE = "Delivery Package %d assigned to Truck %d.";
    private static final String DELIVERY_PACKAGE_PARAMETER = "delivery package ID";
    private static final String TRUCK_ID_PARAMETER = "truck ID";

    private final TruckAssignmentService truckAssignmentService;

    int truckId;
    int deliveryPackageId;

    public AssignPackageCommand(TruckAssignmentService truckAssignmentService) {
        this.truckAssignmentService = truckAssignmentService;
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        parseParameters(parameters);

        truckAssignmentService.assignPackageToTruck(deliveryPackageId, truckId);

        return String.format(DELIVERY_PACKAGE_ASSIGNED_MESSAGE, deliveryPackageId, truckId);
    }

    private void parseParameters(List<String> parameters) {
        deliveryPackageId = ParsingHelper.tryParseInteger(parameters.get(0), DELIVERY_PACKAGE_PARAMETER);
        truckId = ParsingHelper.tryParseInteger(parameters.get(1), TRUCK_ID_PARAMETER);
    }
}
