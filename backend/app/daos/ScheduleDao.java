package daos;

import models.Schedule;

import java.util.Collection;

public interface ScheduleDao {

    Schedule create(Schedule schedule);
    Collection<Schedule> findScheduleByUser(String username);
    Schedule deleteById(Integer id);
}
