package com.ebn.calendar.repository;

import com.ebn.calendar.model.dao.Identifiable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GenericCRUDRepository<E extends Identifiable<ID>, ID> {

    protected final SessionFactory sessionFactory;

    private final Class<E> type;

    public GenericCRUDRepository(SessionFactory sessionFactory, Class<E> type) {
        this.sessionFactory = sessionFactory;
        this.type = type;
    }

    //returns the entity if succeeded or null if failed
    //it will also set the id of the entity given as parameter
    public E create(E entity) {
        E toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(entity);
                transaction.commit();
                toReturn = entity;
            } catch (RuntimeException e) {
                e.printStackTrace();
                if (transaction != null)
                    transaction.rollback();
            }
        }
        return toReturn;
    }

    //returns the entity if it exists or null if failed
    public E read(ID id) {
        E toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                E aux;
                transaction = session.beginTransaction();
                aux = session.get(type, id);
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

    //returns the old entity if succeeded or null if failed
    public E update(E entity) {
        E toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                E aux;
                transaction = session.beginTransaction();
                aux = session.get(type, entity.getIdentifier());
                session.detach(aux);
                session.merge(entity);
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

    //returns the deleted entity or null if failed
    public E delete(ID id) {
        E toReturn = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                E aux;
                transaction = session.beginTransaction();
                aux = session.get(type, id);
                session.remove(aux);
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
