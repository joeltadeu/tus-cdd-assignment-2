FROM gcr.io/distroless/java21-debian13

ADD target/appointment-service.jar appointment-service.jar

EXPOSE 9083

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/appointment-service.jar"]