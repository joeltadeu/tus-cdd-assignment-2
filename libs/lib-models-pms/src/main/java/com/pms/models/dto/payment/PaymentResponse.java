package com.pms.models.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    @Schema(description = "Payment Id",
            name = "id",
            example = "12312")
    private Long id;

    @Schema(description = "Appointment Id",
            name = "appointmentId",
            example = "34")
    private Long appointmentId;

    @Schema(description = "Date and time of the payment",
            name = "paymentTimestamp",
            example = "2025-10-24 10:34:00")
    private LocalDateTime paymentTimestamp;

    @Schema(description = "Amount",
            name = "amount",
            example = "100.30")
    private BigDecimal amount;

    @Schema(description = "Currency",
            name = "amount",
            example = "100.30")
    private PaymentCurrency currency;

    @Schema(description = "Payment Method Id",
            name = "paymentMethodId",
            example = "pm_102943094")
    private String paymentMethodId;

    @Schema(description = "Payment status",
            name = "status",
            example = "PAID")
    private PaymentStatus status;
}
