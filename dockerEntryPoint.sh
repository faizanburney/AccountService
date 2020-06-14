#!/bin/sh
echo "Running the container using following properties"
echo "Spring Profile==>$SPRING_PROFILES_ACTIVE"
echo "Java Max Memory==>$JAVA_MAX_MEMORY"
java  -Xmx$JAVA_MAX_MEMORY -Djava.security.egd=file:/dev/./urandom  -jar app.jar