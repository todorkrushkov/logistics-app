package logisticsapp.utils.helpers;

import logisticsapp.core.contracts.repositories.CustomerRepository;
import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.models.DeliveryPackageImpl;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.dtos.DeliveryPackageDTO;
import logisticsapp.core.enums.State;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryPackageLoader {

    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;

    public DeliveryPackageLoader(CustomerRepository customerRepository, LocationRepository locationRepository) {
        this.customerRepository = customerRepository;
        this.locationRepository = locationRepository;
    }

    public List<DeliveryPackage> resolvePackages(List<DeliveryPackageDTO> dtos) {
        return dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private DeliveryPackage toModel(DeliveryPackageDTO dto) {
        Customer sender = customerRepository.findCustomerById(dto.getSenderId());
        Customer receiver = customerRepository.findCustomerById(dto.getReceiverId());
        Location start = locationRepository.findLocationById(dto.getStartLocationId());
        Location end = locationRepository.findLocationById(dto.getEndLocationId());

        return new DeliveryPackageImpl(
                dto.getId(),
                sender,
                receiver,
                dto.getWeight(),
                start,
                end,
                State.valueOf(dto.getState())
        );
    }
}