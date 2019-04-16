package daos;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Attractions;
import play.db.jpa.JPAApi;

import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static play.mvc.Controller.request;

public class AttractionDaoImpl implements AttractionDao {

    private final JPAApi jpaApi;


    @Inject
    public AttractionDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public Attractions create(Attractions attractions) {

        if (null == attractions) {

            throw new IllegalArgumentException("Attraction must be provided");
        }

        jpaApi.em().persist(attractions);
        return attractions;

    }

    public Attractions delete(Integer id) {

        if (null == id) {
            throw new IllegalArgumentException("Attraction id must be provided");
        }

        final Attractions existingAttraction = jpaApi.em().find(Attractions.class, id);
        if (null == existingAttraction) {
            return null;
        }

        jpaApi.em().remove(existingAttraction);

        return existingAttraction;
    }

    @Override
    public Collection<Attractions> findAttrByCity(String city) {

        TypedQuery<Attractions> query = jpaApi.em().createQuery("SELECT a FROM Attractions a where city LIKE '%"+city+"%'", Attractions.class);
        List<Attractions> cards= query.getResultList();

        return cards;
    }

    @Override
    public Collection<Attractions> findAttrByUname(String username) {

        TypedQuery<Attractions> query = jpaApi.em().createQuery("SELECT a FROM Attractions a where username  LIKE '%"+username+"%'", Attractions.class);
        List<Attractions> cards= query.getResultList();

        return cards;
    }

    @Override
    public Collection<Attractions> findAttrByCategory(String category, String city) {
        TypedQuery<Attractions> query = jpaApi.em().createQuery("SELECT a FROM Attractions a where category LIKE '%"+category+"%' and city Like '%"+city+"%'", Attractions.class);
        List<Attractions> cards= query.getResultList();

        return cards;
    }

    @Override
    public Collection<Attractions> findAllAttr() {
        TypedQuery<Attractions> query = jpaApi.em().createQuery("SELECT a FROM Attractions a", Attractions.class);
        List<Attractions> cards= query.getResultList();

        return cards;
    }

    public Attractions update(Attractions attractions){

        if(null == attractions){
            throw new  IllegalArgumentException("Attraction Details must be provided ");
        }

        if(null==attractions.getAid()){
            throw new IllegalArgumentException("Id must be specified");
        }

        final Attractions existingAttr = jpaApi.em().find(Attractions.class,attractions.getAid());

        if(null== existingAttr){
            return null;
        }


        existingAttr.setCategory(attractions.getCategory());
        existingAttr.setDescription(attractions.getDescription());
        existingAttr.setLocation(attractions.getLocation());
        existingAttr.setFrom_Timings(attractions.getFrom_Timings());
        existingAttr.setUpto_Timings(attractions.getUpto_Timings());
        existingAttr.setRating(attractions.getRating());
        existingAttr.setFare(attractions.getFare());

        jpaApi.em().persist(existingAttr);

        return existingAttr;

    }

    public Optional<Attractions> read(Integer id) {

        if (null == id) {
            throw new IllegalArgumentException("Id must be provided");
        }

        final Attractions attractions = jpaApi.em().find(Attractions.class, id);
        return attractions != null ? Optional.of(attractions) : Optional.empty();
    }

}


