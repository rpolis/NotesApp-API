package com.udemy.core;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "notes")
@NamedQueries({
        @NamedQuery(name ="com.udemy.core.Notes.findAllNotes", query = "select n from Notes n"),
        @NamedQuery(name ="com.udemy.core.Notes.findNotesbyid",query = "select n from Notes n where n.id = :id"),
        @NamedQuery(name ="com.udemy.core.Notes.findNotesInBoard",query = "select n from Notes n where n.boardId = :id")

})

public class Notes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String owner;
    private String title;
    private String content;
    @Column(name="board_id")
    private int boardId;
    @Column(name="created_date")
    private Date createdDate;

    public Notes (){

    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public Notes(String owner, String title, String content, int boardId) {
        this.owner = owner;
        this.title = title;
        this.content = content;
        this.boardId = boardId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
