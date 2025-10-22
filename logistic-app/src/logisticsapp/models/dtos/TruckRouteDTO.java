package logisticsapp.models.dtos;

public class TruckRouteDTO {
    public int truckId;
    public int routeId;

    public TruckRouteDTO() {
        // Required no-arg constructor for Jackson
    }

    public TruckRouteDTO(int truckId, int routeId) {
        setTruckId(truckId);
        setRouteId(routeId);
    }

    public int getTruckId() {
        return truckId;
    }

    private void setTruckId(int truckId) {
        this.truckId = truckId;
    }

    public int getRouteId() {
        return routeId;
    }

    private void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}