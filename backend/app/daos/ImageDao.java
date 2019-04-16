package daos;

import models.Image;

import java.util.Collection;

public interface ImageDao extends CrudDao<Image, String> {

    Collection<Image> searchByAttractionId(Integer id);
    Integer delete(Integer id);


}