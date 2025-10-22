package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryPackageDTOTests {

    @Test
    void constructor_ShouldInitializeAllFieldsCorrectly() {
        int id = 1;
        double weight = 2.5;
        int startLocationId = 10;
        int endLocationId = 20;
        int senderId = 100;
        int receiverId = 200;
        String state = "ON_ROUTE";

        DeliveryPackageDTO dto = new DeliveryPackageDTO(id, weight, startLocationId, endLocationId, senderId, receiverId, state);

        assertAll(
                () -> assertEquals(id, dto.getId()),
                () -> assertEquals(weight, dto.getWeight()),
                () -> assertEquals(startLocationId, dto.getStartLocationId()),
                () -> assertEquals(endLocationId, dto.getEndLocationId()),
                () -> assertEquals(senderId, dto.getSenderId()),
                () -> assertEquals(receiverId, dto.getReceiverId()),
                () -> assertEquals(state, dto.getState())
        );
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        DeliveryPackageDTO dto = new DeliveryPackageDTO();
        assertNotNull(dto);
    }
}
