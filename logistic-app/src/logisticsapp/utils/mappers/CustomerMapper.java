package logisticsapp.utils.mappers;

import logisticsapp.models.CustomerImpl;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.dtos.CustomerDTO;

import java.util.List;

public final class CustomerMapper {

    /** model ➜ DTO */
    public static CustomerDTO toDto(Customer c) {
        List<Integer> pkgIds = c.getDeliveryPackage().stream()
                .map(DeliveryPackage::getId)
                .toList();
        return new CustomerDTO(
                c.getId(),
                c.getFirstName(),
                c.getLastName(),
                c.getPhoneNum(),
                pkgIds
        );
    }

    /** DTO ➜ model (initial state, *without* packages) */
    public static Customer fromDtoBare(CustomerDTO dto) {
        return new CustomerImpl(dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhoneNum());
    }
}