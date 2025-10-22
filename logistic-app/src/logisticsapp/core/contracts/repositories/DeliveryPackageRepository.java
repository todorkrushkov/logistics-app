package logisticsapp.core.contracts.repositories;

import logisticsapp.commands.enums.TransferType;
import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.core.enums.State;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.dtos.DeliveryPackageDTO;

import java.util.List;

public interface DeliveryPackageRepository extends Loadable<DeliveryPackage>, Savable<DeliveryPackageDTO> {

    DeliveryPackage createDeliveryPackage(Customer sender, Customer receiver, double weight, Location startLocation, Location endLocation);

    DeliveryPackage findDeliveryPackageById(int id);

    List<DeliveryPackage> findDeliveryPackagesByTransferType(Customer customer, TransferType transferType);

    DeliveryPackage changeState(int deliveryPackageId, State state);

    List<DeliveryPackage> getDeliveryPackages();

    boolean deliveryPackageExists(int id);

}
