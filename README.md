# SUMMER
Simple Spring Boot + Hibernate expense tracker web service. Made to learn a little bit of Hibernate configuration and usages. Very simple project, I'll eventually consider to improve it and add a React client, like above, to learn it.


# Unit test
The file src\main\resources\application.properties can be set to use Spring Boot profiles "test" and "production".
Test profile is used for unit and integration tests, it will enable an Hibernate configuration for a HSQLDB in memory-db.
Production profile will enable another Hibernate configuration, this time for a MySQL db.
