#Start with a base image containing Java runtime
FROM eclipse-temurin:21-jdk-alpine

LABEL "org.opencontainers.image.authors"="minibank.com"

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]