package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.models.RouteImpl;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.dtos.RouteDTO;
import logisticsapp.utils.helpers.IdHelper;
import logisticsapp.utils.mappers.RouteMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteRepositoryImpl implements RouteRepository {
    public static final String TYPE_OF_OBJECT = "route";
    public static final int ROUTE_ID = 100;

    private int nextIdRoutes;
    private final List<Route> routes;

    public RouteRepositoryImpl() {
        nextIdRoutes = ROUTE_ID;
        routes = new ArrayList<>();
    }

    @Override
    public Route createRoute(List<Integer> locationStops, Map<Integer, LocalDate> scheduleMap) {
        Route route = new RouteImpl(++nextIdRoutes, locationStops, scheduleMap);
        this.routes.add(route);
        return route;
    }

    @Override
    public Route findRouteById(int routeId) {
        return IdHelper.findObjectById(routes, routeId, TYPE_OF_OBJECT);
    }

    @Override
    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    @Override
    public boolean routeExists(List<Integer> locationStops) {
        return routes.stream()
                .anyMatch(r -> r.getRouteStops().equals(locationStops));
    }

    @Override
    public void loadFromDtoList(List<RouteDTO> dtos) {
        routes.clear();
        int maxId = 0;

        for (RouteDTO dto : dtos) {
            Route route = RouteMapper.fromDto(dto);
            routes.add(route);
            maxId = Math.max(maxId, dto.getId());
        }

        nextIdRoutes = maxId;
    }

    @Override
    public List<RouteDTO> saveToDtoList() {
        return routes.stream()
                .map(RouteMapper::toDto)
                .toList();
    }
}