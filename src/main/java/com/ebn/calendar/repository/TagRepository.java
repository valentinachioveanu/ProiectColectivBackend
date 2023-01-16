package com.ebn.calendar.repository;

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
public class TagRepository extends GenericCRUDRepository<Tag, String> {
    @Autowired
    public TagRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Tag.class);
    }

    @Override
    public Tag delete(String id) {
        if (!deleteEventAssociationsToTag(id)) {
            return null;
        }
        logger.info("deleted all event associations to tag");
        return super.delete(id);
    }

    public List<Tag> readTagsForUser(User user) {
        List<Tag> toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Tag> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Tag where owner = :user", Tag.class)
                        .setParameter("user", user)
                        .list();
                transaction.commit();
                toReturn = aux;
                logger.trace("tags read successfully");
            } catch (RuntimeException e) {
                logger.error(e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }

    public List<Tag> readTagsForUser(Collection<String> tagsIds, User user) {
        List<Tag> toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<Tag> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from Tag where owner = :user and id in :tagsIds", Tag.class)
                        .setParameter("user", user)
                        .setParameter("tagsIds", tagsIds)
                        .list();
                transaction.commit();
                toReturn = aux;
                logger.trace("tags read with ids successfully");
            } catch (RuntimeException e) {
                logger.error(e);
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }

    private boolean deleteEventAssociationsToTag(String id) {
        boolean result = false;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                MutationQuery query = session.createNativeMutationQuery("delete from event_tag where tag_id = :id");
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
