package com.ebn.calendar.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;

import java.sql.*;

public class PersistenceConfiguration {

    private static final Logger logger = LogManager.getLogger();

    private final String postgresUsername = "postgres";

    private final String postgresPassword = "postgres";

    private final boolean dropDatabase = true;

    @Bean
    SessionFactory sessionFactory() {
        if (dropDatabase) {
            dropDatabase();
        }
        if (!databaseExists()) {
            createDatabase();
        }
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            logger.info("session factory created");
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void dropDatabase() {
        try (Connection postgresConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", postgresUsername, postgresPassword);
             Statement createDBStatement = postgresConnection.createStatement()) {

            createDBStatement.execute("""
                    DROP DATABASE calendar
                    """);
            logger.info("database dropped");
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    private void createDatabase() {
        try (Connection postgresConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", postgresUsername, postgresPassword);
             Statement createDBStatement = postgresConnection.createStatement()) {

            createDBStatement.execute("""
                    CREATE DATABASE calendar
                    """);
            logger.info("database created");
        } catch (SQLException e) {
            logger.error(e);
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
            logger.info("database verified");
        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
