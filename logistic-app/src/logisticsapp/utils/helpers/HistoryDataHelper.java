package logisticsapp.utils.helpers;

import logisticsapp.models.dtos.*;

import java.util.List;

public class HistoryDataHelper {
    private List<LocationDTO> locations;
    private List<TruckDTO> trucks;
    private List<RouteDTO> routes;
    private List<DeliveryPackageDTO> packages;
    private List<CustomerDTO> customers;
    private List<TruckRouteDTO> truckRoutes;

    public HistoryDataHelper() {}

    public List<LocationDTO> getLocations() { return locations; }
    public void setLocations(List<LocationDTO> locations) { this.locations = locations; }

    public List<TruckDTO> getTrucks() { return trucks; }
    public void setTrucks(List<TruckDTO> trucks) { this.trucks = trucks; }

    public List<RouteDTO> getRoutes() { return routes; }
    public void setRoutes(List<RouteDTO> routes) { this.routes = routes; }

    public List<DeliveryPackageDTO> getPackages() { return packages; }
    public void setPackages(List<DeliveryPackageDTO> packages) { this.packages = packages; }

    public List<CustomerDTO> getCustomers() { return customers; }
    public void setCustomers(List<CustomerDTO> customers) { this.customers = customers; }

    public List<TruckRouteDTO> getTruckRoutes() { return truckRoutes; }
    public void setTruckRoutes(List<TruckRouteDTO> truckRoutes) { this.truckRoutes = truckRoutes; }
}