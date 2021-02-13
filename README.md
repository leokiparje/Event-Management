# Event-Management

## Requirements

The project has the following dependencies:

- JRE/JDK 8+
- Maven 3.5+
- SQL database that can work with Hibernate ORM, e.g. PostgreSQL

## Build and run

Clone repository to a local folder and use **mvn install** inside the root folder of the repository to build all the core system JARs. With this command integration test is also runned. Now edit the config files (the database connection), in src/main/resources/application.properties, database platform, username, password and url. To run the application use **mvn spring-boot:run**. Application should be running on port 8080 (http://localhost:8080/). Now you can test the endpoints.

## Implementation details

There are two endpoints for creating users and events (I had to use participant instead of user because user is a key word in postgreSQL). After creating users and events, they can be linked by **addParticipantToEvent** method. A user can create a meeting if he's currently participating in an event. Meeting start and end time must be before the event ends. Once the user creates a meeting, its status is PREPARING and invitations are send to other users that are present on same event. User can accept or decline an ivitation via **respondToInvite** method and after all invitees respond to the invitation a meeting is scheduled. There are also two endpoints : **getAllInvitations and getAllScheduledMeetings** so that every user can see all invitations and scheduled meetings.
