package com.pms.models.dto.payment;

import com.pms.models.dto.payment.validator.ValidCurrency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    @Schema(description = "Appointment Id",
            name = "appointmentId",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "34")
    @NotNull(message = "Appointment Id cannot be null")
    private Long appointmentId;

    @Schema(description = "Amount",
            name = "amount",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "100.30")
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    @Max(value = 10000, message = "Amount cannot exceed 10,000")
    private BigDecimal amount;

    @Schema(description = "Currency",
            name = "currency",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "100.30")
    @NotNull(message = "Currency cannot be null")
    @ValidCurrency(
            allowedCurrencies = {PaymentCurrency.USD, PaymentCurrency.EUR, PaymentCurrency.GBP, PaymentCurrency.BRL, PaymentCurrency.CAD, PaymentCurrency.CHF},
            message = "Only USD, EUR, GBP, BRL, CAD and CHF are supported for payments"
    )
    private PaymentCurrency currency;

    @Schema(description = "Payment Method Id",
            name = "paymentMethodId",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "pm_102943094")
    @NotBlank(message = "Payment method ID cannot be blank")
    private String paymentMethodId;
}
