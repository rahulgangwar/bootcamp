# Spring AOP Controller Logging Interceptor

This document describes the implementation of a Spring AOP aspect that intercepts and logs all controller method calls, including parameters and responses.

## Overview

The `ControllerLoggingAspect` is a comprehensive logging solution that provides:

- **Request/Response Logging**: Captures HTTP request details and response data
- **Method Parameter Logging**: Logs all method parameters with type information
- **Execution Time Tracking**: Measures and logs method execution time
- **Exception Handling**: Logs exceptions with stack traces
- **Security**: Automatically redacts sensitive information from logs
- **Request Tracking**: Assigns unique request IDs for correlation

## Features

### 1. HTTP Request Logging
- HTTP method (GET, POST, PUT, DELETE)
- Request URL and query parameters
- Client IP address (with X-Forwarded-For support)
- User-Agent information
- Request headers (sensitive headers redacted)

### 2. Method Parameter Logging
- Parameter types and values
- JSON serialization of complex objects
- Automatic truncation of large parameters
- Sensitive data redaction (passwords, tokens)

### 3. Response Logging
- Response status codes
- Response body (sanitized)
- Response types
- Automatic truncation of large responses

### 4. Performance Monitoring
- Method execution time in milliseconds
- Request start and completion timestamps
- Performance bottleneck identification

### 5. Security Features
- Automatic redaction of sensitive fields:
  - `password`
  - `token`
  - `accessToken`
  - `refreshToken`
  - `Authorization` headers
- Configurable sensitive header filtering
- Parameter value sanitization

## Implementation Details

### Files Created

1. **`ControllerLoggingAspect.java`** - Main aspect class
2. **`AspectConfig.java`** - Configuration to enable AOP
3. **Updated `pom.xml`** - Added Spring AOP dependency

### Dependencies Added

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### Pointcut Definition

The aspect intercepts all methods in the controller package:

```java
@Pointcut("execution(* com.example.controller.*.*(..))")
public void controllerMethods() {}
```

## Log Output Examples

### Request Start
```
[REQUEST-START] [REQ-1683123456789-1] POST http://localhost:8080/api/auth/login - User-Agent: PostmanRuntime/7.32.2 - IP: 127.0.0.1
[REQUEST-HEADERS] [REQ-1683123456789-1] Headers: {Content-Type=application/json, Accept=*/*}
[REQUEST-START] [REQ-1683123456789-1] Executing AuthController.login
```

### Method Parameters
```
[CONTROLLER] Entering method: AuthController.login with 1 parameters
[PARAMETERS] Parameter[0] (LoginRequestDto): {"username":"john.doe","password":"[REDACTED]"}
```

### Successful Response
```
[REQUEST-SUCCESS] [REQ-1683123456789-1] AuthController.login completed in 245ms
[RESPONSE] [REQ-1683123456789-1] Status: 200 OK, Type: AuthResponseDto
[RESPONSE] [REQ-1683123456789-1] Body: {"accessToken":"[REDACTED]","refreshToken":"[REDACTED]","user":{"id":1,"username":"john.doe"}}
```

### Exception Handling
```
[REQUEST-ERROR] [REQ-1683123456789-1] AuthController.login failed after 123ms with exception: Invalid credentials
[CONTROLLER] Method AuthController.login threw exception: Invalid credentials
```

## Configuration

### Enable AOP
The `@EnableAspectJAutoProxy` annotation in `AspectConfig.java` enables AOP support.

### Logging Levels
Configure logging levels in `application.properties`:

```properties
# Enable debug logging for the aspect
logging.level.com.example.aspect.ControllerLoggingAspect=INFO

# Enable debug logging for all controllers
logging.level.com.example.controller=DEBUG

# Set root logging level
logging.level.root=INFO
```

### Sensitive Data Configuration
The aspect automatically redacts common sensitive fields. To customize:

1. Modify the `redactSensitiveData()` method
2. Update the `isSensitiveHeader()` method for headers
3. Adjust the regex patterns for field matching

## Performance Considerations

### Log Volume
- Large request/response bodies are automatically truncated
- Parameters over 500 characters are truncated
- Responses over 1000 characters are truncated

### Performance Impact
- Minimal overhead for method interception
- JSON serialization only for logging (not affecting business logic)
- Asynchronous logging recommended for high-traffic applications

### Memory Usage
- Request IDs are generated per request (no memory accumulation)
- Temporary objects are garbage collected after logging

## Customization

### Adding New Sensitive Fields
To redact additional sensitive fields, update the `redactSensitiveData()` method:

```java
return jsonString
    .replaceAll("(\"newSensitiveField\"\\s*:\\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
    // ... existing patterns
```

### Changing Log Format
Modify the log statements in the aspect methods to change the output format.

### Excluding Specific Controllers
To exclude specific controllers from logging, modify the pointcut:

```java
@Pointcut("execution(* com.example.controller.*.*(..)) && !execution(* com.example.controller.HealthController.*(..))")
public void controllerMethods() {}
```

### Adding Method-Level Control
Use custom annotations to control logging behavior:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogging {
}
```

Then update the pointcut to exclude annotated methods.

## Testing

### Verify Installation
1. Start the application
2. Make a request to any controller endpoint
3. Check the logs for the expected output format

### Test Scenarios
- Login with valid credentials
- Login with invalid credentials (exception handling)
- Register a new user (parameter logging)
- Access protected endpoints (header logging)

## Troubleshooting

### Common Issues

1. **Aspect not working**
   - Ensure `@EnableAspectJAutoProxy` is present
   - Verify Spring AOP dependency is included
   - Check that the aspect class has `@Component` annotation

2. **No logs appearing**
   - Check logging configuration in `application.properties`
   - Verify log level is set to INFO or DEBUG
   - Ensure the pointcut matches your controller package

3. **Performance issues**
   - Consider using asynchronous logging
   - Reduce log verbosity in production
   - Monitor memory usage with large payloads

4. **Sensitive data exposure**
   - Review the `redactSensitiveData()` method
   - Add new patterns for custom sensitive fields
   - Test with real data to ensure proper redaction

## Security Best Practices

1. **Never log actual passwords or tokens**
2. **Use structured logging for better parsing**
3. **Implement log rotation to prevent disk space issues**
4. **Consider encrypting log files in production**
5. **Regularly audit logged data for compliance**
6. **Use centralized logging for distributed systems**

## Integration with Monitoring

This logging aspect can be integrated with:
- **ELK Stack** (Elasticsearch, Logstash, Kibana)
- **Splunk** for log analysis
- **Application Performance Monitoring** tools
- **Custom metrics collection** for performance monitoring

## Conclusion

The Spring AOP Controller Logging Interceptor provides comprehensive request/response logging with security considerations and performance optimizations. It's designed to be production-ready while maintaining flexibility for customization.