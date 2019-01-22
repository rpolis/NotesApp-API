package com.udemy.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "boards")
@NamedQueries({
        @NamedQuery(name ="com.udemy.core.Boards.findAllBoards", query = "select n from Boards n"),
        @NamedQuery(name ="com.udemy.core.Boards.findBoardsbyOwner",query = "select n from Boards n where n.owner = :id"),
        @NamedQuery(name ="com.udemy.core.Boards.findBoardsbyIds",query = "select n from Boards n where n.id in (:id)")
})

public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(name="created_date")
    private Date createdDate;
    private int owner;

    @Transient
    @JsonSerialize
    @JsonDeserialize
    private List<Notes> notes;

    public Boards(){

    }

    public Boards(String title, Date createdDate, int owner){
        this.createdDate = createdDate;
        this.title = title;
        this.owner = owner;

    }

    public Boards(String title, Date createdDate, int owner, List<Notes> notes) {
        this.title = title;
        this.createdDate = createdDate;
        this.owner = owner;
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }
}

