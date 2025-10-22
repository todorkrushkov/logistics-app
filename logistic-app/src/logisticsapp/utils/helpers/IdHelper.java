package logisticsapp.utils.helpers;

import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.contracts.Identifiable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class IdHelper {

    public static final String INVALID_OBJECT_WITH_ID_ERR = "Can't find %s with ID %d!";

    public static <T extends Identifiable> T findObjectById(List<T> list, int id, String typeOfObject) {
        for (T item : list) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ElementNotFoundException(format(INVALID_OBJECT_WITH_ID_ERR, typeOfObject, id));
    }

    public static<T> List<T> mapIdsToObjects(List<Integer> ids, Function<Integer, T> resolver) {
        return ids.stream()
                .map(resolver)
                .collect(Collectors.toList());
    }

}
