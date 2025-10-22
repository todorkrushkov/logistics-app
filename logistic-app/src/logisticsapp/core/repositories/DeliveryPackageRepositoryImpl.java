package logisticsapp.core.repositories;

import logisticsapp.commands.enums.TransferType;
import logisticsapp.core.enums.State;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.DeliveryPackageImpl;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.dtos.DeliveryPackageDTO;
import logisticsapp.utils.mappers.DeliveryPackageMapper;
import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.utils.helpers.IdHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryPackageRepositoryImpl implements DeliveryPackageRepository {
    public static final String NO_DELIVERY_PACKAGES_FOR_CUSTOMER_ERR = "No delivery packages for this customer.";
    public static final String TYPE_OF_OBJECT = "delivery package";
    public static final int DELIVERY_PACKAGE_ID = 100000;

    private int nextIdDeliveryPackage;

    private final List<DeliveryPackage> deliveryPackages;

    public DeliveryPackageRepositoryImpl() {
        nextIdDeliveryPackage = DELIVERY_PACKAGE_ID;
        deliveryPackages = new ArrayList<>();
    }

    @Override
    public DeliveryPackage createDeliveryPackage(Customer sender, Customer receiver, double weight, Location startLocation, Location endLocation) {
        DeliveryPackage deliveryPackage = new DeliveryPackageImpl(++nextIdDeliveryPackage, sender, receiver, weight, startLocation, endLocation);

        this.deliveryPackages.add(deliveryPackage);
        sender.addDeliveryPackage(deliveryPackage);
        receiver.addDeliveryPackage(deliveryPackage);

        return deliveryPackage;
    }

    @Override
    public DeliveryPackage findDeliveryPackageById(int deliveryPackageId) {
        return IdHelper.findObjectById(deliveryPackages, deliveryPackageId, TYPE_OF_OBJECT);
    }

    @Override
    public List<DeliveryPackage> findDeliveryPackagesByTransferType(Customer customer, TransferType transferType) {
        if (transferType.equals(TransferType.RECEIVE)) {
            return customer.getDeliveryPackage()
                    .stream()
                    .filter(dp -> dp.getReceiver().equals(customer))
                    .collect(Collectors.toList());
        }
        if (transferType.equals(TransferType.SENT)) {
            return customer.getDeliveryPackage()
                    .stream()
                    .filter(dp -> dp.getSender().equals(customer))
                    .collect(Collectors.toList());
        }
        throw new ElementNotFoundException(NO_DELIVERY_PACKAGES_FOR_CUSTOMER_ERR);
    }

    @Override
    public DeliveryPackage changeState(int deliveryPackageId, State state) {
        DeliveryPackage deliveryPackage = findDeliveryPackageById(deliveryPackageId);
        deliveryPackage.changeState(state);
        return deliveryPackage;
    }

    @Override
    public void loadFromDtoList(List<DeliveryPackage> packages) {
        deliveryPackages.clear();
        deliveryPackages.addAll(packages);

        int maxId = packages.stream()
                .mapToInt(DeliveryPackage::getId)
                .max()
                .orElse(DELIVERY_PACKAGE_ID);

        nextIdDeliveryPackage = maxId;
    }

    @Override
    public List<DeliveryPackageDTO> saveToDtoList() {
        return deliveryPackages.stream()
                .map(DeliveryPackageMapper::toDto)
                .toList();
    }
    public List<DeliveryPackage> getDeliveryPackages() {
        return new ArrayList<>(deliveryPackages);
    }

    @Override
    public boolean deliveryPackageExists(int id) {
        return deliveryPackages.stream().anyMatch(dp -> dp.getId() == id);
    }
}
