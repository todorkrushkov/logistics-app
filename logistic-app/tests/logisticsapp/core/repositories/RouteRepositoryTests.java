package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.RouteRepository;
import logisticsapp.models.contracts.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RouteRepositoryTests {

    RouteRepository routeRepository;

    @BeforeEach
    public void beforeEach() {
        routeRepository = new RouteRepositoryImpl();
    }

    @Test
    public void createRoute_ShouldAddRouteToRepository() {
        List<Integer> stops = List.of(1, 2, 3);
        Map<Integer, LocalDate> schedule = Map.of(1, LocalDate.now());

        Route created = routeRepository.createRoute(stops, schedule);

        assertNotNull(created);
        assertEquals(stops, created.getRouteStops());
        assertEquals(schedule, created.getScheduleMap());

        Route found = routeRepository.findRouteById(created.getId());
        assertEquals(created, found);
    }

    @Test
    public void routeExists_ShouldReturnTrue_WhenMatchingStopsExist() {
        List<Integer> stops = List.of(10, 20, 30);
        routeRepository.createRoute(stops, Map.of(10, LocalDate.now()));

        assertTrue(routeRepository.routeExists(stops));
    }

    @Test
    public void routeExists_ShouldReturnFalse_WhenNoMatch() {
        assertFalse(routeRepository.routeExists(List.of(100, 200, 300)));
    }
}

