package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private boolean enabled;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    // Computed field
    private String fullName;
}