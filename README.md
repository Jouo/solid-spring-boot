![logo](https://jouo-public-files.s3.amazonaws.com/solid-spring-logo.png)

![badge-linux](https://img.shields.io/badge/-Spring-brightgreen) ![badge-language](https://img.shields.io/badge/-Hibernate-blue) ![badge-api](https://img.shields.io/badge/-Java-orange) ![badge-type](https://img.shields.io/badge/-Security-blueviolet) ![badge-version](https://img.shields.io/badge/-Logging-yellow)

This project can be used as a reference on how to work with Spring Boot, it implements all sorts of things while also following the SOLID principles, hopefully it is as clean, extendable and maintainable as I expected it to be.

There are also some mock and integration tests with JUnit and Mockito, so you can easily check that everything works!

##### Main directory structure
- **aop** - logging logic 
- **orm** - contains the Services, DAO, Entities, and APIs
- **rest** - controllers, error handlers, http responses
- **security** - spring security configuration, JWT and IP banning
- **utility** - logic used around the application
- **validator** - hibernate validators for APIs

##### How to setup this project
1. Clone this repository with `git clone https://jouo/solid-spring-boot`
2. Run the SQL script found in the `files` directory to setup the database
3. Open the `application.properties` found in `src/main/resources/`
4. Edit the values `url`, `username`, `password` under the database section
5. Compile it with `mvn clean install` (to skip tests add `-DskipTests`)
6. The jar is found under `target/`, run it with `java -jar services-1.0.jar`

### Additional Information
In the following sections I will briefly explain how this project works, just a heads up: names shown like `this` refers to an interface, therefore you can make your own implementations of it and anything in **bold** refers to a class.

####  AOP
The **AOPSettings** contain the directory paths of the classes that you wish to log and pointcuts that by default will trigger on any method, **Pointcut** will store the execution paths in public variables, while **AOP** implements the logging logic by making use of `LogUtils`.

Now for the configuration itself you have to change the logback settings found in the resources:

- To enable saving the logs to files simply uncomment the setting from resources/logback.xml
- The file path and other settings can be found in resources/logback.properties

#### ORM
An API class is used as a bridge between the user input and the Entity class, now that we have that out of the way:

- Each Entity has an API
- Each API has an Utility
- Each DAO extend a base DAO
- Most Services extend a base Service
- And the Services that do extend, require all of the afore mentioned

It may sound tricky at first but it's actually super straight forward once you look at the code, it's really flexible and keeps everything as DRY as possible.

#### REST
Aside from the REST controllers (endpoints of your application) you will find some utilities, the **HttpRequestHandler** takes care of any `HttpException` thrown by the application (among other things).

There are two factories, one is used to generate `BindingExceptionResponse` which are returned to the user upon triggering an invalid input, the other factory will either return the requested resource or an `HttpExceptionReponse` that contains information about the http status returned along a message.

#### Security
Attempting to authenticate to request the JWT needed to interact with the application will either return the valid request or trigger the `FailureListener` which will keep track of failed attempts and IP ban you once necessary.

The **SecurityConfiguration** contains the configuration for Spring Security, things like role based permissions and user authentication through a JWT filter.

#### Utility
This one is divided in two directories: general and orm, the former stores utility classes that are used all around the application, while the latter is used specifically for the orm, you can read once again the ORM section.

Generally speaking, these utility classes contain logic used in the application, each and every of them implement an interface, so don't be scared to make your own classes!

#### Validator
Nothing but custom hibernate validators for the ORM APIs.

#### The Setting class
You may have noticed there's a **Setting** class outside of the afore mentioned directories, think of it as a bridge between the custom user values placed in application.properties and the classes themselves.

This class will load the values and store each of them in static variables, from there, other classes can simply access those values through this class, without having to inject the value themselves, everything is in one place.

