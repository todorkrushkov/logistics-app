package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.LocationRepository;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.contracts.Location;
import logisticsapp.models.dtos.LocationDTO;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocationRepositoryTests {

    LocationRepository locationRepository;

    @BeforeEach
    public void beforeEach() {
        locationRepository = new LocationRepositoryImpl();
    }

    @Test
    public void createLocation_Should_AddLocationWithIncrementedId() {
        Location location = locationRepository.createLocation(City.SYD);

        assertEquals(1, location.getId());
        assertEquals(City.SYD, location.getCity());
        assertEquals(1, locationRepository.getLocations().size());
    }

    @Test
    public void findLocationById_Should_ReturnCorrectLocation() {
        Location loc1 = locationRepository.createLocation(City.SYD);
        Location loc2 = locationRepository.createLocation(City.MEL);

        Location found = locationRepository.findLocationById(loc2.getId());

        assertEquals(City.MEL, found.getCity());
    }

    @Test
    public void findLocationById_Should_Throw_WhenNotFound() {
        assertThrows(ElementNotFoundException.class, () ->
                locationRepository.findLocationById(999));
    }

    @Test
    public void findIdByLocation_Should_ReturnCorrectId() {
        locationRepository.createLocation(City.SYD);
        int id = locationRepository.findIdByLocation("SYD");

        assertEquals(1, id);
    }

    @Test
    public void findIdByLocation_Should_Throw_WhenNotFound() {
        assertThrows(ElementNotFoundException.class, () ->
                locationRepository.findIdByLocation("SYD"));
    }

    @Test
    public void locationExists_Should_ReturnTrue_WhenCityExists() {
        locationRepository.createLocation(City.SYD);

        assertTrue(locationRepository.locationExists(City.SYD));
    }

    @Test
    public void locationExists_Should_ReturnFalse_WhenCityDoesNotExist() {
        assertFalse(locationRepository.locationExists(City.SYD));
    }

    @Test
    public void getLocations_Should_ReturnCopy_NotOriginalList() {
        locationRepository.createLocation(City.SYD);
        List<Location> copy = locationRepository.getLocations();
        copy.clear();

        assertEquals(1, locationRepository.getLocations().size());
    }

    @Test
    public void mapLocationIdToLocation_Should_MapIdsCorrectly() {
        Location loc1 = locationRepository.createLocation(City.SYD);
        Location loc2 = locationRepository.createLocation(City.MEL);

        List<Location> result = locationRepository.mapLocationIdToLocation(List.of(loc1.getId(), loc2.getId()));

        assertEquals(2, result.size());
        assertEquals(City.SYD, result.get(0).getCity());
        assertEquals(City.MEL, result.get(1).getCity());
    }
}
