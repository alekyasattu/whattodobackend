package daos;

import models.Attractions;

import java.util.Collection;

public interface AttractionDao extends CrudAttractionDao<Attractions, Integer> {

    Collection<Attractions> findAttrByCity(String city);
    Collection<Attractions> findAttrByUname(String username);
    Collection<Attractions> findAttrByCategory( String category, String city);
    Collection<Attractions> findAllAttr();

}
