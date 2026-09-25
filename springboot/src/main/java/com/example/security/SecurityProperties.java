package com.example.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private boolean enabled = true;
    private List<String> publicEndpoints = new ArrayList<>();
    private Cors cors = new Cors();

    @Data
    public static class Cors {
        private List<String> allowedOrigins = new ArrayList<>();
        private List<String> allowedMethods = new ArrayList<>();
        private List<String> allowedHeaders = new ArrayList<>();
        private boolean allowCredentials;
        private List<String> exposedHeaders = new ArrayList<>();
    }
}