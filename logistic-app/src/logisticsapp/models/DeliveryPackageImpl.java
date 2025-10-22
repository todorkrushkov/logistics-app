package logisticsapp.models;

import logisticsapp.core.enums.State;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;

import java.util.Objects;

import static java.lang.String.format;

public class DeliveryPackageImpl implements DeliveryPackage {
    private static final String WEIGHT_LENGTH_ERR = "Weight can't be negative.";
    private static final String PACKAGE_ALREADY_IN_STATE_ERR = "Package already in state %s";

    private int id;
    private double weight;
    private Location startLocation;
    private Location endLocation;
    private Customer sender;
    private Customer receiver;
    private State state;

    public DeliveryPackageImpl(int id, Customer sender, Customer receiver, double weight,
                               Location startLocation, Location endLocation) {
        setId(id);
        setSender(sender);
        setReceiver(receiver);
        setWeight(weight);
        setStartLocation(startLocation);
        setEndLocation(endLocation);
        state = State.ACCEPTED;
    }

    public DeliveryPackageImpl(int id, Customer sender, Customer receiver, double weight,
                               Location startLocation, Location endLocation, State initialState) {
        setId(id);
        setSender(sender);
        setReceiver(receiver);
        setWeight(weight);
        setStartLocation(startLocation);
        setEndLocation(endLocation);
        this.state = initialState;
    }

    @Override
    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public Customer getSender() {
        return sender;
    }

    private void setSender(Customer sender) {
        this.sender = sender;
    }

    @Override
    public Customer getReceiver() {
        return receiver;
    }

    private void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    private void setWeight(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException(WEIGHT_LENGTH_ERR);
        }

        this.weight = weight;
    }

    @Override
    public Location getStartLocation() {
        return startLocation;
    }

    private void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    @Override
    public Location getEndLocation() {
        return endLocation;
    }

    private void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void changeState(State newState) {
        if (this.state == newState) {
            throw new IllegalStateException(format(PACKAGE_ALREADY_IN_STATE_ERR, getState()));
        }
        this.state = newState;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryPackageImpl that = (DeliveryPackageImpl) o;
        return id == that.id &&
                Double.compare(weight, that.weight) == 0 &&
                Objects.equals(startLocation, that.startLocation) &&
                Objects.equals(endLocation, that.endLocation) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(receiver, that.receiver) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, startLocation, endLocation, sender, receiver, state);
    }

    @Override
    public String printInfo() {
        return format(
                "-> DeliveryPackage %s%n" +
                        "   - State: %s%n" +
                        "   - Weight: %.3f KG%n" +
                        "   - Start Location: %s%n" +
                        "   - End Location: %s%n",
                getId(),
                getState(),
                getWeight(),
                getStartLocation().getCity(),
                getEndLocation().getCity()
        );
    }
}