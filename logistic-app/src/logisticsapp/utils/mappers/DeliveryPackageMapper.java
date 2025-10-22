package logisticsapp.utils.mappers;

import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.dtos.DeliveryPackageDTO;

public final class DeliveryPackageMapper {

    public static DeliveryPackageDTO toDto(DeliveryPackage deliveryPackage) {
        return new DeliveryPackageDTO(
                deliveryPackage.getId(),
                deliveryPackage.getWeight(),
                deliveryPackage.getStartLocation().getId(),
                deliveryPackage.getEndLocation().getId(),
                deliveryPackage.getSender().getId(),
                deliveryPackage.getReceiver().getId(),
                deliveryPackage.getState().name()
        );
    }
}
