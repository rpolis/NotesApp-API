package com.udemy.resources;

import com.codahale.metrics.annotation.Timed;
import com.udemy.core.*;
import com.udemy.db.*;

import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.SessionFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.*;
import java.util.stream.Collectors;

@Path("/app")
public class HelloResource
{
    private UserDAO udao;
    private NotesDAO ndao;
    private BoardsDAO bdao;
    private UserBoardsDAO ubdao;
    private Service service = new Service();

    public HelloResource(){

    }

    public HelloResource(UserDAO udao , NotesDAO ndao, BoardsDAO bdao, UserBoardsDAO ubdao)
    {
        this.udao = udao;
        this.ndao = ndao;
        this.bdao = bdao;
        this.ubdao = ubdao;
    }

    @GET
    @Timed
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public List<Notes> getNotes()
    {
        return ndao.findAllNotes();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers( )
    {

        return udao.findAll();
    }


    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Notes> getNotesbyId(@PathParam("id") long id)
    {
        return ndao.findNotesbyid(id);
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/userboards/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BoardsResponse getUserBoards(@PathParam("id") Integer id)
    {
//        List<Integer> id = Arrays.stream(ids.split(","))
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
        return ubdao.findBoardsOfUser(id, bdao, ndao);
    }

    @POST
    @UnitOfWork
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message signup(@Valid @NotNull User user) {
        return udao.save(user);
    }

    @POST
    @UnitOfWork
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LoginResponse signin(@Valid @NotNull User user) {
        return udao.validate(user);
    }

    @POST
    @UnitOfWork
    @Path("/saveboard")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boards saveBoard(@Valid @NotNull Boards board) {
        return bdao.save(board, ubdao);
    }

    @POST
    @UnitOfWork
    @Path("/savenotes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Notes saveNotes(@Valid @NotNull Notes notes) {
        return ndao.save(notes);
    }

    @POST
    @UnitOfWork
    @Path("/editnotes")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Notes editNotes(@Valid @NotNull Notes notes) {
        return ndao.save(notes);
    }

    @POST
    @UnitOfWork
    @Path("/shareboard")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Message shareboard(@Valid @NotNull UserBoards userBoards) {
        userBoards.setCreatedDate(new Date());
        List<User> users = udao.findByUsername(userBoards.getEmail());
        if(users.size() == 0){
            return new Message(404, "User not found");
        }
        userBoards.setUserId(users.get(0).getId());
        userBoards = ubdao.save(userBoards);
        if(userBoards.getId() > 1){
            return new Message(200, "shared");
        }else{
            return new Message(500, "Failed");
        }
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/boardUsers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getBoardUsers(@PathParam("id") Integer id)
    {
        return ubdao.boardUsers(id, udao);
    }

}
