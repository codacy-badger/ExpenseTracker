# Expense Tracker
Simple Spring Boot + Spring Data expense tracker web service. Made to learn a little bit of Spring Data CrudRepository configuration and usages.
It's a very simple project.

# Unit test
The file src\main\resources\application.properties can be set to use Spring Boot profiles "test" and "production".
Test profile is used for unit and integration tests, it will enable a configuration for a HSQLDB in-memory db.
Production profile will enable another configuration, this time for a MySQL db.

