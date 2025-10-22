package logisticsapp.models;

import logisticsapp.models.contracts.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RouteImplTests {

    private Route route;
    private List<Integer> routeStops;
    private Map<Integer, LocalDate> scheduleMap;

    @BeforeEach
    void setUp() {
        routeStops = Arrays.asList(1, 2, 3);
        scheduleMap = new LinkedHashMap<>();
        scheduleMap.put(1, LocalDate.of(2025, 7, 15));
        scheduleMap.put(2, LocalDate.of(2025, 7, 16));
        scheduleMap.put(3, LocalDate.of(2025, 7, 17));
        route = new RouteImpl(101, routeStops, scheduleMap);
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        assertEquals(101, route.getId());
        assertEquals(routeStops, route.getRouteStops());
        assertEquals(scheduleMap, route.getScheduleMap());
    }

    @Test
    void getId_ShouldReturnCorrectId() {
        assertEquals(101, route.getId());
    }

    @Test
    void getRouteStops_ShouldReturnCorrectStops() {
        assertEquals(routeStops, route.getRouteStops());
    }

    @Test
    void getScheduleMap_ShouldReturnCorrectSchedule() {
        assertEquals(scheduleMap, route.getScheduleMap());
    }

    @Test
    void printInfo_ShouldReturnCorrectFormat() {
        assertEquals(String.format("-> Route %d%n", 101), route.printInfo());
    }

    @Test
    void equals_ShouldReturnTrueForSameContent() {
        Route other = new RouteImpl(101, routeStops, scheduleMap);
        assertEquals(route, other);
        assertEquals(route.hashCode(), other.hashCode());
    }

    @Test
    void equals_ShouldReturnFalseForDifferentId() {
        Route other = new RouteImpl(102, routeStops, scheduleMap);
        assertNotEquals(route, other);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentStops() {
        List<Integer> otherStops = Arrays.asList(1, 2);
        Route other = new RouteImpl(101, otherStops, scheduleMap);
        assertNotEquals(route, other);
    }

    @Test
    void equals_ShouldReturnFalseForDifferentSchedule() {
        Map<Integer, LocalDate> otherMap = new LinkedHashMap<>(scheduleMap);
        otherMap.put(4, LocalDate.of(2025, 7, 18));
        Route other = new RouteImpl(101, routeStops, otherMap);
        assertNotEquals(route, other);
    }
}
