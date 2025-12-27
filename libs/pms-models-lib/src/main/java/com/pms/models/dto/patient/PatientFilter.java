package com.pms.models.dto.patient;

import com.pms.models.dto.PmsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientFilter extends PmsFilter {
    @Schema(description = "Patient's first name to be searched",
            name = "firstName",
            example = "John")
    private String firstName;

    @Schema(description = "Patient's last name to be searched",
            name = "lastName",
            example = "Foreman")
    private String lastName;

    @Schema(description = "Patient's email to be searched",
            name = "email",
            example = "john.foreman@gmail.com")
    private String email;
}
