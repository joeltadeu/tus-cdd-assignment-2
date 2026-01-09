package com.pms.models.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {
    @Schema(description = "Patient Id",
            name = "patientId",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "12")
    @NotNull(message = "Patient Id cannot be null")
    private Long patientId;

    @Schema(description = "Doctor Id",
            name = "doctorId",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "14")
    @NotNull(message = "Doctor Id cannot be null")
    private Long doctorId;

    @Schema(description = "Start time",
            name = "startTime",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2025-09-10 10:35:00")
    @NotNull(message = "Appointment start time cannot be null")
    private LocalDateTime startTime;

    @Schema(description = "Type",
            name = "type",
            example = "Appointment Type")
    private AppointmentType type;

    @Schema(description = "Title",
            name = "title",
            example = "Knee Pain Consultation")
    private String title;

    @Schema(description = "Description",
            name = "description",
            example = "Patient is experiencing persistent knee pain after running. Initial consultation to diagnose the issue.")
    private String description;
}
