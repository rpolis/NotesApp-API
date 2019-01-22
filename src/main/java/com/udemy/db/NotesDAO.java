package com.udemy.db;
import com.udemy.core.Notes;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class NotesDAO extends AbstractDAO<Notes>{

    public NotesDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Notes> findAllNotes()
    {
        return list(namedQuery("com.udemy.core.Notes.findAllNotes"));
    }



    public Optional<Notes> findNotesbyid(long id)
    {
        return Optional.ofNullable(
                uniqueResult(
                        namedQuery("com.udemy.core.Notes.findNotesbyid")
                        .setParameter("id",id)));

    }

    public List<Notes> findNotesInBoard(int id)
    {
        return
                list(
                        namedQuery("com.udemy.core.Notes.findNotesInBoard")
                                .setParameter("id",id));

    }

    public Notes save(Notes notes){
        notes.setCreatedDate(new Date());
        return persist(notes);
    }

}
