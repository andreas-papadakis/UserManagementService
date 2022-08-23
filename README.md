# UserManagementService
Java application to represent a User Management Service API. Runs under Java 18 and MySQL on Docker.

In order to run successfully, database name must be myDB and contain a single table called users with those columns:
id (type int, primary key, not null, auto increment)
first_name (type varchar(100), not null)
last_name (type varchar(100), not null)
e_mail (type varchar(100), not null)

Under http://localhost:8080/swagger-ui/index.html#/ there is swagger documentation.
