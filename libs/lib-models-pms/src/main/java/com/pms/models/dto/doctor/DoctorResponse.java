package com.pms.models.dto.doctor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {
    @Schema(description = "Doctor id",
            name = "id",
            example = "12")
    private Long id;

    @Schema(description = "Doctor first name",
            name = "firstName",
            example = "John")
    private String firstName;

    @Schema(description = "Doctor last name",
            name = "lastName",
            example = "Foreman")
    private String lastName;

    @Schema(description = "Doctor title",
            name = "title",
            example = "Dr.")
    private String title;

    @Schema(description = "Doctor speciality",
            name = "speciality",
            example = "Primary Care")
    private String speciality;

    @Schema(description = "Doctor email",
            name = "email",
            example = "john.foreman@gmail.com")
    private String email;

    @Schema(description = "Doctor phone",
            name = "phone",
            example = "+1-555-0123")
    private String phone;

    @Schema(description = "Doctor department",
            name = "department",
            example = "Primary Care")
    private String department;
}
