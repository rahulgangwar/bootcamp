package com.example.service;

import com.example.dto.AuthResponseDto;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterRequestDto;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.User;
import com.example.model.VerificationToken;
import com.example.repository.UserRepository;
import com.example.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }

        // Create new user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                // User is enabled by default, set it to false if need to implement verify email feature
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        // Create verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(savedUser)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .tokenType(VerificationToken.TokenType.EMAIL_VERIFICATION)
                .build();
        verificationTokenRepository.save(verificationToken);

        // Send verification email
//        emailService.sendVerificationEmail(savedUser.getEmail(), savedUser.getFirstName(), token);

        // Generate JWT tokens
        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getJwtExpiration())
                .user(mapUserToDto(savedUser))
                .build();
    }

    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update last login
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT tokens
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtService.getJwtExpiration())
                .user(mapUserToDto(user))
                .build();
    }

    public AuthResponseDto refresh(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            
            return AuthResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtService.getJwtExpiration())
                    .user(mapUserToDto(user))
                    .build();
        } else {
            throw new BadRequestException("Invalid refresh token");
        }
    }

    @Transactional
    public void verifyEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Verification token has expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);
    }

    @Transactional
    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isEnabled()) {
            throw new BadRequestException("Email already verified");
        }

        // Delete old verification token if exists
        verificationTokenRepository.findByUserIdAndTokenTypeAndUsedFalse(user.getId(), VerificationToken.TokenType.EMAIL_VERIFICATION)
                .ifPresent(verificationTokenRepository::delete);

        // Create new verification token
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusHours(24))
                .tokenType(VerificationToken.TokenType.EMAIL_VERIFICATION)
                .build();
        verificationTokenRepository.save(verificationToken);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), user.getFirstName(), token);
    }

    private AuthResponseDto.UserDto mapUserToDto(User user) {
        return AuthResponseDto.UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicture(user.getProfilePicture())
                .enabled(user.isEnabled())
                .role(user.getRole().name())
                .timezone(user.getTimezone())
                .dateFormat(user.getDateFormat())
                .theme(user.getTheme())
                .build();
    }
}