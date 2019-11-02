package challenger.purple.persistence;

import java.util.Map;

public interface Persistence<T> {

    T merge(T object);

    T getById(Integer id);

    Map<Integer,T> get();

}
