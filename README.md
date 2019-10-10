The Spring Boot application demonstrates how to replace in the Camunda Engine the user and group handling. User, group and membership information will not be stored in the engine, but in separate tables, and the engine is configured to use this tables through the `JpaIdentityProviderPlugin`.
The same tables are used by Spring Security too.
The database is an in-memory H2 database.

The tables are:

* `user`
* `groups`
* `group_authorities`
* `group_members`

See the schema.sql file in the resources folder. 
Spring Security is built up on this same tables, by registering the `JpaUserDetailsService` class.

We create three users and three groups:

`alice` - member of the `admins` group.
`charlie` - member of the `admins` group.
`joe` - member of the `guests` group.

The passwords are `test`. See the `data.sql` file in the resources folder. 

The H2 Console runs at: [http://localhost:8080/h2-console](localhost:8080/h2-console). Admin username is `sa` with no password (default).

The camunda REST interface is running on the path `http://localhost:8080/rest/...` (default).  
Is secured with Spring Security, try eg. [localhost:8080/rest/engine](localhost:8080/rest/engine) !

The Camunda Web Applications are accessible here: [http://localhost:8080](http://localhost:8080)

Or:
[http://localhost:8080/app/welcome/default/#/login](http://localhost:8080/app/welcome/default/#/login)
[http://localhost:8080/app/admin/default/#/login](http://localhost:8080/app/admin/default/#/login)
[http://localhost:8080/app/tasklist/default/#/login](http://localhost:8080/app/tasklist/default/#/login)
[http://localhost:8080/app/cockpit/default/#/login](http://localhost:8080/app/cockpit/default/#/login).

The admin group name in Camunda is set to `admins`. (with the `AdministratorAuthorizationPlugin`).
So try to login with `alice` (password: `test`) first!
And and evtl. give permissons to `charlie` and `joe`. :)
(Or set `camunda.bpm.authorization.enabled: false` in the `application.yaml` for the Camunda Web Applications).

The application deploys a process named `SimpleProcess` too. It can be started from a html page
[http://localhost:8080/start-process.html](http://localhost:8080/start-process.html) over the 
REST interface, the page is secured too.

Remarks:
- The `JpaIdentityProviderPlugin` was built after the [LDAP plugin](https://github.com/camunda/camunda-bpm-platform/tree/master/engine-plugins/identity-ldap).
Only the LDAP access was replaced by JPA access.
- This should not be a common scenario for an embedded engine i think. Yo can restrict the access to the REST interface through Spring Security, and can use the Java interface in the Java program.
You can set the `assignee` property for tasks. The Camunda Web Application could be used only by some separate users, who can be stored in the camunda tables separate from the business users.
[When is authorization required](https://docs.camunda.org/manual/7.11/user-guide/process-engine/authorization-service/#when-is-authorization-required).
