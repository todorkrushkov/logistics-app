package logisticsapp.core.services;

import logisticsapp.commands.enums.TransferType;
import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.core.contracts.repositories.LogisticsRepository;
import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.models.contracts.*;

import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;

public class PrintModelInfoService {

    private final LogisticsRepository logisticsRepository;
    private final DeliveryPackageRepository deliveryPackageRepository;
    private final TruckRepository truckRepository;
    private final LocationRepository locationRepository;

    public PrintModelInfoService(LogisticsRepository logisticsRepository,
                            DeliveryPackageRepository deliveryPackageRepository,
                            TruckRepository truckRepository,
                            LocationRepository locationRepository) {
        this.logisticsRepository = logisticsRepository;
        this.deliveryPackageRepository = deliveryPackageRepository;
        this.truckRepository = truckRepository;
        this.locationRepository = locationRepository;
    }

    public String printCustomerInfo(Customer customer, TransferType transferType) {
        List<DeliveryPackage> deliveryPackages = deliveryPackageRepository
                .findDeliveryPackagesByTransferType(customer, transferType);

        StringBuilder sb = new StringBuilder();

        sb.append(customer.printInfo());
        sb.append(format("   -> Delivery packages for %s:", transferType.toString()));
        if (deliveryPackages.isEmpty()) {
            sb.append(format("%n      -> No delivery packages."));
        } else {
            for (DeliveryPackage deliveryPackage : deliveryPackages) {
                sb.append(format("%n      - Delivery Package: %d [%s]",
                        deliveryPackage.getId(),
                        deliveryPackage.getState()));
            }
        }

        return sb.toString();
    }

    public String printDeliveryPackageInfo(DeliveryPackage deliveryPackage) {
        Truck truck = truckRepository.findTruckByDeliveryPackageId(deliveryPackage.getId());

        Customer sender = deliveryPackage.getSender();
        Customer receiver = deliveryPackage.getReceiver();

        StringBuilder sb = new StringBuilder();

        sb.append(format("%s" +
                        "   -> Sender ID: %d%n" +
                        "   -> Receiver ID: %d",
                deliveryPackage.printInfo(),
                sender.getId(),
                receiver.getId()));

        if (truck == null) {
            sb.append(format("%n   -> No truck assigned"));
        } else {
            sb.append(format("%n   -> Truck ID: %d", truck.getId()));
        }

        return sb.toString();
    }

    public String printTruckInfo(Truck truck) {
        List<DeliveryPackage> deliveryPackages = truck.getDeliveryPackages();
        StringBuilder sb = new StringBuilder();

        sb.append(truck.printInfo());
        sb.append("   -> Delivery packages:");
        if (deliveryPackages.isEmpty()) {
            sb.append(format("%n      - No delivery packages"));
        } else {
            for (DeliveryPackage deliveryPackage : deliveryPackages) {
                sb.append(format("%n      - Delivery Package ID: %d",
                        deliveryPackage.getId()));
            }
        }
        return sb.toString();
    }

    public String printRouteInfo(Route route) {
        List<Location> routeStops = locationRepository
                .mapLocationIdToLocation(route.getRouteStops());

        List<Truck> trucks = logisticsRepository
                .getAssignedTrucksForRoute(route.getId());

        StringBuilder sb = new StringBuilder();

        sb.append(route.printInfo());
        sb.append("   -> Stops:");
        for (Location location : routeStops) {
            sb.append(format("%n      - %s", location.getCity()));
        }

        sb.append(format("%n   -> Trucks:"));
        if (trucks.isEmpty()) {
            sb.append(format("%n      - No trucks assigned."));
        } else {
            for (Truck truck : trucks) {
                sb.append(format("%n      - Truck: %d", truck.getId()));
            }
        }

        return sb.toString();
    }

    public String printAllCustomers(List<Customer> customers) {
        return printAllElements("Customers", customers, c -> "Customer: " + c.getId());
    }

    public String printAllDeliveryPackages(List<DeliveryPackage> deliveryPackages) {
        return printAllElements("Delivery Packages", deliveryPackages,
                dp -> "Delivery Package: " + dp.getId());
    }

    public String printAllTrucks(List<Truck> trucks) {
        return printAllElements("Trucks", trucks, t -> "Truck: " + t.getId());
    }

    public String printAllRoutes(List<Route> routes) {
        return printAllElements("Routes", routes, r -> "Route: " + r.getId());
    }

    private <T> String printAllElements(String elementType, List<T> items,
                                               Function<T, String> formatter) {
        StringBuilder sb = new StringBuilder();
        sb.append(format("-> All %s:", elementType));

        if (items.isEmpty()) {
            sb.append(format("%n   - No items found."));
        } else {
            for (T item : items) {
                sb.append(format("%n   - ")).append(formatter.apply(item));
            }
        }

        return sb.toString();
    }
}