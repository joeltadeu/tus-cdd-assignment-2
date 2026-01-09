package com.pms.models.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    @Schema(description = "Appointment id",
            name = "id",
            example = "18")
    private Long id;

    @Schema(description = "Patient info",
            name = "patient")
    private PatientAppointment patient;

    @Schema(description = "Doctor info",
            name = "doctor")
    private DoctorAppointment doctor;

    @Schema(description = "Start time",
            name = "startTime",
            example = "2025-09-10 10:35:00")
    private LocalDateTime startTime;

    @Schema(description = "End time",
            name = "endTime",
            example = "2025-09-10 11:35:00")
    private LocalDateTime endTime;

    @Schema(description = "Duration",
            name = "duration",
            example = "60")
    private Integer duration;

    @Schema(description = "Appointment description",
            name = "description",
            example = "Check up")
    private String description;

    @Schema(description = "Appointment type",
            name = "type",
            example = "CONSULTATION")
    private AppointmentType type;

    @Schema(description = "Appointment status",
            name = "status",
            example = "SCHEDULED")
    private AppointmentStatus status;
}
