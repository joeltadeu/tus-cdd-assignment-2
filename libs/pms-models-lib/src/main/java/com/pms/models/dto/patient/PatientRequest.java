package com.pms.models.dto.patient;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    @Schema(description = "Patient first name",
            name = "firstName",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "John")
    @NotBlank(message = "Patient's first name cannot be null")
    @Size(max = 50, message = "Patient's first name cannot exceed 50 characters")
    private String firstName;

    @Schema(description = "Patient last name",
            name = "lastName",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Foreman")
    @NotBlank(message = "Patient's name cannot be null")
    @Size(max = 50, message = "Patient's last name cannot exceed 50 characters")
    private String lastName;

    @Schema(description = "Patient email",
            name = "email",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "john.foreman@gmail.com")
    @NotBlank(message = "Patient's email cannot be null")
    @Email(message = "Invalid email")
    private String email;

    @Schema(description = "Patient address",
            name = "address",
            example = "Coosan Road, Jolly Mariner")
    private String address;

    @Schema(description = "Patient date of birth",
            name = "dateOfBirth",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1982-09-25")
    @NotNull(message = "Patient's date of birth cannot be null")
    private LocalDate dateOfBirth;
}
