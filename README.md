How to Account Service:

simple run 
./gradlew build

that will run the unit tests and build the jar file in the following location
./build/libs/taskAccounts
just run the jar file using command e.g:
java -jar taskAccounts.jar

Swagger based documentation can be accessed from following location:
http://localhost:8090/swagger-ui.html

Logs are also exposed via endpoint and can be accessed from following location.
http://localhost:8090/actuator/loggers/

Docker file is also included in the package.
To build the docker image just run:
Docker build 
in the the project directory where dockerfile is located.

Account Service uses reactive Webflux of spring boot to handle user requests in a reactive manner. This frameworks is designed to handle request asynchronously that proves to be very efficient in high load environment. To make current service completely non blocking we can also use mongo db reactive api but for simpliciy purpose I have used JPA and H2 db. 

I have used a Spring boot default cache here that regenerates after every day assuming the exchange rate refreshes every day.
