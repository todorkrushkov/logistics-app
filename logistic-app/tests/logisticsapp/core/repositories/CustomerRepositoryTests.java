package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.exceptions.ElementNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTests {
    CustomerRepository customerRepository;

    @BeforeEach
    public void beforeEach() {
        customerRepository = new CustomerRepositoryImpl();
    }

    @Test
    public void createCustomer_Should_AddCustomerWithIncrementId() {
        var customer1 = customerRepository.createCustomer
                ("Todor", "Krushkov", "0888000001");
        var customer2 = customerRepository.createCustomer
                ("Ivelin", "Yanev", "0888000002");

        assertEquals(10001, customer1.getId());
        assertEquals(10002, customer2.getId());
        assertEquals(2, customerRepository.getCustomers().size());
    }

    @Test
    public void findCustomerById_Should_ReturnCorrectCustomer() {
        var customer = customerRepository.createCustomer("Ivelin", "Yanev", "0888000002");

        var found = customerRepository.findCustomerById(customer.getId());

        assertEquals("Ivelin", found.getFirstName());
        assertEquals("0888000002", found.getPhoneNum());
    }

    @Test
    public void findCustomerById_Should_ThrowException_When_IdNotFound() {
        assertThrows(ElementNotFoundException.class,
                () -> customerRepository.findCustomerById(9999));
    }

    @Test
    public void findCustomerByPhone_Should_ThrowException_When_PhoneNotFound() {
        assertThrows(ElementNotFoundException.class,
                () -> customerRepository.findCustomerByPhone("0888123456"));
    }

    @Test
    public void customerExists_Should_ReturnTrue_If_CustomerWithPhoneExists() {
        customerRepository.createCustomer("Todor", "Krushkov", "0888000001");

        assertTrue(customerRepository.customerExists("0888000001"));
    }

    @Test
    public void customerExists_Should_ReturnFalse_If_CustomerWithPhoneNotExists() {
        assertFalse(customerRepository.customerExists("0000000000"));
    }

    @Test
    public void getCustomers_Should_Return_CopyOfTheList() {
        customerRepository.createCustomer("Petya", "Dimitrova", "0888999888");
        var list = customerRepository.getCustomers();

        list.clear();
        assertEquals(1, customerRepository.getCustomers().size());
    }
}
