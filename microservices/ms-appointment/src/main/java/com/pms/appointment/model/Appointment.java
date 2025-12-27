package com.pms.appointment.model;

import com.pms.models.dto.appointment.AppointmentStatus;
import com.pms.models.dto.appointment.AppointmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private AppointmentType type;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    private Boolean followUpRequired;
    private LocalDateTime cancellationTime;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
