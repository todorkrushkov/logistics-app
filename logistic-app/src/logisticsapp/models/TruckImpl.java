package logisticsapp.models;

import logisticsapp.core.exceptions.InvalidUserInputException;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Truck;
import logisticsapp.utils.helpers.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class TruckImpl implements Truck {
    public static final int MODEL_MIN_LENGTH = 2;
    public static final int MODEL_MAX_LENGTH = 30;
    private static final String MODEL_LENGTH_ERR = format(
            "Model should be between %d and %d chars.",
            MODEL_MIN_LENGTH,
            MODEL_MAX_LENGTH);

    public static final int CAPACITY_MIN_KG = 50;
    private static final String CAPACITY_KG_ERR = format(
            "Truck capacity cannot be bellow %d kg.",
            CAPACITY_MIN_KG);

    private static final String RANGE_CANNOT_BE_NEGATIVE_ERR = "Truck capacity cannot be negative!";

    private int id;
    private String model;
    private double capacity;
    private double currentLoad;
    private int range;
    private final List<DeliveryPackage> deliveryPackages;

    public TruckImpl(int id, String model, double capacity, int range) {
        setId(id);
        setModel(model);
        setCapacity(capacity);
        setRange(range);
        currentLoad = 0;
        this.deliveryPackages = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public String getModel() {
        return model;
    }

    private void setModel(String model) {
        ValidationHelper.validateStringLength(model, MODEL_MIN_LENGTH, MODEL_MAX_LENGTH, MODEL_LENGTH_ERR);
        this.model = model;
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

    private void setCapacity(double capacity) {
        if(capacity < CAPACITY_MIN_KG) {
            throw new InvalidUserInputException(CAPACITY_KG_ERR);
        }
        this.capacity = capacity;
    }

    @Override
    public double getCurrentLoad() {
        return currentLoad;
    }

    @Override
    public int getRange() {
        return range;
    }

    private void setRange(int range) {
        if(range < 0) {
            throw new InvalidUserInputException(RANGE_CANNOT_BE_NEGATIVE_ERR);
        }
        this.range = range;
    }

    @Override
    public List<DeliveryPackage> getDeliveryPackages() {
        return new ArrayList<>(deliveryPackages);
    }

    @Override
    public void addDeliveryPackage(DeliveryPackage deliveryPackage) {
        deliveryPackages.add(deliveryPackage);
        currentLoad += deliveryPackage.getWeight();
    }

    @Override
    public void removeDeliveryPackage(DeliveryPackage deliveryPackage) {
        deliveryPackages.remove(deliveryPackage);
        currentLoad -= deliveryPackage.getWeight();
    }

    @Override
    public double getAvailableCapacity() {
        return capacity - currentLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TruckImpl truck = (TruckImpl) o;
        return id == truck.id &&
                Double.compare(capacity, truck.capacity) == 0 &&
                Double.compare(currentLoad, truck.currentLoad) == 0 &&
                range == truck.range &&
                Objects.equals(model, truck.model) &&
                Objects.equals(deliveryPackages, truck.deliveryPackages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, capacity, currentLoad, range, deliveryPackages);
    }

    @Override
    public String printInfo() {
        return format(
                "-> Truck %d%n" +
                        "   - Model: %s%n" +
                        "   - Capacity: %.2f KG%n" +
                        "   - Range: %d KM%n",
                getId(),
                getModel(),
                getCapacity(),
                getRange()
        );
    }
}
