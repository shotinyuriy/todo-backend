package com.shotin.todobackend.service;

import com.shotin.todobackend.model.ValidationError;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

public class ViolationConverter {
    public static <T> Set<ValidationError> toValidationErrors(Set<ConstraintViolation<T>> violations) {
        Set<ValidationError> errors = new HashSet<>();
        for(ConstraintViolation<T> violation : violations) {
            errors.add(new ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()));
        }
        return errors;
    }
}
