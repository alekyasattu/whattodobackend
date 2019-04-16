package daos;

import models.Attractions;
import models.Schedule;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

public class ScheduleDaoImpl implements ScheduleDao{

    private final JPAApi jpaApi;

    @Inject
    public ScheduleDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Schedule create(Schedule schedule) {
        if (null == schedule) {

            throw new IllegalArgumentException("schedule must be provided");
        }

        jpaApi.em().persist(schedule);
        return schedule;

    }

    @Override
    public Collection<Schedule> findScheduleByUser(String username) {

        TypedQuery<Schedule> query = jpaApi.em().createQuery("SELECT a FROM Schedule a where username  LIKE '%"+username+"%' ORDER BY date ", Schedule.class);
        List<Schedule> cards= query.getResultList();

        return cards;
    }

    public Schedule deleteById(Integer id) {

        if (null == id) {
            throw new IllegalArgumentException("id must be provided");
        }

        final Schedule existingSchedule = jpaApi.em().find(Schedule.class, id);
        if (null == existingSchedule) {
            return null;
        }

        jpaApi.em().remove(existingSchedule);

        return existingSchedule;
    }
}
