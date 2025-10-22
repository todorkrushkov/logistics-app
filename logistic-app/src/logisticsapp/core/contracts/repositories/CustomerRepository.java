package logisticsapp.core.contracts.repositories;

import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.dtos.CustomerDTO;

import java.util.List;
import java.util.Map;

public interface CustomerRepository extends Loadable<CustomerDTO>, Savable<CustomerDTO> {

    Customer createCustomer(String firstName, String lastName, String phoneNum);

    Customer findCustomerById(int id);

    Customer findCustomerByPhone(String phoneNum);

    List<Customer> getCustomers();

    boolean customerExists(String phoneNum);

    void restoreCustomerPackages(Map<Integer, DeliveryPackage> pkgMap);
}
