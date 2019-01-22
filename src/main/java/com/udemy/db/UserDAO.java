package com.udemy.db;

import com.udemy.core.LoginResponse;
import com.udemy.core.Message;
import com.udemy.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public class UserDAO extends AbstractDAO<User>
{
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<User> findAll()
    {
       return list(namedQuery("com.udemy.core.User.findAll"));
    }

    public Optional<User> findByUsernamePassword(String username, String password)
    {
        return Optional.ofNullable(
                uniqueResult(
                        namedQuery("com.udemy.core.User.findByUsernamePassword")
                        .setParameter("username",username)
                        .setParameter("password",password)
                )

        );
    }

    public List<User> findByUsername(String username)
    {
        return
                list(namedQuery("com.udemy.core.User.findByUsername")
                                .setParameter("username",username)
                 );
    }

    public List<User> findByIds(List<Integer> id)
    {
        return
                list(namedQuery("com.udemy.core.User.findByIds")
                        .setParameter("id",id)
                );
    }


    public Message save(User user){

        try {
            user.setCreatedDate(new Date());
            persist(user);
        }catch(ConstraintViolationException e){
            e.printStackTrace();
            throw e;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }


        return new Message(200, "Signup successful");
    }

    public LoginResponse validate(User user){
        User u = uniqueResult(
                namedQuery("com.udemy.core.User.findByUsernamePassword")
                        .setParameter("username",user.getUsername())
                        .setParameter("password",user.getPassword())
        );
        if(u != null){
            User curUser = new User();
            curUser.setId(u.getId());
            curUser.setUsername(u.getUsername());
            return new LoginResponse(200, curUser);
        }

        return new LoginResponse(401, new User());

    }
}
