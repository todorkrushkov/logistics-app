package logisticsapp.models.contracts;

import java.util.List;

public interface Customer extends Identifiable, Printable {

    String getFirstName();

    String getLastName();

    String getPhoneNum();

    List<DeliveryPackage> getDeliveryPackage();

    void addDeliveryPackage(DeliveryPackage deliveryPackage);

}
