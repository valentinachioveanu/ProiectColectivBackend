package com.ebn.calendar.repository;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dao.User;
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

    public List<Event> readUserEvents(User user) {
        List<Event> toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Event> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Event where user = :user", Event.class)
                        .setParameter("user", user)
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
