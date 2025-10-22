package logisticsapp.utils.mappers;

import logisticsapp.models.TruckImpl;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.TruckDTO;

public final class TruckMapper {
    public static TruckDTO toDto(Truck truck) {
        return new TruckDTO(
                truck.getId(),
                truck.getModel(),
                truck.getCapacity(),
                truck.getCurrentLoad(),
                truck.getRange(),
                truck.getDeliveryPackages().stream().map(DeliveryPackage::getId).toList()
        );
    }

    public static TruckImpl fromDto(TruckDTO dto) {
        return new TruckImpl(dto.getId(), dto.getModel(), dto.getCapacity(), dto.getRange());
    }
}
