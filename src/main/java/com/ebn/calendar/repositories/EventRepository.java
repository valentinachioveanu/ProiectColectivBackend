package com.ebn.calendar.repositories;

import com.ebn.calendar.model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRepository extends GenericCRUDRepository<String, Event> {
    @Autowired
    public EventRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Event.class);
    }

    @Deprecated
    //returns null if failed to get data - this will be deleted in further versions
    public List<Event> getAll() {
        List<Event> toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Event> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Event", Event.class)
                        .list();
                transaction.commit();
                toReturn = aux;
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }
}
