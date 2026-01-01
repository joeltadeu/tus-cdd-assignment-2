package com.pms.performance.payload;

import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class AppointmentPayloadBuilder {

  private static final Faker faker = new Faker();
  private static final Random random = new Random();

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private static final List<AppointmentTemplate> APPOINTMENT_TEMPLATES =
      List.of(
          new AppointmentTemplate(
              "Knee Pain Consultation",
              "Patient is experiencing persistent knee pain after running. Initial consultation to diagnose the issue."),
          new AppointmentTemplate(
              "Back Pain Evaluation",
              "Patient reports chronic lower back pain, especially after long periods of sitting."),
          new AppointmentTemplate(
              "Sports Injury Assessment",
              "Patient suffered a sports-related injury and requires a medical evaluation."),
          new AppointmentTemplate(
              "Post-Surgery Follow-up",
              "Follow-up consultation to assess recovery progress after recent surgery."),
          new AppointmentTemplate(
              "General Orthopedic Consultation",
              "Patient requests a general orthopedic evaluation due to ongoing joint discomfort."));

  public static String build(long patientId, long doctorId) {
    AppointmentTemplate template = randomTemplate();
    String startTime = randomFutureDateTime();

    return String.format(
        "{ \"title\": \"%s\", "
            + "\"type\": \"CONSULTATION\", "
            + "\"startTime\": \"%s\", "
            + "\"patientId\": %d, "
            + "\"doctorId\": %d, "
            + "\"description\": \"%s\" }",
        template.title(), startTime, patientId, doctorId, template.description());
  }

  private static AppointmentTemplate randomTemplate() {
    return APPOINTMENT_TEMPLATES.get(random.nextInt(APPOINTMENT_TEMPLATES.size()));
  }

  private static String randomFutureDateTime() {
    // Between now and 90 days in the future
    LocalDateTime futureDate =
        LocalDateTime.now()
            .plusDays(faker.number().numberBetween(0, 90))
            .withHour(faker.number().numberBetween(8, 18))
            .withMinute(List.of(0, 15, 30, 45).get(random.nextInt(4)))
            .withSecond(0)
            .withNano(0);

    return futureDate.format(DATE_TIME_FORMATTER);
  }

  private record AppointmentTemplate(String title, String description) {}
}
