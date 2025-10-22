package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;

import java.util.Comparator;
import java.util.List;

public class DeliveryPlannerService {
    public static final String NO_BEST_ROUTE_FOUND_ERR = "No best route found";
    private final TruckAssignmentService truckAssignmentService;
    private final RouteRepository routeRepository;

    public DeliveryPlannerService(TruckAssignmentService truckAssignmentService, RouteRepository routeRepository) {
        this.truckAssignmentService = truckAssignmentService;
        this.routeRepository = routeRepository;
    }

    public Truck findBestTruckForDelivery(int startLocationId, int endLocationId, double weight) {
        List<Route> filteredRoutes = filterRoutes(startLocationId, endLocationId);
        Route bestRoute = findBestRoute(filteredRoutes, endLocationId);

        Truck bestTruck = truckAssignmentService.findAvailableTruckOnRoute(bestRoute, weight);

        return bestTruck;
    }

    public List<Route> filterRoutes(int startId, int endId) {
        return routeRepository
                .getRoutes()
                .stream()
                .filter(route -> {
                    List<Integer> stops = route.getRouteStops();
                    return stops.contains(startId) && stops.contains(endId) &&
                            stops.indexOf(startId) < stops.indexOf(endId);
                })
                .toList();
    }

    public Route findBestRoute(List<Route> routes, int endLocationId) {
        return routes
                .stream()
                .filter(route -> route.getScheduleMap().containsKey(endLocationId))
                .min(Comparator.comparing(r -> r.getScheduleMap().get(endLocationId)))
                .orElseThrow(() -> new ElementNotFoundException(NO_BEST_ROUTE_FOUND_ERR));
    }

    public String findETA(int startLocationId, int endLocationId ) {
        List<Route> filteredRoutes = filterRoutes(startLocationId, endLocationId);
        Route bestRoute = findBestRoute(filteredRoutes, endLocationId);

        return bestRoute
                .getScheduleMap()
                .get(endLocationId)
                .toString();
    }
}