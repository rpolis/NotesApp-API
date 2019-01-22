package com.udemy.core;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name ="com.udemy.core.User.findAll",
        query = "select u from User u"),
        @NamedQuery(name = "com.udemy.core.User.findByUsernamePassword",
        query = "select u from User u " +
                "where u.username = :username "
                + "and u.password = :password"),
        @NamedQuery(name = "com.udemy.core.User.findByUsername",
        query = "select u from User u " +
                "where u.username = :username "),
        @NamedQuery(name = "com.udemy.core.User.findByIds",
                query = "select u from User u " +
                        "where u.id in (:id) ")
})
public class User {

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;
    private String password;
    @Column(name = "created_date")
    private Date createdDate;


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public User()
    {
    }
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }





}