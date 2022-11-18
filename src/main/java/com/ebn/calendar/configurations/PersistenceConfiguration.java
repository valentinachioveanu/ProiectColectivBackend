package com.ebn.calendar.configurations;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;

import java.sql.*;

public class PersistenceConfiguration {

    private final String postgresUsername = "postgres";

    private final String postgresPassword = "postgres";

    @Bean
    SessionFactory sessionFactory() {
        if (!databaseExists()) {
            createDatabase();
        }
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createDatabase() {
        try (Connection postgresConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", postgresUsername, postgresPassword);
             Statement createDBStatement = postgresConnection.createStatement()) {

            createDBStatement.execute("""
                    CREATE DATABASE calendar
                    """);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private boolean databaseExists() {
        boolean result;
        try (Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", postgresUsername, postgresPassword);
             Statement findDBStatement = c.createStatement()) {

            ResultSet resultSet = findDBStatement.executeQuery("""
                    SELECT datname FROM pg_catalog.pg_database WHERE datname='calendar'
                    """);
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }
}
