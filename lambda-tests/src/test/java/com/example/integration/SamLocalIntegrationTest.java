package com.example.integration;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.lambda.FunctionConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for SAM Local invocations.
 * 
 * To run SAM local tests:
 * 1. Build the project: ./gradlew clean build
 * 2. Start SAM local: sam local start-api (from lambda-infra directory)
 * 3. Or invoke directly: sam local invoke "ReactiveFunction" --event events/hello.json
 */
@SpringBootTest(classes = com.example.lambda.LambdaApplication.class)
@TestPropertySource(properties = {
    "spring.cloud.function.definition=hello",
    "spring.main.web-application-type=reactive"
})
class SamLocalIntegrationTest {

    @org.springframework.beans.factory.annotation.Autowired
    private FunctionConfig functionConfig;

    private Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> helloFunction;

    @BeforeEach
    void setUp() {
        helloFunction = functionConfig.hello();
    }

    @Test
    void shouldHandleGetRequest() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setHttpMethod("GET");
        request.setPath("/hello");
        request.setQueryStringParameters(Map.of("name", "TestUser"));
        
        // When
        APIGatewayProxyResponseEvent response = helloFunction.apply(request);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"message\":\"ok\"");
        assertThat(response.getHeaders()).containsKey("Content-Type");
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo("application/json");
    }

    @Test
    void shouldHandlePostRequestWithBody() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setHttpMethod("POST");
        request.setPath("/hello");
        request.setBody("{\"name\":\"TestUser\",\"message\":\"Hello\"}");
        
        // When
        APIGatewayProxyResponseEvent response = helloFunction.apply(request);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"message\":\"ok\"");
    }

    @Test
    void shouldHandleRequestWithPathParameters() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setHttpMethod("GET");
        request.setPath("/hello/user/123");
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", "123");
        request.setPathParameters(pathParameters);
        
        // When
        APIGatewayProxyResponseEvent response = helloFunction.apply(request);
        
        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).contains("\"message\":\"ok\"");
        assertThat(response.getBody()).contains("pathParams");
    }

    @Test
    void shouldReturnJsonContentType() {
        // Given
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        request.setHttpMethod("GET");
        request.setPath("/hello");
        
        // When
        APIGatewayProxyResponseEvent response = helloFunction.apply(request);
        
        // Then
        assertThat(response.getHeaders()).isNotNull();
        assertThat(response.getHeaders().get("Content-Type")).isEqualTo("application/json");
    }
}

