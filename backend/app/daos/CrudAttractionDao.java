package daos;

import java.util.Optional;

public interface CrudAttractionDao<E, K> {

    E create(E entity);
    Optional<E> read(K aid);
    E update(E entity);
    E delete(K aid);
}
