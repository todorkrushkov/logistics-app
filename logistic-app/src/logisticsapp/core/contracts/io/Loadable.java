package logisticsapp.core.contracts.io;

import java.util.List;

public interface Loadable<T> {

    void loadFromDtoList(List<T> dtos);

}
