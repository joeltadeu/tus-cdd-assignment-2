package com.pms.models.dto.payment.validator;

import com.pms.models.dto.payment.PaymentCurrency;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCurrencyValidator.class)
@Documented
public @interface ValidCurrency {
    String message() default "Invalid currency. Supported currencies: USD, EUR, GBP, BRL, CAD, CHF";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // Optional: Specify which currencies are allowed
    PaymentCurrency[] allowedCurrencies() default {};
}
