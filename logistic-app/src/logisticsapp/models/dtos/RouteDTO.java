package logisticsapp.models.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RouteDTO {
    public int id;
    public List<Integer> routeStops;
    public Map<Integer, String> scheduleMap;

    public RouteDTO() {
        // Required no-arg constructor for Jackson
    }

    public RouteDTO(int id, List<Integer> routeStops, Map<Integer, String> scheduleMap) {
        setId(id);
        setRouteStops(routeStops);
        setScheduleMap(scheduleMap);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public List<Integer> getRouteStops() {
        return routeStops;
    }

    private void setRouteStops(List<Integer> routeStops) {
        this.routeStops = routeStops;
    }

    public Map<Integer, String> getScheduleMap() {
        return scheduleMap;
    }

    private void setScheduleMap(Map<Integer, String> scheduleMap) {
        this.scheduleMap = scheduleMap;
    }
}