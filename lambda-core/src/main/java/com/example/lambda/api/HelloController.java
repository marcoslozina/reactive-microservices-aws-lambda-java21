package com.example.lambda.api;

import com.example.lambda.handlers.HelloHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Reactive HTTP facade so the same Lambda function can be exercised locally via Docker.
 * It keeps the contract simple (`/hello?name=`) while reusing the existing HelloHandler logic.
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    private final HelloHandler helloHandler;

    public HelloController(HelloHandler helloHandler) {
        this.helloHandler = helloHandler;
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, Object>>> helloGet(@RequestParam(required = false) String name) {
        return buildResponse(name, null);
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> helloPost(
        @RequestParam(required = false) String name,
        @RequestBody(required = false) Map<String, Object> body
    ) {
        String resolvedName = name;
        if (!StringUtils.hasText(resolvedName) && body != null) {
            Object bodyName = body.get("name");
            resolvedName = bodyName != null ? String.valueOf(bodyName) : null;
        }
        return buildResponse(resolvedName, body);
    }

    private Mono<ResponseEntity<Map<String, Object>>> buildResponse(String name, Map<String, Object> body) {
        Mono<String> greetingMono = StringUtils.hasText(name)
            ? helloHandler.processGreeting(name.trim())
                .timeout(Duration.ofSeconds(5))
                .onErrorResume(error -> {
                    log.warn("Fallo procesando saludo, devolviendo valor por defecto", error);
                    return Mono.just("Hello, World!");
                })
            : Mono.just("Hello, World!");

        return greetingMono.map(greeting -> {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "ok");
            response.put("greeting", greeting);
            response.put("timestamp", Instant.now().toString());
            if (StringUtils.hasText(name)) {
                response.put("name", name);
            }
            if (body != null && !body.isEmpty()) {
                response.put("body", body);
            }
            return ResponseEntity.ok(response);
        });
    }
}

