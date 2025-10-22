package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TruckRouteDTOTests {

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        int truckId = 5;
        int routeId = 12;

        TruckRouteDTO dto = new TruckRouteDTO(truckId, routeId);

        assertEquals(truckId, dto.getTruckId());
        assertEquals(routeId, dto.getRouteId());
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        TruckRouteDTO dto = new TruckRouteDTO();
        assertNotNull(dto);
    }
}
