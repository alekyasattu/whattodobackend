package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.security.Authenticator;
import daos.AttractionDao;
import daos.*;
import models.Attractions;
import models.Image;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;


public class AttractionsController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(AttractionsController.class);

    //final JPAApi jpaApi; Used incase when the database code is in controller but now its being moved to the dao pkg


    private AttractionDao attractionDao;
    private UserDao userDao;
    private ImageDao imageDao;

//    @Inject
//    public AttractionsController(AttractionDao attractionDao) {
//        this.attractionDao = attractionDao;
//    }

    @Inject
    public AttractionsController(AttractionDao attractionDao, UserDao userDao, ImageDao imageDao) {
        this.attractionDao = attractionDao;
        this.userDao = userDao;
        this.imageDao = imageDao;
    }

    @Transactional
    @Authenticator
    public Result createAttraction() {

        final JsonNode json = request().body().asJson();

        final Attractions attractions = Json.fromJson(json, Attractions.class);

        final User user = (User) ctx().args.get("user");
        attractions.setUser(user);

        LOGGER.debug("user"+ user);
        LOGGER.debug("Attraction Name ="+attractions.getAttr_Name());

        LOGGER.error("This is an error");

        final String attrname = json.get("attr_Name").asText();


        if ( null == attrname) {

            return badRequest("Attraction name must be provided");
        }

        final Attractions na = attractionDao.create(attractions);

        for(String url : attractions.getImageUrls()) {
            final Image image = new Image(url);
            image.setImageUrl(url);
            image.setAttraction(na);
            LOGGER.debug(image.getImageUrl());
            LOGGER.debug(attractions.getAttr_Name());
//            LOGGER.error("image error");
            LOGGER.debug("image" + image);
            imageDao.create(image);
        }

        final JsonNode result = Json.toJson(na);
                return ok(result);
    }

    @Transactional
    public Result updateAttractionById() {

        final JsonNode json = request().body().asJson();
        final Attractions attractions = Json.fromJson(json, Attractions.class);

        if (null == attractions.getAttr_Name() || null == attractions.getCity()) {
            return badRequest("Missing mandatory parameters");
        }

        attractionDao.update(attractions);

        final JsonNode result = Json.toJson(attractions);
        return ok(result);
    }

    @Transactional
    public Result getAttractionByCity() {

        final JsonNode json = request().body().asJson();

        final String city = json.get("city").asText();

        if(null == city)
            return badRequest("Missing Mandatory parameters");

        final Collection<Attractions> attractions = attractionDao.findAttrByCity(city);

        final JsonNode result = Json.toJson(attractions);

        return ok(result);

    }

    @Transactional
    public Result getAttractionByCategory() {

        final JsonNode json = request().body().asJson();

        final String city = json.get("city").asText();
        final String category = json.get("category").asText();


        if(null == category || null == city)
            return badRequest("Missing Mandatory parameters");

        final Collection<Attractions> attractions = attractionDao.findAttrByCategory(category, city);

        final JsonNode result = Json.toJson(attractions);

        return ok(result);


    }

    @Transactional
    public Result deleteAttractionById() {

        final JsonNode json = request().body().asJson();

        final Integer id = json.get("aid").asInt();


        if (null == id) {
            return badRequest("Id must be provided");
        }

        imageDao.delete(id);
        final Attractions attractions = attractionDao.delete(id);

        final JsonNode result = Json.toJson(attractions);
        return ok(result);
    }

    @Transactional
    public Result getAttractionById() {

        final JsonNode json = request().body().asJson();

        final Integer id = json.get("aid").asInt();

        if(null == id)
            return badRequest("Missing Mandatory parameters");

        final Optional<Attractions> attractions = attractionDao.read(id);


        if (attractions.isPresent()) {
            final JsonNode result = Json.toJson(attractions.get());
            return ok(result);
        } else {
            return notFound();
        }

    }


    @Transactional
    public Result getAttractionByUser() {

        final JsonNode json = request().body().asJson();

        final String token = json.get("accessToken").asText();
        LOGGER.debug("token"+ token);
        if(null == token)
            return badRequest("Missing Mandatory parameters");
        final User user = userDao.findUserByAuthToken(token);

        final String username = user.getUsername();

        final Collection<Attractions> attractions = attractionDao.findAttrByUname(username);

        final JsonNode result = Json.toJson(attractions);

        return ok(result);



    }

    @Transactional
    public Result getAllAttractions() {


        final Collection<Attractions> attractions = attractionDao.findAllAttr();

        final JsonNode result = Json.toJson(attractions);

        return ok(result);


    }



}
