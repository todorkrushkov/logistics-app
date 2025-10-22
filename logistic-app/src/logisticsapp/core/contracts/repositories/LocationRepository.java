package logisticsapp.core.contracts.repositories;

import logisticsapp.core.contracts.io.Loadable;
import logisticsapp.core.contracts.io.Savable;
import logisticsapp.models.dtos.LocationDTO;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;

import java.util.List;

public interface LocationRepository extends Loadable<LocationDTO>, Savable<LocationDTO> {

    Location createLocation(City city);

    Location findLocationById(int id);

    int findIdByLocation(String locationInput);

    List<Location> mapLocationIdToLocation(List<Integer> locationIds);

    List<Location> getLocations();

    boolean locationExists(City city);
}
