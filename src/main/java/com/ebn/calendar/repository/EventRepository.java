package com.ebn.calendar.repository;

import com.ebn.calendar.model.dao.Event;
import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Collection;
import java.util.List;

@Repository
public class EventRepository extends GenericCRUDRepository<Event, String> {
    @Autowired
    public EventRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Event.class);
    }

    @Override
    public Event delete(String id) {
        Event toBeDeleted = read(id);
        if (!deleteTagAssociationsToEvent(id)) {
            return null;
        }
        logger.info("deleted all tag associations to event");
        if (super.delete(id) == null) {
            return null;
        }
        return toBeDeleted;
    }

    public List<Event> readEventsForUser(User user) {
        List<Event> toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Event> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Event where owner = :user", Event.class)
                        .setParameter("user", user)
                        .list();
                transaction.commit();
                toReturn = aux;
                logger.trace("read events {}", toReturn);
            } catch (RuntimeException e) {
                logger.error(e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }

    public List<Event> readEventsContainingAllTagsForUser(Collection<Tag> tags, User user) {
        List<Event> result = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Event> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Event where owner = :user", Event.class)
                        .setParameter("user", user)
                        .list();
                transaction.commit();

                aux = aux.stream()
                        .filter(event -> event.getTags().containsAll(tags))
                        .toList();
                result = aux;
                logger.trace("read events with specific tags{}", result);
            } catch (RuntimeException e) {
                logger.error(e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return result;
    }

    private boolean deleteTagAssociationsToEvent(String id) {
        boolean result = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                MutationQuery query = session.createNativeMutationQuery("delete from event_tag where event_id = :id");
                query.setParameter("id", id);
                query.executeUpdate();
                transaction.commit();
                result = true;
            } catch (RuntimeException e) {
                logger.error(e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return result;
    }

}
