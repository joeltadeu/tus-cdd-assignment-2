package com.pms.models.dto.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    @Schema(description = "Doctor first name",
            name = "firstName",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "John")
    @NotBlank(message = "Doctor's first name cannot be null")
    @Size(max = 50, message = "Doctor's first name cannot exceed 50 characters")
    private String firstName;

    @Schema(description = "Doctor last name",
            name = "lastName",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Foreman")
    @NotBlank(message = "Doctor's last name cannot be null")
    @Size(max = 50, message = "Doctor's last name cannot exceed 50 characters")
    private String lastName;

    @Schema(description = "Doctor title",
            name = "title",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Dr.")
    @NotBlank(message = "Doctor's title cannot be null")
    private String title;

    @Schema(description = "Doctor speciality id",
            name = "specialityId",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "1")
    @NotNull(message = "Doctor's speciality id cannot be null")
    private Long specialityId;

    @Schema(description = "Doctor email",
            name = "email",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "john.foreman@gmail.com")
    @NotBlank(message = "Doctor's email cannot be null")
    @Email(message = "Invalid email")
    private String email;

    @Schema(description = "Doctor phone",
            name = "phone",
            example = "+1-555-0123")
    private String phone;

    @Schema(description = "Doctor department",
            name = "department",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "Primary Care")
    @NotNull(message = "Doctor's department cannot be null")
    private String department;
}
