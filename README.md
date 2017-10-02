# mf-test
Simple credit card tool for test purposes.

## Technology Stack
- **Front-end**: React, Semantic UI, JavaScript.
- **Back-end**: Spring Boot, Spring, Java 8.
- **Testing**: JUnit, jMock.
- **Database**: H2 in memory. A good choice for test purposes as no complex configuration is necessary.
- **Security**: JSON Web Tokens (JWT). Tokens are exchanged between server and client. The token authentication is performed on each RESTFull call.

## Design
This project uses the concept of **Clean Architecture** ([more info here](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)). The Inversion of Dependency principle is used to decouple the core (where the business rules live) from other layers of the system (presentation, database etc).

This approach produces a system that is:
- Independent of frameworks. The core is writen in pure Java.
- Testable. The core is tested with simple JUnit tests.
- Independent of UI. You can use many disparate UI technologies. It's not going to affect the core.
- Independent of databases. The core doesn't know about the persistence implementation.
- Independent of any external agent. The business rules don’t know anything about the outside world.

### Core
The basic two classes, where all the business logic is implemented, are:
```
com.mftest.core.business.LoginBusiness
com.mftest.core.business.CardBusiness
```

The classes implementing the JUnit tests are *LoginBusinessTest* and *CardBusinessTest*.

### RESTFull services
There are service classes implementing the RESTFull services. They are just a layer to expose the core business rules.

The service classes are:
```
com.mftest.services.LoginService
com.mftest.services.CardService
```
The core classes *LoginBusiness* and *CardBusiness* are injected in the service classes by the Spring container.

Each method in *CardService* perform a token authentication using JWT.

### Persistence
Persistence is performed by the classes:
```
com.mftest.persistence.UserDAO
com.mftest.persistence.CardDAO
```

The Spring container injects these two classes into the core, as a concrete persistence implementation.

They implement the core interfaces *UserPersistenceInterface* and *CardPersistenceInterface*.

## Test Instructions

In order to test the system simply download the repository, navigate to the */download* directory and execute:
```
$ java -jar mftest-application-1.0.jar
```

Open a web browser and navigate to:
```
http://localhost:8080
```

Use this user and password to test as administrator:
```
User: admin  Password: 1234
```
