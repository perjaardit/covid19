# Covid Correlation Application

Simple application with [Spring Boot 2](https://spring.io/projects/spring-boot) and [Apache Maven](https://maven.apache.org/),
that calculates the correlation coefficient between the percentage of people that died and got vaccinated
of COVID-19 given a continent or all available countries using the API as described on: https://github.com/M-Media-Group/Covid-19-API.

Operations covered,

| Operation       | Description    | [RequestType]:Request URL |
| :------------- | :----------: | :----------- |
| Get Coefficient For Continent | Get correlation coefficient for a given continent  |[GET]:http://localhost:8080/api/correlation/details/{continent}    |
| Get Coefficient For All Countries   | Get correlation coefficient for all available countries |[GET]:http://localhost:8080/api/correlation/details |


## Prerequisites

What is needed to start developing and to run the application:

- JDK 11+, for instance [OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)
- Apache Maven 3.3+
- Lombok IDE plugin

### Installing

After checking out the project, you may need to install the dependencies

    `mvn clean install && mvn compile`

After that we may start the application via preferred IDE or using cmd:

    `mvn spring-boot:run`

## Running the app and tests

You may test the rest functionality via [swagger ui](http://localhost:8080/api/swagger-ui/index.html)
