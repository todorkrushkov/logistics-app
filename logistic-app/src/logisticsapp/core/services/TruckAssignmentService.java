package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.contracts.repositories.LogisticsRepository;
import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.core.enums.State;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.core.exceptions.InvalidOperation;
import logisticsapp.models.DistanceTable;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;

import java.util.List;

public class TruckAssignmentService {
    private static final String TRUCK_ALREADY_ASSIGNED_TO_ROUTE_ERR = "Truck is already assigned to a route";
    private static final String TRUCK_RANGE_TOO_SHORT_FOR_ROUTE_ERR = "Truck's range is too short for this route.";
    private static final String TRUCK_CAPACITY_CAP_REACHED_ERR = "Truck capacity cap reached.";
    private static final String DELIVERY_PACKAGE_ALREADY_DELIVERED_ERR = "Can't assign delivered package to truck.";

    LogisticsRepository logisticsRepository;
    DeliveryPackageRepository deliveryPackageRepository;
    TruckRepository truckRepository;

    public TruckAssignmentService(LogisticsRepository logisticsRepository, TruckRepository truckRepository, DeliveryPackageRepository deliveryPackageRepository) {
        this.logisticsRepository = logisticsRepository;
        this.truckRepository = truckRepository;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    public void assignTruckToRoute(Truck truck, Route route) {
        if (logisticsRepository.getAssignedRouteForTruck(truck.getId()) != null) {
            throw new InvalidOperation(TRUCK_ALREADY_ASSIGNED_TO_ROUTE_ERR);
        }

        if (truck.getRange() < calculateRouteDistance(route)) {
            throw new InvalidOperation(TRUCK_RANGE_TOO_SHORT_FOR_ROUTE_ERR);
        }

        logisticsRepository.assignTruckToRoute(truck, route);
    }

    public Truck findAvailableTruckOnRoute(Route route, double weight) {
        int routeId = route.getId();

        return logisticsRepository
                .getTruckToRouteMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == routeId) //get all trucks assigned to this routeId
                .map(entry -> truckRepository.findTruckById(entry.getKey())) //get actual trucks
                .filter(truck -> truck.getAvailableCapacity() >= weight) //get all truck where availableCapacity is ok
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException("No available truck on this route"));
    }

    public void assignPackageToTruck(int deliveryPackageId, int truckId) {
        Truck truck = truckRepository.findTruckById(truckId);
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findDeliveryPackageById(deliveryPackageId);

        if (deliveryPackage.getState().equals(State.DELIVERED)) {
            throw new InvalidOperation(DELIVERY_PACKAGE_ALREADY_DELIVERED_ERR);
        }

        if (truck.getCurrentLoad() + deliveryPackage.getWeight() > truck.getCapacity()) {
            throw new InvalidOperation(TRUCK_CAPACITY_CAP_REACHED_ERR);
        }

        truckRepository.addPackageToTruck(deliveryPackage, truck);
        deliveryPackage.changeState(State.ON_ROUTE);
    }

    public void removePackageFromTruckWhenDelivered(int deliveryPackageId) {
        Truck truck = truckRepository.findTruckByDeliveryPackageId(deliveryPackageId);
        DeliveryPackage deliveryPackage = deliveryPackageRepository.findDeliveryPackageById(deliveryPackageId);

        truck.removeDeliveryPackage(deliveryPackage);
    }

    private int calculateRouteDistance(Route route) {
        int total = 0;
        List<Integer> routeStops = route.getRouteStops();

        for(int i = 0; i < routeStops.size() - 1; i++) {
            int fromId = routeStops.get(i);
            int toId = routeStops.get(i + 1);

            total += DistanceTable.getDistance(fromId -1, toId - 1);
        }

        return total;
    }
}