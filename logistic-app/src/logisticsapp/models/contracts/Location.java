package logisticsapp.models.contracts;

import logisticsapp.models.enums.City;

public interface Location extends Identifiable {

    City getCity();
}
