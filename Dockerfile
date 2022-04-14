# the firrst stage of our build will use a maven 3.6.3 parent image
FROM maven:3.6-jdk-11-slim AS MAVEN_BUILD
# copy the source tree and the pom.xml to our container
COPY ./ ./
# package our application code
RUN mvn clean package

# the second stage of our buld will use open jdk 11
FROM openjdk:11.0.7-jdk-slim

# copy the source tree and the pom.xml to our container
COPY --from=MAVEN_BUILD /target/library-0.0.1-SNAPSHOT.jar /library.jar
# set the startup command to execute the jar
CMD ["java", "-jar", "/library.jar"]

# docker build -t docker-multi-stage-build-library:1.0-SNAPSHOT .
# docker run -d -p 8085:8085 docker-multi-stage-build-library:1.0-SNAPSHOT