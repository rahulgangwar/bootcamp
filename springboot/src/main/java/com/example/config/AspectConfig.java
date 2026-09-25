package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration class to enable Spring AOP (AspectJ) support.
 * This configuration enables the use of @Aspect annotations and
 * allows the ControllerLoggingAspect to intercept controller methods.
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    // This class enables AOP support in the Spring application
    // No additional configuration is needed as the aspect is
    // automatically detected via @Component annotation
}