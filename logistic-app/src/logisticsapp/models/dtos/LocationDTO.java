package logisticsapp.models.dtos;

public class LocationDTO {
    public int id;
    public String city;

    public LocationDTO() {
        // Required no-arg constructor for Jackson
    }

    public LocationDTO(int id, String city) {
        this.id = id;
        this.city = city;
    }
}
