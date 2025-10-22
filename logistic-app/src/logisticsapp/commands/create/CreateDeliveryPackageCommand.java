package logisticsapp.commands.create;

import logisticsapp.commands.CommandsConstants;
import logisticsapp.commands.contracts.Command;
import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.core.services.CreateModelService;
import logisticsapp.core.services.DeliveryPlannerService;
import logisticsapp.models.contracts.*;
import logisticsapp.utils.helpers.ParsingHelper;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.List;

import static java.lang.String.format;

public class CreateDeliveryPackageCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
    private static final String ITEM = "DeliveryPackage";
    private static final String WEIGHT_PARAMETER = "weight";

    private final CreateModelService createModelService;
    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;
    private final DeliveryPlannerService deliveryPlannerService;

    private Customer sender;
    private Customer receiver;
    private double weight;
    private Location startLocation;
    private Location endLocation;

    public CreateDeliveryPackageCommand(CreateModelService createModelService,
                                        CustomerRepository customerRepository,
                                        LocationRepository locationRepository,
                                        DeliveryPlannerService deliveryPlannerService) {
        this.createModelService = createModelService;
        this.customerRepository = customerRepository;
        this.locationRepository= locationRepository;
        this.deliveryPlannerService = deliveryPlannerService;
    }

    @Override
    public String execute(List<String> parameters) {
        StringBuilder output = new StringBuilder();

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        parseParameters(parameters);

        DeliveryPackage createdDeliveryPackage = createModelService.createDeliveryPackage(sender, receiver, weight, startLocation, endLocation);
        output.append(format(CommandsConstants.ITEM_CREATED_MESSAGE, ITEM, createdDeliveryPackage.getId()));

        Truck assignedTruck = deliveryPlannerService.findBestTruckForDelivery(startLocation.getId(), endLocation.getId(), weight);
        List<Route> routes = deliveryPlannerService.filterRoutes(startLocation.getId(), endLocation.getId());
        Route bestRoute = deliveryPlannerService.findBestRoute(routes, endLocation.getId());
        String eta = deliveryPlannerService.findETA(startLocation.getId(), endLocation.getId());

        output.append(format("%nRecommended route %d: [%s -> %s], Truck %d, ETA: %s.",
                bestRoute.getId(),
                startLocation.getCity(),
                endLocation.getCity(),
                assignedTruck.getId(),
                eta));

        output.append(format("%nUse 'ASSIGNPACKAGE %d %d' to confirm assignment.", createdDeliveryPackage.getId(), assignedTruck.getId()));

        return output.toString();
    }

    private void parseParameters(List<String> parameters) {
        String phoneNumSender = parameters.get(0);
        String phoneNumReceiver = parameters.get(1);

        sender = customerRepository.findCustomerByPhone(phoneNumSender);
        receiver = customerRepository.findCustomerByPhone(phoneNumReceiver);

        weight = ParsingHelper.tryParseDouble(parameters.get(2), WEIGHT_PARAMETER);

        int startLocationId = locationRepository.findIdByLocation(parameters.get(3));
        int endLocationId = locationRepository.findIdByLocation(parameters.get(4));

        startLocation = locationRepository.findLocationById(startLocationId);
        endLocation = locationRepository.findLocationById(endLocationId);
    }
}
