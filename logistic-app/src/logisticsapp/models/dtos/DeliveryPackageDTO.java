package logisticsapp.models.dtos;

public class DeliveryPackageDTO {
    public int id;
    public double weight;
    public int startLocationId;
    public int endLocationId;
    public int senderId;
    public int receiverId;
    public String state;

    public DeliveryPackageDTO() {
        // Required no-arg constructor for Jackson
    }

    public DeliveryPackageDTO(int id, double weight, int startLocationId, int endLocationId, int senderId, int receiverId, String state) {
        setId(id);
        setWeight(weight);
        setStartLocationId(startLocationId);
        setEndLocationId(endLocationId);
        setSenderId(senderId);
        setReceiverId(receiverId);
        setState(state);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    private void setWeight(double weight) {
        this.weight = weight;
    }

    public int getStartLocationId() {
        return startLocationId;
    }

    private void setStartLocationId(int startLocationId) {
        this.startLocationId = startLocationId;
    }

    public int getEndLocationId() {
        return endLocationId;
    }

    private void setEndLocationId(int endLocationId) {
        this.endLocationId = endLocationId;
    }

    public int getSenderId() {
        return senderId;
    }

    private void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    private void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }
}
