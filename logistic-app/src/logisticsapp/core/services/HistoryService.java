package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.*;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.CustomerDTO;
import logisticsapp.models.dtos.LocationDTO;
import logisticsapp.models.dtos.TruckRouteDTO;
import logisticsapp.utils.helpers.DeliveryPackageLoader;
import logisticsapp.utils.helpers.HistoryDataHelper;
import logisticsapp.utils.helpers.JsonFileHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HistoryService {
    private final LocationRepository locationRepository;
    private final TruckRepository truckRepository;
    private final RouteRepository routeRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryPackageRepository deliveryPackageRepository;
    private final LogisticsRepository logisticsRepository;
    private final DeliveryPackageLoader deliveryPackageLoader;

    public HistoryService(
            LocationRepository locationRepository,
            TruckRepository truckRepository,
            RouteRepository routeRepository,
            CustomerRepository customerRepository,
            DeliveryPackageRepository deliveryPackageRepository,
            LogisticsRepository logisticsRepository,
            DeliveryPackageLoader deliveryPackageLoader) {

        this.locationRepository = locationRepository;
        this.truckRepository = truckRepository;
        this.routeRepository = routeRepository;
        this.customerRepository = customerRepository;
        this.deliveryPackageRepository = deliveryPackageRepository;
        this.logisticsRepository = logisticsRepository;
        this.deliveryPackageLoader = deliveryPackageLoader;
    }

    public void load(String filePath) {
        HistoryDataHelper data = JsonFileHelper.readFromFile(filePath, HistoryDataHelper.class);

        locationRepository.loadFromDtoList(data.getLocations());
        customerRepository.loadFromDtoList(data.getCustomers());
        routeRepository.loadFromDtoList(data.getRoutes());

        List<DeliveryPackage> packages = deliveryPackageLoader.resolvePackages(data.getPackages());
        deliveryPackageRepository.loadFromDtoList(packages);

        truckRepository.loadFromDtoList(data.getTrucks());

        Map<Integer, DeliveryPackage> packageMap = packages
                .stream()
                .collect(Collectors.toMap(DeliveryPackage::getId, dp -> dp));

        truckRepository.restoreTruckPackages(packageMap);
        customerRepository.restoreCustomerPackages(packageMap);

        for (TruckRouteDTO dto : data.getTruckRoutes()) {
            Truck truck = truckRepository.findTruckById(dto.getTruckId());
            Route route = routeRepository.findRouteById(dto.getRouteId());
            logisticsRepository.assignTruckToRoute(truck, route);
        }
    }

    public void save(String filePath) {
        HistoryDataHelper data = new HistoryDataHelper();

        data.setLocations(
                locationRepository.getLocations().stream()
                        .map(loc -> new LocationDTO(loc.getId(), loc.getCity().name()))
                        .collect(Collectors.toList())
        );

        data.setCustomers(
                customerRepository.saveToDtoList()
        );

        data.setPackages(
                deliveryPackageRepository.saveToDtoList()
        );

        data.setTrucks(
                truckRepository.saveToDtoList()
        );

        data.setRoutes(
                routeRepository.saveToDtoList()
        );

        data.setTruckRoutes(
                logisticsRepository.getTruckToRouteMap().entrySet().stream()
                        .map(e -> new TruckRouteDTO(e.getKey(), e.getValue()))
                        .collect(Collectors.toList())
        );

        JsonFileHelper.writeToFile(filePath, data);
    }

}