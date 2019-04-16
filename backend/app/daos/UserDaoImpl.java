package daos;

import models.User;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {

    final JPAApi jpaApi;

    private User user;

    @Inject
    public UserDaoImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public User create(User user){

        if(null == user){
            throw new  IllegalArgumentException("User details must be provided ");
        }
        jpaApi.em().persist(user);
        return user;

    }
    public User findUserByName(String username){
        if(null==username){
            throw new IllegalArgumentException("Username must be specified");
        }
        final User user = jpaApi.em().find(User.class,username);


        return user;
    }

    public User update(User user){

        if(null == user){
            throw new  IllegalArgumentException("User Details must be provided ");
        }

        if(null==user.getUsername()){
            throw new IllegalArgumentException("Name must be specified");
        }

        final User existingUser = jpaApi.em().find(User.class,user.getUsername());

        if(null== existingUser){
            return null;
        }


        existingUser.setEmail(user.getEmail());
        existingUser.setCity(user.getCity());

        jpaApi.em().persist(existingUser);

        return existingUser;

    }

    public User findUserByAuthToken(String authToken) {
        User user = null;

        try {
            //LOGGER.debug("accessToken at find by level" + authToken);
            TypedQuery<User> query = jpaApi.em().createQuery("SELECT u from User u WHERE  u.accessToken = '" + authToken +"' ", User.class);

            user = query.getSingleResult();

        }
        catch(NoResultException nre){
            throw new IllegalArgumentException("Sign in to complete the action");
        }
        return user;
    }


}
