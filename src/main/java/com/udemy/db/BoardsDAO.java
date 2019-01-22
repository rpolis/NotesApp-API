package com.udemy.db;

import com.udemy.core.UserBoards;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import com.udemy.core.Boards;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BoardsDAO extends AbstractDAO<Boards>{

    public BoardsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Boards> findAllBoards()
    {
        return list(namedQuery("com.udemy.core.Boards.findAllBoards"));
    }


    public Optional<Boards> findAllBoardsForUser(int id){
        return null;
    }

    public List<Boards> findBoardsbyIds(List<Integer> ids)
    {
        return list( namedQuery("com.udemy.core.Boards.findBoardsbyIds")
                                .setParameter("id",ids));

    }

    public Boards save(Boards board, UserBoardsDAO ubdao) {
        Date date = new Date();
        board.setCreatedDate(date);
        Boards newBoard = persist(board);
        UserBoards userBoard = new UserBoards();

        userBoard.setBoardId(newBoard.getId());
        userBoard.setUserId(newBoard.getOwner());
        userBoard.setCreatedDate(date);
        ubdao.save(userBoard);
        return newBoard;
    }
}


