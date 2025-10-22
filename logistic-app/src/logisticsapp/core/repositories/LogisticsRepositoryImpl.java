package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.LogisticsRepository;
import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.TruckRouteDTO;

import java.util.*;
import java.util.stream.Collectors;

public class LogisticsRepositoryImpl implements LogisticsRepository {
    RouteRepository routeRepository;
    TruckRepository truckRepository;

    private final Map<Integer, Integer> truckToRouteMap = new HashMap<>();

    public LogisticsRepositoryImpl(RouteRepository routeRepository, TruckRepository truckRepository) {
        this.routeRepository = routeRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    public Map<Integer, Integer> getTruckToRouteMap() {
        return new HashMap<>(truckToRouteMap);
    }

    @Override
    public void assignTruckToRoute(Truck truck, Route route) {
        truckToRouteMap.put(truck.getId(), route.getId());
    }

    @Override
    public Route getAssignedRouteForTruck(int truckId) {
        Integer routeId = truckToRouteMap.get(truckId);
        return routeId != null ? routeRepository.findRouteById(routeId) : null;
    }

    @Override
    public List<Truck> getAssignedTrucksForRoute(int routeId) {
        return getTruckToRouteMap().entrySet().stream()
                .filter(e -> e.getValue() == routeId)
                .map(e -> truckRepository.findTruckById(e.getKey()))
                .collect(Collectors.toList());
    }
    @Override
    public void loadFromDtoList(List<TruckRouteDTO> dtos) {
        truckToRouteMap.clear();

        for (TruckRouteDTO dto : dtos) {
            truckToRouteMap.put(dto.truckId, dto.routeId);
        }
    }

    @Override
    public List<TruckRouteDTO> saveToDtoList() {
        return truckToRouteMap.entrySet()
                .stream()
                .map(e -> new TruckRouteDTO(e.getKey(), e.getValue()))
                .toList();
    }
}