package logisticsapp.core.services;

import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.enums.State;
import logisticsapp.models.contracts.DeliveryPackage;

public class ChangeStateService {
    private final TruckAssignmentService truckAssignmentService;
    private final DeliveryPackageRepository deliveryPackageRepository;

    public ChangeStateService(TruckAssignmentService truckAssignmentService,
                              DeliveryPackageRepository deliveryPackageRepository) {
        this.truckAssignmentService = truckAssignmentService;
        this.deliveryPackageRepository = deliveryPackageRepository;
    }

    public DeliveryPackage changeState(int deliveryPackageId, State state) {
        DeliveryPackage deliveryPackage = deliveryPackageRepository.changeState(deliveryPackageId, state);
        truckAssignmentService.removePackageFromTruckWhenDelivered(deliveryPackageId);
        return deliveryPackage;
    }
}
