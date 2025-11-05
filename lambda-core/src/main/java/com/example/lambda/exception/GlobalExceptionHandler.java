package com.example.lambda.exception;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for Lambda functions.
 * Provides centralized error handling and response formatting.
 */
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles runtime exceptions in Lambda functions.
     *
     * @param throwable the exception to handle
     * @param requestId the request ID for tracing
     * @return error response
     */
    public static APIGatewayProxyResponseEvent handleException(Throwable throwable, String requestId) {
        logger.error("Exception in Lambda function", throwable);

        HttpStatus status = determineHttpStatus(throwable);
        String errorMessage = sanitizeErrorMessage(throwable);

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(status.value());

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", errorMessage);
        errorBody.put("status", status.value());
        errorBody.put("timestamp", java.time.Instant.now().toString());
        errorBody.put("requestId", requestId);

        // Only include stack trace in development (not in production)
        if (Boolean.parseBoolean(System.getenv().getOrDefault("INCLUDE_STACK_TRACE", "false"))) {
            errorBody.put("stackTrace", getStackTrace(throwable));
        }

        response.setBody(formatErrorBody(errorBody));
        
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.put("X-Request-Id", requestId);
        response.setHeaders(headers);

        return response;
    }

    /**
     * Determines HTTP status code based on exception type.
     */
    private static HttpStatus determineHttpStatus(Throwable throwable) {
        if (throwable instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (throwable instanceof com.example.lambda.validation.RequestValidator.ValidationException) {
            return HttpStatus.BAD_REQUEST;
        } else if (throwable instanceof SecurityException) {
            return HttpStatus.FORBIDDEN;
        } else if (throwable instanceof RuntimeException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Sanitizes error message to prevent information leakage.
     */
    private static String sanitizeErrorMessage(Throwable throwable) {
        String message = throwable.getMessage();
        if (message == null || message.isEmpty()) {
            return "An unexpected error occurred";
        }
        
        // In production, don't expose internal details
        if (!Boolean.parseBoolean(System.getenv().getOrDefault("DEVELOPMENT_MODE", "false"))) {
            // Return generic message for security
            return "Internal server error";
        }
        
        return message;
    }

    /**
     * Formats error body as JSON.
     */
    private static String formatErrorBody(Map<String, Object> errorBody) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper()
                .writeValueAsString(errorBody);
        } catch (Exception e) {
            // Fallback to manual JSON if Jackson fails
            return String.format(
                "{\"error\":\"%s\",\"status\":%d,\"timestamp\":\"%s\",\"requestId\":\"%s\"}",
                errorBody.get("error"),
                errorBody.get("status"),
                errorBody.get("timestamp"),
                errorBody.get("requestId")
            );
        }
    }

    /**
     * Gets stack trace as string array.
     */
    private static String[] getStackTrace(Throwable throwable) {
        return java.util.Arrays.stream(throwable.getStackTrace())
            .map(StackTraceElement::toString)
            .toArray(String[]::new);
    }
}



