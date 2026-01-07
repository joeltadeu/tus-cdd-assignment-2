package com.pms.performance.payload.test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.performance.payload.AppointmentPayloadBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AppointmentPayloadBuilderTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  @Test
  void shouldBuildValidAppointmentJson() throws Exception {
    String json = AppointmentPayloadBuilder.build(1L, 2L);

    assertNotNull(json);
    assertFalse(json.isBlank());

    JsonNode root = OBJECT_MAPPER.readTree(json);

    assertTrue(root.hasNonNull("title"));
    assertTrue(root.hasNonNull("type"));
    assertTrue(root.hasNonNull("startTime"));
    assertTrue(root.hasNonNull("patientId"));
    assertTrue(root.hasNonNull("doctorId"));
    assertTrue(root.hasNonNull("description"));
  }

  @Test
  void shouldContainCorrectPatientAndDoctorIds() throws Exception {
    long patientId = 100L;
    long doctorId = 200L;

    JsonNode root = OBJECT_MAPPER.readTree(AppointmentPayloadBuilder.build(patientId, doctorId));

    assertEquals(patientId, root.get("patientId").asLong());
    assertEquals(doctorId, root.get("doctorId").asLong());
  }

  @Test
  void shouldHaveValidIsoLocalDateTimeStartTime() throws Exception {
    JsonNode root = OBJECT_MAPPER.readTree(AppointmentPayloadBuilder.build(1L, 2L));
    String startTime = root.get("startTime").asText();

    assertNotNull(startTime);

    assertDoesNotThrow(
        () -> {
          LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
        });
  }

  @Test
  void shouldUseTemplateTitlesAndDescriptions() throws Exception {
    Set<String> validTitles =
        Set.of(
            "Knee Pain Consultation",
            "Back Pain Evaluation",
            "Sports Injury Assessment",
            "Post-Surgery Follow-up",
            "General Orthopedic Consultation");

    Set<String> validDescriptions =
        Set.of(
            "Patient is experiencing persistent knee pain after running. Initial consultation to diagnose the issue.",
            "Patient reports chronic lower back pain, especially after long periods of sitting.",
            "Patient suffered a sports-related injury and requires a medical evaluation.",
            "Follow-up consultation to assess recovery progress after recent surgery.",
            "Patient requests a general orthopedic evaluation due to ongoing joint discomfort.");

    JsonNode root = OBJECT_MAPPER.readTree(AppointmentPayloadBuilder.build(1L, 2L));

    String title = root.get("title").asText();
    String description = root.get("description").asText();

    assertTrue(validTitles.contains(title), "Title must be from templates");
    assertTrue(validDescriptions.contains(description), "Description must be from templates");
  }

  @Test
  void shouldGenerateDifferentPayloadsOnMultipleCalls() {
    String json1 = AppointmentPayloadBuilder.build(1L, 2L);
    String json2 = AppointmentPayloadBuilder.build(1L, 2L);

    assertNotEquals(
        json1, json2, "Two builds should not produce identical JSON due to randomization");
  }
}
