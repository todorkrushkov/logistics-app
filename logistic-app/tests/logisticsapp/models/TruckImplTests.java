package logisticsapp.models;

import logisticsapp.core.exceptions.InvalidUserInputException;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TruckImplTests {

    @Test
    void constructor_ShouldInitializeCorrectly() {
        TruckImpl truck = new TruckImpl(1, "Scania", 1000, 800);

        assertEquals(1, truck.getId());
        assertEquals("Scania", truck.getModel());
        assertEquals(1000, truck.getCapacity());
        assertEquals(0, truck.getCurrentLoad());
        assertEquals(800, truck.getRange());
        assertTrue(truck.getDeliveryPackages().isEmpty());
    }

    @Test
    void addDeliveryPackage_ShouldIncreaseLoadAndAddToList() {
        TruckImpl truck = new TruckImpl(1, "Volvo", 1000, 500);
        DeliveryPackage pkg = createRealPackage(200);

        truck.addDeliveryPackage(pkg);

        assertEquals(1, truck.getDeliveryPackages().size());
        assertEquals(200, truck.getCurrentLoad());
        assertEquals(800, truck.getAvailableCapacity());
    }

    private DeliveryPackage createRealPackage(double weight) {
        Location start = new LocationImpl(1, City.SYD);
        Location end = new LocationImpl(2, City.MEL);
        Customer sender = new CustomerImpl(10001, "Georgi", "Binev", "0891234567");
        Customer receiver = new CustomerImpl(10002, "Ivan", "Ivanov", "0891231231");

        return new DeliveryPackageImpl(1, sender, receiver, weight, start, end);
    }

    @Test
    void getDeliveryPackages_ShouldReturnCopy() {
        TruckImpl truck = new TruckImpl(1, "MAN", 1000, 600);
        truck.addDeliveryPackage(createRealPackage(100));

        List<DeliveryPackage> packages = truck.getDeliveryPackages();
        packages.clear();

        assertEquals(1, truck.getDeliveryPackages().size());
    }

    @Test
    void constructor_ShouldThrow_WhenModelTooShort() {
        assertThrows(InvalidUserInputException.class, () ->
                new TruckImpl(1, "A", 1000, 400));
    }

    @Test
    void constructor_ShouldThrow_WhenCapacityTooLow() {
        assertThrows(InvalidUserInputException.class, () ->
                new TruckImpl(1, "Valid Model", 10, 400));
    }

    @Test
    void constructor_ShouldThrow_WhenRangeNegative() {
        assertThrows(InvalidUserInputException.class, () ->
                new TruckImpl(1, "Valid Model", 500, -1));
    }

    @Test
    void printInfo_ShouldReturnExpectedFormat() {
        TruckImpl truck = new TruckImpl(1001, "DAF", 800, 400);
        String output = truck.printInfo();

        assertTrue(output.contains("-> Truck 1001"));
        assertTrue(output.contains("   - Model: DAF"));
        assertTrue(output.contains("   - Capacity: 800.00 KG"));
        assertTrue(output.contains("   - Range: 400 KM"));
    }
}
