package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.contracts.Identifiable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class IdHelperTests {

    static class DummyIdentifiable implements Identifiable {
        private final int id;
        private final String name;

        public DummyIdentifiable(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void findObjectById_ShouldReturnCorrectObject_WhenIdExists() {
        List<DummyIdentifiable> list = Arrays.asList(
                new DummyIdentifiable(1, "One"),
                new DummyIdentifiable(2, "Two")
        );

        DummyIdentifiable result = IdHelper.findObjectById(list, 2, "Dummy");

        assertEquals("Two", result.getName());
    }

    @Test
    public void findObjectById_ShouldThrowException_WhenIdNotFound() {
        List<DummyIdentifiable> list = Arrays.asList(
                new DummyIdentifiable(1, "One")
        );

        ElementNotFoundException ex = assertThrows(
                ElementNotFoundException.class,
                () -> IdHelper.findObjectById(list, 99, "Dummy")
        );

        assertEquals("Can't find Dummy with ID 99!", ex.getMessage());
    }

    @Test
    public void mapIdsToObjects_ShouldReturnCorrectMappedList() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        Function<Integer, DummyIdentifiable> resolver = id -> new DummyIdentifiable(id, "Name" + id);

        List<DummyIdentifiable> result = IdHelper.mapIdsToObjects(ids, resolver);

        assertEquals(3, result.size());
        assertEquals("Name1", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Name3", result.get(2).getName());
    }
}