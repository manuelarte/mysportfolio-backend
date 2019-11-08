FROM openjdk:11
VOLUME /tmp
ADD build/libs/mysportfolio-0.1.0-SNAPSHOT.jar mysportfolio-0.1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","mysportfolio-0.1.0-SNAPSHOT.jar"]