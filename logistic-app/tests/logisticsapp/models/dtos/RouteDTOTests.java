package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RouteDTOTests {

    @Test
    void constructor_ShouldInitializeAllFieldsCorrectly() {
        int id = 1;
        List<Integer> routeStops = Arrays.asList(100, 200, 300);
        Map<Integer, String> scheduleMap = new HashMap<>();
        scheduleMap.put(100, "2025-07-14");
        scheduleMap.put(200, "2025-07-15");

        RouteDTO dto = new RouteDTO(id, routeStops, scheduleMap);

        assertEquals(id, dto.getId());
        assertEquals(routeStops, dto.getRouteStops());
        assertEquals(scheduleMap, dto.getScheduleMap());
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        RouteDTO dto = new RouteDTO();
        assertNotNull(dto);
    }
}
