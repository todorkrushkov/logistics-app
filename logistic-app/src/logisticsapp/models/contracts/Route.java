package logisticsapp.models.contracts;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Route extends Identifiable, Printable {

    List<Integer> getRouteStops();

    Map<Integer, LocalDate> getScheduleMap();

}
