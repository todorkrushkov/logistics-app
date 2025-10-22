package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.*;
import logisticsapp.core.exceptions.ElementAlreadyExistsException;
import logisticsapp.core.repositories.*;
import logisticsapp.models.contracts.*;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateModelServiceTests {

    private CreateModelService service;

    @BeforeEach
    public void setUp() {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        DeliveryPackageRepository deliveryPackageRepository = new DeliveryPackageRepositoryImpl();
        LocationRepository locationRepository = new LocationRepositoryImpl();
        RouteRepository routeRepository = new RouteRepositoryImpl();
        TruckRepository truckRepository = new TruckRepositoryImpl();

        service = new CreateModelService(
                customerRepository,
                deliveryPackageRepository,
                locationRepository,
                routeRepository,
                truckRepository
        );
    }

    @Test
    public void createCustomer_Should_CreateSuccessfully() {
        Customer customer = service.createCustomer("Todor", "Krushkov", "0888000000");

        assertEquals("Todor", customer.getFirstName());
        assertEquals("Krushkov", customer.getLastName());
        assertEquals("0888000000", customer.getPhoneNum());
    }

    @Test
    public void createCustomer_Should_Throw_WhenPhoneNumberAlreadyExists() {
        service.createCustomer("Todor", "Krushkov", "0888000000");

        assertThrows(ElementAlreadyExistsException.class, () ->
                service.createCustomer("Ivelin", "Yanev", "0888000000"));
    }

    @Test
    public void createLocation_Should_CreateSuccessfully() {
        Location location = service.createLocation(City.SYD);

        assertEquals(City.SYD, location.getCity());
    }

    @Test
    public void createLocation_Should_Throw_WhenCityExists() {
        service.createLocation(City.SYD);

        assertThrows(ElementAlreadyExistsException.class, () ->
                service.createLocation(City.SYD));
    }

    @Test
    public void createTruck_Should_CreateSuccessfully() {
        Truck truck = service.createTruck("Mercedes", 1000, 500);

        assertEquals("Mercedes", truck.getModel());
        assertEquals(1000, truck.getCapacity());
        assertEquals(500, truck.getRange());
    }

    @Test
    public void createDeliveryPackage_Should_CreateSuccessfully() {
        Customer sender = service.createCustomer("Todor", "Krushkov", "0888000000");
        Customer receiver = service.createCustomer("Ivelin", "Yanev", "0888000001");
        Location start = service.createLocation(City.SYD);
        Location end = service.createLocation(City.MEL);

        DeliveryPackage dp = service.createDeliveryPackage(sender, receiver, 5.0, start, end);

        assertEquals(5.0, dp.getWeight());
        assertEquals(sender, dp.getSender());
        assertEquals(receiver, dp.getReceiver());
        assertEquals(start, dp.getStartLocation());
        assertEquals(end, dp.getEndLocation());
    }

    @Test
    public void createRoute_Should_CreateSuccessfully() {
        Location loc1 = service.createLocation(City.SYD);
        Location loc2 = service.createLocation(City.MEL);

        List<Integer> stopIds = List.of(loc1.getId(), loc2.getId());

        Route route = service.createRoute(stopIds);

        assertEquals(2, route.getScheduleMap().size());
        assertTrue(route.getScheduleMap().containsKey(loc1.getId()));
        assertTrue(route.getScheduleMap().containsKey(loc2.getId()));
    }

    @Test
    public void createRoute_Should_Throw_WhenRouteExists() {
        Location loc1 = service.createLocation(City.SYD);
        Location loc2 = service.createLocation(City.MEL);

        List<Integer> stopIds = List.of(loc1.getId(), loc2.getId());
        service.createRoute(stopIds);

        assertThrows(ElementAlreadyExistsException.class, () ->
                service.createRoute(stopIds));
    }
}