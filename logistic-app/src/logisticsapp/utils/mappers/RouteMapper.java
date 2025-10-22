package logisticsapp.utils.mappers;

import logisticsapp.models.RouteImpl;
import logisticsapp.models.contracts.Route;
import logisticsapp.models.dtos.RouteDTO;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RouteMapper {

    public static RouteDTO toDto(Route route) {
        Map<Integer, String> scheduleMap = new LinkedHashMap<>();
        route.getScheduleMap().forEach((id, date) -> scheduleMap.put(id, date.toString()));

        return new RouteDTO(
                route.getId(),
                route.getRouteStops(),
                scheduleMap
        );
    }

    public static Route fromDto(RouteDTO dto) {
        Map<Integer, LocalDate> scheduleMap = new LinkedHashMap<>();
        dto.getScheduleMap().forEach((id, dateStr) -> scheduleMap.put(id, LocalDate.parse(dateStr)));

        return new RouteImpl(dto.getId(), dto.getRouteStops(), scheduleMap);
    }
}