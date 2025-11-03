package com.example.lambda.handlers;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for HelloHandler.
 */
class HelloHandlerTest {

    private HelloHandler helloHandler;
    private MeterRegistry meterRegistry;

    @BeforeEach
    void setUp() {
        meterRegistry = new SimpleMeterRegistry();
        helloHandler = new HelloHandler(meterRegistry);
    }

    @Test
    void shouldProcessGreetingWithName() {
        StepVerifier.create(helloHandler.processGreeting("TestUser"))
            .expectNext("Hello, TestUser!")
            .verifyComplete();
    }

    @Test
    void shouldProcessGreetingWithEmptyName() {
        StepVerifier.create(helloHandler.processGreeting(""))
            .expectNext("Hello, World!")
            .verifyComplete();
    }

    @Test
    void shouldProcessGreetingWithNullName() {
        StepVerifier.create(helloHandler.processGreeting(null))
            .expectNext("Hello, World!")
            .verifyComplete();
    }

    @Test
    void shouldIncrementInvocationCounter() {
        double initialCount = helloHandler.getInvocationCount();
        
        // Use StepVerifier instead of .block() to properly test reactive code
        StepVerifier.create(helloHandler.processGreeting("TestUser"))
            .expectNextMatches(greeting -> {
                assertThat(helloHandler.getInvocationCount()).isGreaterThan(initialCount);
                return true;
            })
            .verifyComplete();
    }
}



