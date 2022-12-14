package com.ebn.calendar.repository;

import com.ebn.calendar.model.dao.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends GenericCRUDRepository<User, String> {

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public User readByUsername(String username) {
        User toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                User aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from User where username = :username", User.class)
                        .setParameter("username", username).getSingleResult();
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
