package com.pms.models.dto.payment.validator;

import com.pms.models.dto.payment.PaymentCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCurrencyValidator implements ConstraintValidator<ValidCurrency, PaymentCurrency> {

    private PaymentCurrency[] allowedCurrencies;

    @Override
    public void initialize(ValidCurrency constraintAnnotation) {
        this.allowedCurrencies = constraintAnnotation.allowedCurrencies();
    }

    @Override
    public boolean isValid(PaymentCurrency value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // @NotNull should handle this, but added for safety
        }

        // If specific currencies are specified, validate against them
        if (allowedCurrencies.length > 0) {
            for (PaymentCurrency allowed : allowedCurrencies) {
                if (allowed == value) {
                    return true;
                }
            }
            return false;
        }

        // Otherwise, any enum value is valid
        return true;
    }
}
