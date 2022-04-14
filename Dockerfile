#
FROM yuqingyu/openjdk.11.0.11-jre-slim.faketime

# copy the packaged jar into our docker image
COPY target/library-0.0.1-SNAPSHOT.jar /library.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "library.jar"]

# mvn clean package
# docker build -t docker-package-only-build-library:0.0.1-SNAPSHOT .
# docker run -d -p 8085:8085 docker-package-only-build-library:0.0.1-SNAPSHOT