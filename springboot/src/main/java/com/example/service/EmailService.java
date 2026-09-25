package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${app.email.from}")
    private String fromEmail;
    
    @Value("${app.frontend.url}")
    private String frontendUrl;
    
    public void sendVerificationEmail(String to, String name, String token) {
        try {
            String subject = "Verify your Todo App account";
            String verificationUrl = frontendUrl + "/verify-email?token=" + token;
            
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("verificationUrl", verificationUrl);
            
            String htmlContent = buildVerificationEmailContent(name, verificationUrl);
            
            sendHtmlEmail(to, subject, htmlContent);
            log.info("Verification email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", to, e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
    
    public void sendPasswordResetEmail(String to, String name, String token) {
        try {
            String subject = "Reset your Todo App password";
            String resetUrl = frontendUrl + "/reset-password?token=" + token;
            
            String htmlContent = buildPasswordResetEmailContent(name, resetUrl);
            
            sendHtmlEmail(to, subject, htmlContent);
            log.info("Password reset email sent to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", to, e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
    
    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
    }
    
    private String buildVerificationEmailContent(String name, String verificationUrl) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Email Verification</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #4CAF50;'>Welcome to Todo App!</h2>" +
                "<p>Hi " + name + ",</p>" +
                "<p>Thank you for registering with Todo App. Please click the button below to verify your email address:</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + verificationUrl + "' style='background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;'>Verify Email</a>" +
                "</div>" +
                "<p>Or copy and paste this link into your browser:</p>" +
                "<p style='word-break: break-all; color: #666;'>" + verificationUrl + "</p>" +
                "<p>This link will expire in 24 hours.</p>" +
                "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>" +
                "<p style='color: #666; font-size: 14px;'>If you didn't create an account with Todo App, please ignore this email.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
    
    private String buildPasswordResetEmailContent(String name, String resetUrl) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Password Reset</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #FF9800;'>Password Reset Request</h2>" +
                "<p>Hi " + name + ",</p>" +
                "<p>We received a request to reset your password. Click the button below to create a new password:</p>" +
                "<div style='text-align: center; margin: 30px 0;'>" +
                "<a href='" + resetUrl + "' style='background-color: #FF9800; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; display: inline-block;'>Reset Password</a>" +
                "</div>" +
                "<p>Or copy and paste this link into your browser:</p>" +
                "<p style='word-break: break-all; color: #666;'>" + resetUrl + "</p>" +
                "<p>This link will expire in 24 hours.</p>" +
                "<hr style='border: none; border-top: 1px solid #eee; margin: 30px 0;'>" +
                "<p style='color: #666; font-size: 14px;'>If you didn't request a password reset, please ignore this email.</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }
}