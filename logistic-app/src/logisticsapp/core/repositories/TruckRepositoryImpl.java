package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.TruckRepository;
import logisticsapp.models.TruckImpl;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Truck;
import logisticsapp.models.dtos.TruckDTO;
import logisticsapp.utils.helpers.IdHelper;
import logisticsapp.utils.mappers.TruckMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TruckRepositoryImpl implements TruckRepository {
    public static final String TYPE_OF_OBJECT = "truck";
    public static final int TRUCK_ID = 1000;

    private int nextIdTrucks;
    private final List<Truck> trucks;
    private final Map<Integer, TruckDTO> truckDtoById = new HashMap<>();

    public TruckRepositoryImpl() {
        nextIdTrucks = TRUCK_ID;
        trucks = new ArrayList<>();
    }

    @Override
    public Truck createTruck(String model, double capacity, int range) {
        Truck truck = new TruckImpl(++nextIdTrucks, model, capacity, range);
        this.trucks.add(truck);
        return truck;
    }

    @Override
    public Truck findTruckById(int truckId) {
        return IdHelper.findObjectById(trucks, truckId, TYPE_OF_OBJECT);
    }

    public Truck findTruckByDeliveryPackageId(int deliveryPackageId) {
        return trucks.stream()
                .filter(t -> t.getDeliveryPackages().stream()
                        .anyMatch(dp -> dp.getId() == deliveryPackageId)
                )
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Truck> getTrucks() {
        return new ArrayList<>(trucks);
    }

    @Override
    public void addPackageToTruck(DeliveryPackage deliveryPackage, Truck truck) {
        truck.addDeliveryPackage(deliveryPackage);
    }

    @Override
    public void loadFromDtoList(List<TruckDTO> dtos) {
        trucks.clear();
        truckDtoById.clear();

        int maxId = 0;

        for (TruckDTO dto : dtos) {
            Truck truck = TruckMapper.fromDto(dto);
            trucks.add(truck);
            truckDtoById.put(dto.getId(), dto);
            maxId = Math.max(maxId, dto.getId());
        }

        nextIdTrucks = maxId;
    }

    @Override
    public List<TruckDTO> saveToDtoList() {
        return trucks
                .stream()
                .map(TruckMapper::toDto)
                .toList();
    }

    @Override
    public void restoreTruckPackages(Map<Integer, DeliveryPackage> packageMap) {
        for (Truck truck : trucks) {
            TruckImpl impl = (TruckImpl) truck;
            TruckDTO dto = truckDtoById.get(truck.getId());

            if (dto == null) continue;

            for (int packageId : dto.getDeliveryPackageIds()) {
                DeliveryPackage deliveryPackage = packageMap.get(packageId);
                if (deliveryPackage != null) {
                    impl.addDeliveryPackage(deliveryPackage);
                }
            }
        }
    }
}
