package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.models.DeliveryPackageImpl;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.enums.City;
import logisticsapp.models.CustomerImpl;
import logisticsapp.models.LocationImpl;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TruckRepositoryTests {

    TruckRepository truckRepository;
    Customer sender;
    Customer receiver;
    Location start;
    Location end;

    @BeforeEach
    public void setUp() {
        truckRepository = new TruckRepositoryImpl();

        sender = new CustomerImpl(1, "Todor", "Krushkov", "0888000001");
        receiver = new CustomerImpl(2, "Ivelin", "Yanev", "0888000002");
        start = new LocationImpl(1, City.SYD);
        end = new LocationImpl(2, City.MEL);
    }

    @Test
    public void createTruck_Should_AddTruckToRepository() {
        Truck truck = truckRepository.createTruck("Volvo", 1200.5, 500);

        assertNotNull(truck);
        assertEquals("Volvo", truck.getModel());
        assertEquals(1200.5, truck.getCapacity());
        assertEquals(500, truck.getRange());

        Truck found = truckRepository.findTruckById(truck.getId());
        assertEquals(truck, found);
    }

    @Test
    public void addPackageToTruck_Should_AddPackage() {
        Truck truck = truckRepository.createTruck("DAF", 1000, 400);
        DeliveryPackage pkg = new DeliveryPackageImpl(1, sender, receiver, 2.0, start, end); // ✅ fixed

        truckRepository.addPackageToTruck(pkg, truck);

        assertTrue(truck.getDeliveryPackages().contains(pkg));
    }

    @Test
    public void findTruckByDeliveryPackageId_Should_ReturnCorrectTruck() {
        Truck truck = truckRepository.createTruck("Mercedes", 900, 600);
        DeliveryPackage pkg = new DeliveryPackageImpl(2, sender, receiver, 2.0, start, end); // ✅ fixed

        truckRepository.addPackageToTruck(pkg, truck);

        Truck found = truckRepository.findTruckByDeliveryPackageId(pkg.getId());

        assertEquals(truck, found);
    }
}