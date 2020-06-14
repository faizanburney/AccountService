FROM openjdk:13-jdk-alpine3.9
ARG userid
ARG username
RUN adduser -S -u $userid $username
USER ${username}
WORKDIR /home/${username}

VOLUME /tmp
ADD build/libs/taskAccounts.jar /home/${username}/app.jar
COPY dockerEntryPoint.sh /home/${username}/
ENTRYPOINT exec ~/dockerEntryPoint.sh
