package logisticsapp.models.contracts;

import logisticsapp.core.enums.State;

public interface DeliveryPackage extends Identifiable, Printable {

    Customer getSender();

    Customer getReceiver();

    double getWeight();

    Location getStartLocation();

    Location getEndLocation();

    State getState();

    void changeState(State state);

}
