package com.pms.models.dto.doctor;

import com.pms.models.dto.PmsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorFilter extends PmsFilter {
    @Schema(description = "Doctor's firstName to be searched",
            name = "firstName",
            example = "John")
    private String firstName;

    @Schema(description = "Doctor's lastName to be searched",
            name = "lastName",
            example = "Foreman")
    private String lastName;

    @Schema(description = "Doctor's email to be searched",
            name = "email",
            example = "john.foreman@gmail.com")
    private String email;

    @Schema(description = "Doctor's speciality to be searched",
            name = "speciality",
            example = "Internal Medicine")
    private String speciality;
}
