package com.pms.models.dto.payment;

import com.pms.models.dto.PmsFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentFilter extends PmsFilter {
    @Schema(description = "Start amount to be searched",
            name = "startAmount",
            example = "100.0")
    private BigDecimal startAmount;

    @Schema(description = "End amount to be searched",
            name = "endAmount",
            example = "200.0")
    private BigDecimal endAmount;

    @Schema(description = "Currency to be searched",
            name = "currency",
            example = "USD")
    private PaymentCurrency currency;

    @Schema(description = "Currency to be searched",
            name = "currency",
            example = "USD")
    private PaymentStatus status;

    @Schema(description = "Appointment Id",
            name = "appointmentId",
            example = "23")
    private Long appointmentId;

}
