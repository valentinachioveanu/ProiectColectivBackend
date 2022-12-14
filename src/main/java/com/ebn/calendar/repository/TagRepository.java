package com.ebn.calendar.repository;

import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository extends GenericCRUDRepository<String, Tag> {
    @Autowired
    public TagRepository(SessionFactory sessionFactory) {
        super(sessionFactory, Tag.class);
    }

    public List<Tag> readUserTags(User user) {
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
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }
}
