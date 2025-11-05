package com.example.lambda.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reactive validator for request validation using Bean Validation.
 */
@Component
public class RequestValidator {

    private static final Logger logger = LoggerFactory.getLogger(RequestValidator.class);
    private final Validator validator;

    public RequestValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates an object reactively using Bean Validation.
     *
     * @param <T> the type of object to validate
     * @param object the object to validate
     * @return Mono with the validated object, or error if validation fails
     */
    public <T> Mono<T> validate(T object) {
        return Mono.fromCallable(() -> {
                Set<ConstraintViolation<T>> violations = validator.validate(object);
                if (!violations.isEmpty()) {
                    String errors = violations.stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect(Collectors.joining(", "));
                    throw new ValidationException("Validation failed: " + errors);
                }
                return object;
            })
            .subscribeOn(Schedulers.boundedElastic())
            .doOnError(ValidationException.class, e -> logger.warn("Validation error: {}", e.getMessage()));
    }

    /**
     * Custom validation exception.
     */
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }
}



