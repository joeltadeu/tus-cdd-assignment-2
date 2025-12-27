FROM gcr.io/distroless/java21-debian13

ADD target/doctor-service.jar doctor-service.jar

EXPOSE 9082

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/doctor-service.jar"]