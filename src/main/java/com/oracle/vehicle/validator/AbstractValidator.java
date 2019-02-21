package com.oracle.vehicle.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.oracle.vehicle.exception.TrackingException;
import com.oracle.vehicle.exception.ValidationException;
import com.oracle.vehicle.validator.rule.AbstractRule;

public abstract class AbstractValidator {

    private List<AbstractRule> rules = new ArrayList<>();

    protected AbstractValidator useRule(AbstractRule rule) {
        rules.add(rule);
        return this;
    }

    protected void apply() {
        ValidationException validationException = rules.stream()
                                                       .map(AbstractRule::apply)
                                                       .filter(Objects::nonNull)
                                                       .findFirst()
                                                       .orElse(null);

        if (Objects.nonNull(validationException)) {
            throw new TrackingException(validationException.getValidationMessage());
        }
    }

}
