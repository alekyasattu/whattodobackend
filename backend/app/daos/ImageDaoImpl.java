package daos;

import controllers.AttractionsController;
import models.Image;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

public class ImageDaoImpl implements ImageDao {

    private final static Logger.ALogger LOGGER = Logger.of(AttractionsController.class);

    final JPAApi jpaApi;

    @Inject
    public ImageDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

//    @Override
//    public Collection<Image> getImageById(Integer id){
//
//        if(null == id){
//            throw new IllegalArgumentException("id must be provided");
//        }
//
//        LOGGER.debug(String.valueOf(id));
//         Query query= jpaApi.em().createQuery("SELECT imageUrl FROM Image where id = '" + id + "'", Image.class);
//        List images = query.getResultList();
//
//        return images;
//    }

    @Override
    public Image create(Image entity) {
        LOGGER.debug("reached here");

        jpaApi.em().persist(entity);
        return entity;
    }

    @Override
    public Optional<Image> read(String id) {
        return Optional.empty();
    }

    @Override
    public Image update(Image entity) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        Query query  = null;
        if (null == id) {
            throw new IllegalArgumentException("Attraction id must be provided");
        }


//        try {
//            String queryString = "DELETE FROM Image i WHERE attraction.aid = '" + id +"' ";
//          query = jpaApi.em().createQuery(queryString, Integer.class);
//
//        }
//        catch(NoResultException nre)
//        {
//
//        }
        try {
            query = jpaApi.em().createNativeQuery("DELETE FROM Image WHERE image.aid = '" + id +"'");
            query.executeUpdate();

        }
        catch(NoResultException nre)
        {

        }
        if(null==query){
            return  0;
        }
        return 1;
    }

    @Override
    public Collection<Image> all() {
        return null;
    }

    @Override
    public Collection<Image> searchByAttractionId(Integer id) {
        LOGGER.debug("id is " + id);


        if(null == id) {
            throw new IllegalArgumentException("id must be provided");
        }


        Collection<Image> images = null;

        try {
            String queryString = "SELECT i FROM Image i WHERE attraction.aid = '" + id +"' ";
            LOGGER.debug("queryString{} " + queryString);
            TypedQuery<Image> query = jpaApi.em().createQuery(queryString, Image.class);

            images = query.getResultList();

        }
        catch(NoResultException nre){
            throw new IllegalArgumentException("No image found corresponding to given book id");
        }
        return images;


    }




}