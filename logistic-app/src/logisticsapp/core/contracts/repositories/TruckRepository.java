package logisticsapp.core.contracts.repositories;

import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.TruckDTO;

import java.util.List;
import java.util.Map;

public interface TruckRepository extends Loadable<TruckDTO>, Savable<TruckDTO> {

    Truck createTruck(String model, double capacity, int range);

    Truck findTruckById(int id);

    List<Truck> getTrucks();

    void addPackageToTruck(DeliveryPackage deliveryPackage, Truck truck);

    Truck findTruckByDeliveryPackageId(int deliveryPackageId);

    void restoreTruckPackages(Map<Integer, DeliveryPackage> packageMap);
}
