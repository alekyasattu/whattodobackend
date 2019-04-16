package daos;

import models.User;

public interface UserDao extends CrudUserDao<User, Integer> {

    User findUserByName(String username);
    User findUserByAuthToken(String authToken);
}