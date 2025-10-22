package logisticsapp.models.dtos;

import java.util.List;

public class TruckDTO {
    private int id;
    private String model;
    private double capacity;
    private double currentLoad;
    private int range;
    private List<Integer> packageIds;

    public TruckDTO() {
        // Required no-arg constructor for Jackson
    }

    public TruckDTO(int id, String model, double capacity, double currentLoad, int range, List<Integer> packageIds) {
        setId(id);
        setModel(model);
        setCapacity(capacity);
        setCurrentLoad(currentLoad);
        setRange(range);
        setDeliveryPackageIds(packageIds);
    }

    public int getId() { return id; }

    private void setId(int id) { this.id = id; }

    public String getModel() { return model; }

    private void setModel(String model) { this.model = model; }

    public double getCapacity() { return capacity; }

    private void setCapacity(double capacity) { this.capacity = capacity; }

    public double getCurrentLoad() { return currentLoad; }

    private void setCurrentLoad(double currentLoad) { this.currentLoad = currentLoad; }

    public int getRange() { return range; }

    private void setRange(int range) { this.range = range; }

    public List<Integer> getDeliveryPackageIds() {
        return packageIds;
    }

    private void setDeliveryPackageIds(List<Integer> packageIds) {
        this.packageIds = packageIds;
    }
}