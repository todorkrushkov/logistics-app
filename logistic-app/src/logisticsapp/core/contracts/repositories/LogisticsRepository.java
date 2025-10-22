package logisticsapp.core.contracts.repositories;

import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.TruckRouteDTO;

import java.util.List;
import java.util.Map;

public interface LogisticsRepository extends Loadable<TruckRouteDTO>, Savable<TruckRouteDTO> {

    Map<Integer, Integer> getTruckToRouteMap();

    void assignTruckToRoute(Truck truck, Route route);

    Route getAssignedRouteForTruck(int truckId);

    List<Truck> getAssignedTrucksForRoute(int routeId);

}
