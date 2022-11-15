# ProiectColectivBackend

# Setup

1. Check if you have Postgresql:
   - If you don't have Postgresql:
     - download link: https://www.postgresql.org/download/
     - in installation, use the following info:
       - username: postgres
       - password: postgres
       - port: 5432 (this is the port by default)
   - If you have Postgresql:
     - in hibernate.cfg.xml change update the following info with the ones you chose during initial installation:
       - hibernate.connection.password = YOUR_PASS
       - hibernate.connection.url = "jdbc:postgresql://localhost:YOUR_PORT/calendar"
     - in CalendarConfiguration class:
       - postgresPassword = YOUR_PASS
2. Run app (Maven>calendar>Lifecycle>clean + install)

# Documentation
- Rest API calls examples are in documentation/8x9_Calendar.postman_collection.json - just import it in Postman