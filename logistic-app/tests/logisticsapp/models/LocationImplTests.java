package logisticsapp.models;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 public class LocationImplTests {

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        LocationImpl location = new LocationImpl(1, City.SYD);

        assertEquals(1, location.getId());
        assertEquals(City.SYD, location.getCity());
    }

    @Test
    void equals_ShouldReturnTrue_ForSameObject() {
        LocationImpl location = new LocationImpl(1, City.SYD);
        assertTrue(location.equals(location));
    }

    @Test
    void equals_ShouldReturnTrue_ForEqualObjects() {
        LocationImpl loc1 = new LocationImpl(1, City.SYD);
        LocationImpl loc2 = new LocationImpl(1, City.SYD);

        assertTrue(loc1.equals(loc2));
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }

    @Test
    void equals_ShouldReturnFalse_ForNull() {
        LocationImpl location = new LocationImpl(1, City.SYD);
        assertFalse(location.equals(null));
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentClass() {
        LocationImpl location = new LocationImpl(1, City.SYD);
        assertFalse(location.equals("Something"));
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentId() {
        LocationImpl loc1 = new LocationImpl(1, City.SYD);
        LocationImpl loc2 = new LocationImpl(2, City.SYD);

        assertFalse(loc1.equals(loc2));
    }

    @Test
    void equals_ShouldReturnFalse_ForDifferentCity() {
        LocationImpl loc1 = new LocationImpl(1, City.SYD);
        LocationImpl loc2 = new LocationImpl(1, City.MEL);

        assertFalse(loc1.equals(loc2));
    }
}
