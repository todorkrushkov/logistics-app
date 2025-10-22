package logisticsapp.core.contracts.io;

import java.util.List;

public interface Savable<T> {

    List<T> saveToDtoList();

}
