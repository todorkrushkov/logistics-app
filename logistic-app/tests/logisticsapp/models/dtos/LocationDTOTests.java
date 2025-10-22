package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationDTOTests {

    @Test
    void constructor_ShouldInitializeAllFieldsCorrectly() {
        int id = 1;
        String city = "SYD";

        LocationDTO dto = new LocationDTO(id, city);

        assertEquals(id, dto.id);
        assertEquals(city, dto.city);
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        LocationDTO dto = new LocationDTO();
        assertNotNull(dto);
    }
}
