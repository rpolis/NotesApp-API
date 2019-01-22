package com.udemy.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_boards_map")
@NamedQueries({
        @NamedQuery(name ="com.udemy.core.UserBoards.findBoardsOfUser",query = "select n.boardId from UserBoards n where n.userId = :id"),
        @NamedQuery(name ="com.udemy.core.UserBoards.findUsersOfBoard",query = "select n.userId from UserBoards n where n.boardId = :id")
})

public class UserBoards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="user_id")
    int userId;
    @Column(name="board_id")
    int boardId;
    @Column(name="created_date")
    Date createdDate;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserBoards() {
    }

    public UserBoards(int userId, int boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

}
