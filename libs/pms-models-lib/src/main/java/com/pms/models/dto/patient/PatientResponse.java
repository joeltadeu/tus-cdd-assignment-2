package com.pms.models.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
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

    @Schema(description = "Patient address",
            name = "address",
            example = "Coosan Road, Jolly Mariner")
    private String address;

    @Schema(description = "Patient date of birth",
            name = "dateOfBirth",
            example = "1982-09-25")
    private LocalDate dateOfBirth;
}
