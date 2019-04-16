package daos;

import java.util.Collection;
import java.util.Optional;

public interface CrudUserDao<E, K> {

    E create(E entity);
    E update(E entity);
//    Collection<E> all();

}
