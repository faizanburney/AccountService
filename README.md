**How to Account Service:**

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



Account Service uses reactive Webflux of spring boot to handle user requests in a reactive manner. This frameworks is designed to handle request asynchronously that proves to be very efficient in high load environment. To make current service completely non blocking we can also use mongo db reactive api but for simpliciy purpose I have used JPA and H2 db. 

I have used a Spring boot default cache here that regenerates after every day assuming the exchange rate refreshes every day.

**Building Via Docker:**

Docker file is also included in the package.I have added all the scripts to manage this service and other services in production using
docker compose.

You need to have Docker,Gradle and docker-compose installed in your host machine before running them.

To build/run/stop docker, follow these steps:

 build:
 
  ./buildMicroservice.sh all
  
 run :
 
  ./runMicroservice.sh all
  
 stop:
 
 ./stopMicroservice.sh all





