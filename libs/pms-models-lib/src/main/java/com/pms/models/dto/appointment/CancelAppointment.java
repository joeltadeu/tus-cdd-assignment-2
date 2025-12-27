package com.pms.models.dto.appointment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CancelAppointment {
    @Schema(description = "Cancellation reason",
            name = "reason",
            example = "Patient emergency")
    private String reason;

}
