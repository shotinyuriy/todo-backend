package com.shotin.todobackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Data
@AllArgsConstructor
public class ServiceResult<T> {
    private T entity;
    private Set<ValidationError> violations;
    public boolean hasError() {
        return violations != null && !violations.isEmpty();
    }
}
