package logisticsapp.core.contracts.repositories;

import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.dtos.RouteDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RouteRepository extends Loadable<RouteDTO>, Savable<RouteDTO> {

    Route createRoute(List<Integer> locationStops, Map<Integer, LocalDate> scheduleMap);

    Route findRouteById(int id);

    List<Route> getRoutes();

    boolean routeExists(List<Integer> locationStops);
}
