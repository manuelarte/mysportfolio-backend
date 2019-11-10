FROM gradle:jdk11 as builder
MAINTAINER Doncel Martos, Manuel <manueldoncelmartos@gmail.com>

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build # -x integrationTest

FROM openjdk:11.0-jre-stretch
EXPOSE 8080
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/mysportfolio-*.jar /app/mysportfolio.jar
ENTRYPOINT ["java","-jar","/app/mysportfolio.jar"]


#FROM openjdk:11
#VOLUME /tmp
#ADD build/libs/mysportfolio-0.1.0-SNAPSHOT.jar mysportfolio-0.1.0-SNAPSHOT.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","mysportfolio-0.1.0-SNAPSHOT.jar"]