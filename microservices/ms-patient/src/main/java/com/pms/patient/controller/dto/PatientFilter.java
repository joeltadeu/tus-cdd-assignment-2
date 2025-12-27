package com.pms.patient.controller.dto;

import com.pms.controller.filter.PmsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientFilter extends PmsFilter {
    @Schema(description = "Patient's name to be searched",
            name = "name",
            example = "John Foreman")
    private String name;

    @Schema(description = "Patient's email to be searched",
            name = "email",
            example = "john.foreman@gmail.com")
    private String email;
}
