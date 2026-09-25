package com.example.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring AOP Aspect for logging all controller method calls, parameters, and responses.
 * This interceptor captures:
 * - HTTP request details (method, URL, headers)
 * - Method parameters and their values
 * - Method execution time
 * - Response data
 * - Exception handling
 */
@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Pointcut that matches all methods in controller packages
     */
    @Pointcut("execution(* com.example.controller.*.*(..))")
    public void controllerMethods() {}

    /**
     * Around advice that logs method execution details
     * @param joinPoint the join point representing the method execution
     * @return the result of the method execution
     * @throws Throwable if the method throws an exception
     */
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // Get HTTP request details
        HttpServletRequest request = getCurrentHttpRequest();
        String requestId = generateRequestId();
        
        // Log request start
        logRequestStart(joinPoint, request, requestId);
        
        Object result = null;
        try {
            // Execute the actual method
            result = joinPoint.proceed();
            
            // Log successful response
            logRequestSuccess(joinPoint, result, startTime, requestId);
            
            return result;
            
        } catch (Exception ex) {
            // Log exception
            logRequestException(joinPoint, ex, startTime, requestId);
            throw ex;
        }
    }

    /**
     * Before advice to log method entry with parameters
     */
    @Before("controllerMethods()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("[CONTROLLER] Entering method: {}.{} with {} parameters", 
                className, methodName, args.length);
        
        // Log parameters (excluding sensitive data)
        logMethodParameters(args, methodName);
    }

    /**
     * After returning advice to log successful method completion
     */
    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("[CONTROLLER] Method {}.{} completed successfully", className, methodName);
    }

    /**
     * After throwing advice to log exceptions
     */
    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.error("[CONTROLLER] Method {}.{} threw exception: {}", 
                className, methodName, exception.getMessage(), exception);
    }

    /**
     * Log request start with HTTP details
     */
    private void logRequestStart(ProceedingJoinPoint joinPoint, HttpServletRequest request, String requestId) {
        if (request != null) {
            String method = request.getMethod();
            String url = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            String userAgent = request.getHeader("User-Agent");
            String remoteAddr = getClientIpAddress(request);
            
            logger.info("[REQUEST-START] [{}] {} {} {} - User-Agent: {} - IP: {}", 
                    requestId, method, url, 
                    queryString != null ? "?" + queryString : "", 
                    userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 50)) : "Unknown",
                    remoteAddr);
            
            // Log headers (excluding sensitive ones)
            logRequestHeaders(request, requestId);
        }
        
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("[REQUEST-START] [{}] Executing {}.{}", requestId, className, methodName);
    }

    /**
     * Log successful request completion
     */
    private void logRequestSuccess(ProceedingJoinPoint joinPoint, Object result, long startTime, String requestId) {
        long executionTime = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.info("[REQUEST-SUCCESS] [{}] {}.{} completed in {}ms", 
                requestId, className, methodName, executionTime);
        
        // Log response (excluding sensitive data)
        logResponse(result, requestId);
    }

    /**
     * Log request exception
     */
    private void logRequestException(ProceedingJoinPoint joinPoint, Exception ex, long startTime, String requestId) {
        long executionTime = System.currentTimeMillis() - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        logger.error("[REQUEST-ERROR] [{}] {}.{} failed after {}ms with exception: {}", 
                requestId, className, methodName, executionTime, ex.getMessage());
    }

    /**
     * Log method parameters (excluding sensitive data)
     */
    private void logMethodParameters(Object[] args, String methodName) {
        if (args == null || args.length == 0) {
            logger.info("[PARAMETERS] No parameters for method: {}", methodName);
            return;
        }
        
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                logger.info("[PARAMETERS] Parameter[{}]: null", i);
            } else {
                String paramType = arg.getClass().getSimpleName();
                String paramValue = sanitizeParameterValue(arg, methodName);
                logger.info("[PARAMETERS] Parameter[{}] ({}): {}", i, paramType, paramValue);
            }
        }
    }

    /**
     * Log request headers (excluding sensitive ones)
     */
    private void logRequestHeaders(HttpServletRequest request, String requestId) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            
            // Exclude sensitive headers
            if (!isSensitiveHeader(headerName)) {
                headers.put(headerName, headerValue);
            } else {
                headers.put(headerName, "[REDACTED]");
            }
        }
        
        logger.info("[REQUEST-HEADERS] [{}] Headers: {}", requestId, headers);
    }

    /**
     * Log response data (excluding sensitive information)
     */
    private void logResponse(Object result, String requestId) {
        if (result == null) {
            logger.info("[RESPONSE] [{}] Response: null", requestId);
            return;
        }
        
        try {
            String responseType = result.getClass().getSimpleName();
            
            // For ResponseEntity, extract the body
            if (result instanceof org.springframework.http.ResponseEntity) {
                org.springframework.http.ResponseEntity<?> responseEntity = (org.springframework.http.ResponseEntity<?>) result;
                Object body = responseEntity.getBody();
                String statusCode = responseEntity.getStatusCode().toString();
                
                logger.info("[RESPONSE] [{}] Status: {}, Type: {}", requestId, statusCode, 
                        body != null ? body.getClass().getSimpleName() : "null");
                
                // Log response body (sanitized)
                if (body != null) {
                    String sanitizedResponse = sanitizeResponseValue(body);
                    logger.info("[RESPONSE] [{}] Body: {}", requestId, sanitizedResponse);
                }
            } else {
                String sanitizedResponse = sanitizeResponseValue(result);
                logger.info("[RESPONSE] [{}] Type: {}, Data: {}", requestId, responseType, sanitizedResponse);
            }
        } catch (Exception e) {
            logger.warn("[RESPONSE] [{}] Failed to log response: {}", requestId, e.getMessage());
        }
    }

    /**
     * Sanitize parameter values to exclude sensitive data
     */
    private String sanitizeParameterValue(Object param, String methodName) {
        if (param == null) {
            return "null";
        }
        
        try {
            String paramString = objectMapper.writeValueAsString(param);
            
            // Redact sensitive fields
            paramString = redactSensitiveData(paramString);
            
            // Limit length to prevent log flooding
            if (paramString.length() > 500) {
                return paramString.substring(0, 500) + "... [TRUNCATED]";
            }
            
            return paramString;
        } catch (Exception e) {
            return param.toString();
        }
    }

    /**
     * Sanitize response values to exclude sensitive data
     */
    private String sanitizeResponseValue(Object response) {
        if (response == null) {
            return "null";
        }
        
        try {
            String responseString = objectMapper.writeValueAsString(response);
            
            // Redact sensitive fields
            responseString = redactSensitiveData(responseString);
            
            // Limit length to prevent log flooding
            if (responseString.length() > 1000) {
                return responseString.substring(0, 1000) + "... [TRUNCATED]";
            }
            
            return responseString;
        } catch (Exception e) {
            return response.toString();
        }
    }

    /**
     * Redact sensitive data from JSON strings
     */
    private String redactSensitiveData(String jsonString) {
        if (jsonString == null) {
            return null;
        }
        
        // Redact common sensitive fields
        return jsonString
                .replaceAll("(\"password\"\s*:\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
                .replaceAll("(\"token\"\s*:\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
                .replaceAll("(\"accessToken\"\s*:\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
                .replaceAll("(\"refreshToken\"\s*:\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
                .replaceAll("(\"Authorization\"\s*:\s*\")[^\"]*(\")?", "$1[REDACTED]$2")
                .replaceAll("(\"Bearer\s+)[^\"\s]*", "$1[REDACTED]");
    }

    /**
     * Check if header contains sensitive information
     */
    private boolean isSensitiveHeader(String headerName) {
        String lowerCaseHeader = headerName.toLowerCase();
        return lowerCaseHeader.contains("authorization") ||
               lowerCaseHeader.contains("cookie") ||
               lowerCaseHeader.contains("x-api-key") ||
               lowerCaseHeader.contains("x-auth-token");
    }

    /**
     * Get current HTTP request from RequestContextHolder
     */
    private HttpServletRequest getCurrentHttpRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return attributes.getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generate unique request ID for tracking
     */
    private String generateRequestId() {
        return "REQ-" + System.currentTimeMillis() + "-" + Thread.currentThread().getId();
    }

    /**
     * Get client IP address from request
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
}