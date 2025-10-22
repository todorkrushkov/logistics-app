package logisticsapp.models.dtos;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerDTOTests {

    @Test
    void constructor_ShouldInitializeAllFieldsCorrectly() {
        int id = 1;
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String phoneNum = "0888123456";
        List<Integer> packageIds = Arrays.asList(101, 102);

        CustomerDTO dto = new CustomerDTO(id, firstName, lastName, phoneNum, packageIds);

        assertEquals(id, dto.getId());
        assertEquals(firstName, dto.getFirstName());
        assertEquals(lastName, dto.getLastName());
        assertEquals(phoneNum, dto.getPhoneNum());
        assertEquals(packageIds, dto.getPackageIds());
    }

    @Test
    void noArgConstructor_ShouldCreateObject() {
        CustomerDTO dto = new CustomerDTO();
        assertNotNull(dto);
    }

    @Test
    void getters_ShouldReturnDefaultValues_WhenSetWithConstructor() {
        List<Integer> packageIds = List.of(5, 10);
        CustomerDTO dto = new CustomerDTO(2, "Georgi", "Binev", "0899123456", packageIds);

        assertAll(
                () -> assertEquals(2, dto.getId()),
                () -> assertEquals("Georgi", dto.getFirstName()),
                () -> assertEquals("Binev", dto.getLastName()),
                () -> assertEquals("0899123456", dto.getPhoneNum()),
                () -> assertEquals(packageIds, dto.getPackageIds())
        );
    }
}
