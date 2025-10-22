package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.CustomerImpl;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.utils.helpers.IdHelper;
import logisticsapp.models.dtos.CustomerDTO;
import logisticsapp.utils.mappers.CustomerMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {
    public static final String CUSTOMER_WITH_PHONE_NUMBER_NOT_FOUND_ERR = "Customer with phone number %s not found";
    public static final String TYPE_OF_OBJECT = "customer";
    public static final int CUSTOMER_ID = 10000;

    private int nextIdCustomers;
    private final List<Customer> customers;
    private final Map<Integer, CustomerDTO> customerDtoById;

    public CustomerRepositoryImpl() {
        nextIdCustomers = CUSTOMER_ID;
         customers = new ArrayList<>();
         customerDtoById = new HashMap<>();
    }

    @Override
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public Customer createCustomer(String firstName, String lastName, String phoneNum) {
        Customer customer = new CustomerImpl(++nextIdCustomers, firstName, lastName, phoneNum);
        this.customers.add(customer);
        return customer;
    }

    @Override
    public Customer findCustomerById(int customerId) {
        return IdHelper.findObjectById(customers, customerId, TYPE_OF_OBJECT);
    }

    @Override
    public Customer findCustomerByPhone(String phoneNum) {
        return customers.stream()
                .filter(c -> c.getPhoneNum().equals(phoneNum))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format(CUSTOMER_WITH_PHONE_NUMBER_NOT_FOUND_ERR, phoneNum)
                ));
    }

    @Override
    public boolean customerExists(String phoneNum) {
        return customers.stream()
                .anyMatch(c -> c.getPhoneNum().equals(phoneNum));
    }

    @Override
    public void loadFromDtoList(List<CustomerDTO> dtos) {
        customers.clear();
        customerDtoById.clear();

        int maxId = 0;

        for (CustomerDTO dto : dtos) {
            Customer model = CustomerMapper.fromDtoBare(dto);
            customers.add(model);
            customerDtoById.put(dto.getId(), dto);
            maxId = Math.max(maxId, dto.getId());
        }

        nextIdCustomers = maxId;

    }

    @Override
    public List<CustomerDTO> saveToDtoList() {
        return customers
                .stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    @Override
    public void restoreCustomerPackages(Map<Integer, DeliveryPackage> pkgMap) {
        for (Customer c : customers) {
            CustomerDTO dto = customerDtoById.get(c.getId());

            if (dto == null) continue;

            List<Integer> packageIds = dto.getPackageIds();
            if (packageIds == null || packageIds.isEmpty()) continue;

            for (int packageId : packageIds) {

                DeliveryPackage dp = pkgMap.get(packageId);
                if (dp != null) {
                    c.addDeliveryPackage(dp);
                }
            }
        }
    }
}
