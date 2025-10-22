package logisticsapp.models;


import logisticsapp.core.exceptions.InvalidUserInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 public class CustomerImplTests {

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        CustomerImpl customer = new CustomerImpl(1, "Ivan", "Ivanov", "0888123456");

        assertEquals(1, customer.getId());
        assertEquals("Ivan", customer.getFirstName());
        assertEquals("Ivanov", customer.getLastName());
        assertEquals("0888123456", customer.getPhoneNum());
        assertTrue(customer.getDeliveryPackage().isEmpty());
    }

    @Test
    void constructor_ShouldThrow_WhenFirstNameTooShort() {
        InvalidUserInputException ex = assertThrows(InvalidUserInputException.class, () ->
                new CustomerImpl(1, "Iv", "Ivanov", "0888123456"));
        assertTrue(ex.getMessage().contains("First name"));
    }

    @Test
    void constructor_ShouldThrow_WhenFirstNameTooLong() {
        String longName = "I".repeat(21);
        InvalidUserInputException ex = assertThrows(InvalidUserInputException.class, () ->
                new CustomerImpl(1, longName, "Ivanov", "0888123456"));
        assertTrue(ex.getMessage().contains("First name"));
    }

    @Test
    void constructor_ShouldThrow_WhenLastNameTooShort() {
        InvalidUserInputException ex = assertThrows(InvalidUserInputException.class, () ->
                new CustomerImpl(1, "Ivan", "Iv", "0888123456"));
        assertTrue(ex.getMessage().contains("Last name"));
    }

    @Test
    void constructor_ShouldThrow_WhenLastNameTooLong() {
        String longLastName = "P".repeat(31);
        InvalidUserInputException ex = assertThrows(InvalidUserInputException.class, () ->
                new CustomerImpl(1, "Ivan", longLastName, "0888123456"));
        assertTrue(ex.getMessage().contains("Last name"));
    }

    @Test
    void constructor_ShouldThrow_WhenPhoneNumWrongLength() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                new CustomerImpl(1, "Ivan", "Ivanov", "12345"));
        assertTrue(ex.getMessage().contains("Phone number"));
    }


    @Test
    void printInfo_ShouldReturnCorrectFormat() {
        CustomerImpl customer = new CustomerImpl(1, "Ivan", "Ivanov", "0888123456");
        String info = customer.printInfo();

        assertTrue(info.contains("-> Customer 1"));
        assertTrue(info.contains("Ivan Ivanov"));
        assertTrue(info.contains("0888123456"));
    }
}
