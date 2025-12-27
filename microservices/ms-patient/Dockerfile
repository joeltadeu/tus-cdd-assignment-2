FROM gcr.io/distroless/java21-debian13

ARG PROFILE

ADD target/patient-service.jar patient-service.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/patient-service.jar"]
