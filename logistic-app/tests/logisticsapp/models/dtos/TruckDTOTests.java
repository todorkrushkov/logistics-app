package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TruckDTOTests {

    @Test
    void constructor_ShouldInitializeAllFieldsCorrectly() {
        int id = 1;
        String model = "MAN";
        double capacity = 20.5;
        double currentLoad = 10.2;
        int range = 600;
        List<Integer> packageIds = Arrays.asList(101, 102);

        TruckDTO dto = new TruckDTO(id, model, capacity, currentLoad, range, packageIds);

        assertEquals(id, dto.getId());
        assertEquals(model, dto.getModel());
        assertEquals(capacity, dto.getCapacity());
        assertEquals(currentLoad, dto.getCurrentLoad());
        assertEquals(range, dto.getRange());
        assertEquals(packageIds, dto.getDeliveryPackageIds());
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        TruckDTO dto = new TruckDTO();
        assertNotNull(dto);
    }
}
