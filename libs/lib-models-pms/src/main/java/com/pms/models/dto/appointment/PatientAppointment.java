package com.pms.models.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAppointment {
    @Schema(description = "Patient id",
            name = "id",
            example = "12")
    private Long id;

    @Schema(description = "Patient first name",
            name = "firstName",
            example = "John")
    private String firstName;

    @Schema(description = "Patient last name",
            name = "lastName",
            example = "Foreman")
    private String lastName;

    @Schema(description = "Patient email",
            name = "email",
            example = "john.foreman@gmail.com")
    private String email;
}
