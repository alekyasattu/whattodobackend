package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.security.Authenticator;
import daos.AttractionDao;
import daos.ImageDao;
import daos.ScheduleDao;
import daos.UserDao;
import models.Attractions;
import models.Image;
import models.Schedule;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.Collection;
import java.util.Optional;

import static play.mvc.Controller.ctx;
import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

public class ScheduleController {

    private final static Logger.ALogger LOGGER = Logger.of(ScheduleController.class);

    //final JPAApi jpaApi; Used incase when the database code is in controller but now its being moved to the dao pkg


    private ScheduleDao scheduleDao;
    private AttractionDao attractionDao;
    private UserDao userDao;
    private ImageDao imageDao;

    @Inject
    public ScheduleController(ScheduleDao scheduleDao, AttractionDao attractionDao, UserDao userDao, ImageDao imageDao) {
        this.scheduleDao = scheduleDao;
        this.attractionDao = attractionDao;
        this.userDao = userDao;
        this.imageDao = imageDao;
    }

    @Transactional
    @Authenticator
    public Result createSchedule() {

        final JsonNode json = request().body().asJson();

        final Schedule schedule = Json.fromJson(json, Schedule.class);

        final User user = (User) ctx().args.get("user");
        schedule.setUser(user);

        LOGGER.debug("user"+ user);

        LOGGER.error("This is an error");

        final Integer aid = json.get("aid").asInt();



        if ( null == aid) {

            return badRequest("Attraction id must be provided");
        }
        final Optional<Attractions> attractions1 = attractionDao.read(aid);

        if(attractions1.isPresent()) {
            Attractions attractions = attractions1.get();
            schedule.setAttraction(attractions);
        }

        Schedule schedule_returned = scheduleDao.create(schedule);

        final JsonNode result = Json.toJson(schedule_returned);
        return ok(result);
    }

    @Transactional
    public Result getScheduleByUser() {

        final JsonNode json = request().body().asJson();

        final String token = json.get("accessToken").asText();
        LOGGER.debug("token"+ token);
        if(null == token)
            return badRequest("Missing Mandatory parameters");
        final User user = userDao.findUserByAuthToken(token);

        final String username = user.getUsername();

        final Collection<Schedule> schedule = scheduleDao.findScheduleByUser(username);

        final JsonNode result = Json.toJson(schedule);

        return ok(result);

    }

    @Transactional
    public Result deleteScheduleById() {

        final JsonNode json = request().body().asJson();

        final Integer id = json.get("sid").asInt();


        if (null == id) {
            return badRequest("Id must be provided");
        }

        final Schedule schedule = scheduleDao.deleteById(id);

        final JsonNode result = Json.toJson(schedule);
        return ok(result);
    }
}
