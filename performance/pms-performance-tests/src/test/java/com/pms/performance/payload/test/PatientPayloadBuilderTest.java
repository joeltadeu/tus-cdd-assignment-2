package com.pms.performance.payload.test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.performance.payload.PatientPayloadBuilder;
import org.junit.jupiter.api.Test;

class PatientPayloadBuilderTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void shouldBuildValidPatientJson() throws Exception {
        String json = PatientPayloadBuilder.build();

        assertNotNull(json);
        assertFalse(json.isBlank());

        JsonNode root = OBJECT_MAPPER.readTree(json);

        assertTrue(root.hasNonNull("firstName"));
        assertTrue(root.hasNonNull("lastName"));
        assertTrue(root.hasNonNull("email"));
        assertTrue(root.hasNonNull("address"));
        assertTrue(root.hasNonNull("dateOfBirth"));
    }

    @Test
    void shouldContainNonEmptyValues() throws Exception {
        JsonNode root = OBJECT_MAPPER.readTree(PatientPayloadBuilder.build());

        assertFalse(root.get("firstName").asText().isBlank());
        assertFalse(root.get("lastName").asText().isBlank());
        assertFalse(root.get("address").asText().isBlank());
        assertFalse(root.get("dateOfBirth").asText().isBlank());
    }

    @Test
    void shouldGenerateLowercaseEmailWithExpectedFormat() throws Exception {
        JsonNode root = OBJECT_MAPPER.readTree(PatientPayloadBuilder.build());

        String email = root.get("email").asText();

        assertNotNull(email);
        assertTrue(email.endsWith("@example.com"));
        assertEquals(email.toLowerCase(), email);

        // basic structure check: firstname.lastname@example.com
        assertTrue(email.matches("^[a-z]+\\.[a-z]+@example\\.com$"));
    }

    @Test
    void shouldGenerateDifferentPayloadsOnMultipleCalls() {
        String json1 = PatientPayloadBuilder.build();
        String json2 = PatientPayloadBuilder.build();

        assertNotEquals(json1, json2);
    }
}
