package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.*;
import logisticsapp.models.contracts.*;
import logisticsapp.core.exceptions.ElementAlreadyExistsException;
import logisticsapp.models.enums.City;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class CreateModelService {
    public static final String CUSTOMER_ALREADY_EXISTS_ERR = "Customer with phone number %s already exists.";
    public static final String LOCATION_ALREADY_EXISTS_ERR = "Location %s already exists.";
    public static final String ROUTE_ALREADY_EXISTS_ERR = "Route already exists.";

    private final CustomerRepository customerRepository;
    private final DeliveryPackageRepository deliveryPackageRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final TruckRepository truckRepository;

    public CreateModelService(CustomerRepository customerRepository, DeliveryPackageRepository deliveryPackageRepository,
                              LocationRepository locationRepository, RouteRepository routeRepository,
                              TruckRepository truckRepository) {
        this.customerRepository = customerRepository;
        this.deliveryPackageRepository = deliveryPackageRepository;
        this.locationRepository = locationRepository;
        this.routeRepository = routeRepository;
        this.truckRepository = truckRepository;
    }

    public Customer createCustomer(String firstName, String lastName, String phoneNum) {
        if(customerRepository.customerExists(phoneNum)) {
            throw new ElementAlreadyExistsException(format(CUSTOMER_ALREADY_EXISTS_ERR, phoneNum));
        }
        return customerRepository.createCustomer(firstName, lastName, phoneNum);
    }

    public DeliveryPackage createDeliveryPackage(Customer sender, Customer receiver, double weight, Location startLocation, Location endLocation) {
        return deliveryPackageRepository.createDeliveryPackage(sender, receiver, weight, startLocation, endLocation);
    }

    public Location createLocation(City city) {
        if(locationRepository.locationExists(city)) {
            throw new ElementAlreadyExistsException(format(LOCATION_ALREADY_EXISTS_ERR, city));
        }
        return locationRepository.createLocation(city);
    }

    public Route createRoute(List<Integer> locationStops) {
        if(routeRepository.routeExists(locationStops)) {
            throw new ElementAlreadyExistsException(ROUTE_ALREADY_EXISTS_ERR);
        }

        Map<Integer, LocalDate> scheduleMap = new LinkedHashMap<>();
        LocalDate startDate = LocalDate.now();

        for(int i = 0; i < locationStops.size(); i++) {
            scheduleMap.put(locationStops.get(i), startDate.plusDays(i));
        }

        return routeRepository.createRoute(locationStops, scheduleMap);
    }

    public Truck createTruck(String model, double capacity, int range) {
        return truckRepository.createTruck(model, capacity, range);
    }
}
