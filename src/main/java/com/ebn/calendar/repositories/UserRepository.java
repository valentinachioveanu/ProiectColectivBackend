package com.ebn.calendar.repositories;

import com.ebn.calendar.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends GenericCRUDRepository<String, User> {

    @Autowired
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public Optional<User> getByUsername(String username) {
        User toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<User> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from User where username =: usrn", User.class).setParameter("usrn", username).list();
                transaction.commit();
                toReturn = aux.get(0);
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return Optional.ofNullable(toReturn);
    }

    public Boolean existsByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                List<User> aux;
                transaction = session.beginTransaction();
                aux = session.createQuery("from User where username =: usrn", User.class).setParameter("usrn", username).list();
                transaction.commit();
                if (aux.isEmpty())
                    return false;
                else return true;
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return false;
    }
}
