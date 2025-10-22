package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.dtos.LocationDTO;
import logisticsapp.models.LocationImpl;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;
import logisticsapp.utils.helpers.IdHelper;

import java.util.ArrayList;
import java.util.List;

public class LocationRepositoryImpl implements LocationRepository {
    public static final String TYPE_OF_OBJECT = "location";
    public static final String LOCATION_ID_NOT_FOUND_ERR = "No ID for location %s";
    public static final int LOCATION_ID = 0;

    private int nextIdLocations;
    private final List<Location> locations;

    public LocationRepositoryImpl() {
        nextIdLocations = LOCATION_ID;
        locations = new ArrayList<>();
    }

    @Override
    public Location createLocation(City city) {
        Location location = new LocationImpl(++nextIdLocations, city);
        this.locations.add(location);
        return location;
    }

    @Override
    public List<Location> mapLocationIdToLocation(List<Integer> locationIds) {
        return IdHelper.mapIdsToObjects(locationIds, this::findLocationById);
    }

    @Override
    public Location findLocationById(int locationId) {
        return IdHelper.findObjectById(locations, locationId, TYPE_OF_OBJECT);
    }

    @Override
    public int findIdByLocation(String locationInput) {
        for (Location location : locations) {
            if (location.getCity().name().equals(locationInput)) {
                return location.getId();
            }
        }
        throw new ElementNotFoundException(String.format(LOCATION_ID_NOT_FOUND_ERR, locationInput));
    }

    @Override
    public List<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    @Override
    public boolean locationExists(City city) {
        return locations.stream()
                .anyMatch(l -> l.getCity().equals(city));
    }

    @Override
    public void loadFromDtoList(List<LocationDTO> dtos) {
        locations.clear();
        int maxId = 0;

        for (LocationDTO dto : dtos) {
            City city = City.valueOf(dto.city);
            Location location = new LocationImpl(dto.id, city);
            locations.add(location);
            maxId = Math.max(maxId, dto.id);
        }

        nextIdLocations = maxId;
    }

    @Override
    public List<LocationDTO> saveToDtoList() {
        return locations.stream()
                .map(l -> new LocationDTO(l.getId(), l.getCity().name()))
                .toList();
    }
}