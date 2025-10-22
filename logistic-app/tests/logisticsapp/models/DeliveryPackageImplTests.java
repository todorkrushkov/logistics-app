package logisticsapp.models;

import logisticsapp.core.enums.State;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

  public class DeliveryPackageImplTests {

    @Test
    void constructor_ShouldInitializeCorrectly() {
        DeliveryPackage pkg = createRealPackage(150);

        assertEquals(1, pkg.getId());
        assertEquals(150, pkg.getWeight());
        assertEquals("Sydney", pkg.getStartLocation().getCity().toString());
        assertEquals("Sydney", pkg.getStartLocation().getCity().toString());
        assertEquals(State.ACCEPTED, pkg.getState());
        assertEquals("Georgi", pkg.getSender().getFirstName());
        assertEquals("Ivan", pkg.getReceiver().getFirstName());
    }

      private DeliveryPackage createRealPackage(double weight) {
          Location start = new LocationImpl(1, City.SYD);
          Location end = new LocationImpl(2, City.MEL);
          Customer sender = new CustomerImpl(10001, "Georgi", "Binev", "0891234567");
          Customer receiver = new CustomerImpl(10002, "Ivan", "Ivanov", "0891231231");

          return new DeliveryPackageImpl(1, sender, receiver, weight, start, end);
      }

      private DeliveryPackage createPackageWithState(double weight, State state) {
          Location start = new LocationImpl(1, City.SYD);
          Location end = new LocationImpl(2, City.MEL);
          Customer sender = new CustomerImpl(10001, "Georgi", "Binev", "0891234567");
          Customer receiver = new CustomerImpl(10002, "Ivan", "Ivanov", "0891231231");

          return new DeliveryPackageImpl(1, sender, receiver, weight, start, end, state);
      }

    @Test
    void constructorWithState_ShouldInitializeCorrectly() {
        DeliveryPackage pkg = new DeliveryPackageImpl(
                2,
                createRealPackage(100).getSender(),
                createRealPackage(100).getReceiver(),
                100,
                createRealPackage(100).getStartLocation(),
                createRealPackage(100).getEndLocation(),
                State.ON_ROUTE
        );

        assertEquals(State.ON_ROUTE, pkg.getState());
    }

    @Test
    void setWeight_ShouldThrow_WhenNegativeWeight() {
        Location start = createRealPackage(100).getStartLocation();
        Location end = createRealPackage(100).getEndLocation();
        Customer sender = createRealPackage(100).getSender();
        Customer receiver = createRealPackage(100).getReceiver();

        assertThrows(IllegalArgumentException.class, () ->
                new DeliveryPackageImpl(3, sender, receiver, -10, start, end));
    }

    @Test
    void changeState_ShouldChange_WhenDifferentState() {
        DeliveryPackage pkg = createRealPackage(120);
        pkg.changeState(State.ON_ROUTE);

        assertEquals(State.ON_ROUTE, pkg.getState());
    }

    @Test
    void changeState_ShouldThrow_WhenSameState() {
        DeliveryPackage pkg = createPackageWithState(120, State.ACCEPTED);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                pkg.changeState(State.ACCEPTED));

        assertTrue(ex.getMessage().contains("Package already in state Accepted"));
    }

    @Test
    void equalsAndHashCode_ShouldBeConsistent() {
        DeliveryPackage pkg1 = createRealPackage(100);
        DeliveryPackage pkg2 = createRealPackage(100);

        assertEquals(pkg1, pkg2);
        assertEquals(pkg1.hashCode(), pkg2.hashCode());
    }

      @Test
      void printInfo_ShouldContainExpectedData() {
          DeliveryPackage pkg = createPackageWithState(75, State.ACCEPTED);
          String info = pkg.printInfo();

          System.out.println("printInfo() output:\n" + info);

          assertTrue(info.contains("DeliveryPackage " + pkg.getId()), "Missing package id");
          assertTrue(info.contains("State: " + pkg.getState()), "Missing or wrong state");
          assertTrue(info.contains(String.format("Weight: %.3f KG", pkg.getWeight())), "Missing or wrong weight");
          assertTrue(info.contains("Start Location: " + pkg.getStartLocation().getCity()), "Missing or wrong start location");
          assertTrue(info.contains("End Location: " + pkg.getEndLocation().getCity()), "Missing or wrong end location");
      }


  }
