package com.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Test controller to demonstrate the AOP logging interceptor functionality.
 * This controller provides various endpoints to test different logging scenarios.
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
@Tag(name = "Logging Test", description = "Test endpoints for AOP logging demonstration")
public class LoggingTestController {

    @GetMapping("/simple")
    @Operation(summary = "Simple GET request", description = "Test basic logging with no parameters")
    public ResponseEntity<String> simpleGet() {
        return ResponseEntity.ok("Simple GET response");
    }

    @GetMapping("/with-params")
    @Operation(summary = "GET with query parameters", description = "Test logging with query parameters")
    public ResponseEntity<Map<String, Object>> getWithParams(
            @Parameter(description = "User ID") @RequestParam Long userId,
            @Parameter(description = "User name") @RequestParam String name,
            @Parameter(description = "Optional email") @RequestParam(required = false) String email) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("name", name);
        response.put("email", email);
        response.put("message", "Parameters logged successfully");
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/with-body")
    @Operation(summary = "POST with request body", description = "Test logging with request body parameters")
    public ResponseEntity<Map<String, Object>> postWithBody(@RequestBody TestRequestDto request) {
        Map<String, Object> response = new HashMap<>();
        response.put("receivedData", request);
        response.put("message", "Request body logged successfully");
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/with-sensitive-data")
    @Operation(summary = "POST with sensitive data", description = "Test logging with sensitive data redaction")
    public ResponseEntity<Map<String, Object>> postWithSensitiveData(@RequestBody SensitiveDataDto request) {
        Map<String, Object> response = new HashMap<>();
        response.put("username", request.getUsername());
        response.put("message", "Sensitive data processed (password redacted in logs)");
        // Note: password should be redacted in logs
        response.put("accessToken", "sample-token-12345"); // This will be redacted in response logs
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exception")
    @Operation(summary = "Endpoint that throws exception", description = "Test exception logging")
    public ResponseEntity<String> throwException() {
        throw new RuntimeException("This is a test exception for logging demonstration");
    }

    @GetMapping("/slow")
    @Operation(summary = "Slow endpoint", description = "Test execution time logging")
    public ResponseEntity<Map<String, Object>> slowEndpoint() throws InterruptedException {
        // Simulate slow processing
        Thread.sleep(2000); // 2 seconds delay
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Slow operation completed");
        response.put("executionTime", "~2 seconds");
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "PUT with path variable", description = "Test logging with path variables")
    public ResponseEntity<Map<String, Object>> updateResource(
            @Parameter(description = "Resource ID") @PathVariable Long id,
            @RequestBody TestRequestDto request) {
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("updatedData", request);
        response.put("message", "Resource updated successfully");
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "DELETE request", description = "Test DELETE method logging")
    public ResponseEntity<Map<String, String>> deleteResource(
            @Parameter(description = "Resource ID to delete") @PathVariable Long id) {
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Resource with ID " + id + " deleted successfully");
        response.put("status", "deleted");
        
        return ResponseEntity.ok(response);
    }

    // DTO classes for testing
    public static class TestRequestDto {
        private String name;
        private String email;
        private Integer age;
        private String description;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class SensitiveDataDto {
        private String username;
        private String password; // This should be redacted in logs
        private String token;     // This should be redacted in logs

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}