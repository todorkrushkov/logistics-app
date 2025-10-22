package logisticsapp.models.contracts;

import java.util.List;

public interface Truck extends Identifiable, Printable {

    String getModel();

    double getCapacity();

    double getCurrentLoad();

    int getRange();

    List<DeliveryPackage> getDeliveryPackages();

    void addDeliveryPackage(DeliveryPackage deliveryPackage);

    void removeDeliveryPackage(DeliveryPackage deliveryPackage);

    double getAvailableCapacity();
}
