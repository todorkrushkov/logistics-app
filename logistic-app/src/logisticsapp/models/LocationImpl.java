package logisticsapp.models;

import logisticsapp.models.contracts.Location;
import logisticsapp.models.enums.City;

import java.util.Objects;

public class LocationImpl implements Location {

    int id;
    City city;

    public LocationImpl(int id, City city) {
        setId(id);
        setCity(city);
    }

    public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    private void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LocationImpl location = (LocationImpl) o;
        return id == location.id && city == location.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }
}
