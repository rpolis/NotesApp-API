package com.udemy.db;

import com.udemy.core.Boards;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import com.udemy.core.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserBoardsDAO extends AbstractDAO<UserBoards>{
    public UserBoardsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public BoardsResponse findBoardsOfUser(Integer id, BoardsDAO bdao, NotesDAO ndao){
        BoardsResponse boardsResponse = new BoardsResponse();

        List<Integer> userBoardIds = list( namedQuery("com.udemy.core.UserBoards.findBoardsOfUser")
                .setParameter("id",id));

        if(userBoardIds.size() == 0){
            return new BoardsResponse(200, new ArrayList<>(), new ArrayList<>());
        }

        List<Boards> userBoards = bdao.findBoardsbyIds(userBoardIds);



        List<Boards> myBoards = new ArrayList<>();
        List<Boards> sharedBoards = new ArrayList<>();

        for (Boards b: userBoards) {
            b.setNotes(ndao.findNotesInBoard(b.getId()));
            if(b.getOwner() == id){
                myBoards.add(b);
            }else{
                sharedBoards.add(b);
            }
        }

        boardsResponse.setShared(sharedBoards);
        boardsResponse.setNotShared(myBoards);
        boardsResponse.setCode(200);

        return boardsResponse;


    }

    public UserBoards save(UserBoards userBoard){
        userBoard = persist(userBoard);
        return userBoard;
    }

    public List<User> boardUsers(int id, UserDAO udao){
        List<Integer> userBoardIds = list( namedQuery("com.udemy.core.UserBoards.findUsersOfBoard")
                .setParameter("id",id));

        if(userBoardIds.size() == 0){
            return new ArrayList<>();
        }


        return udao.findByIds(userBoardIds);
    }


}
