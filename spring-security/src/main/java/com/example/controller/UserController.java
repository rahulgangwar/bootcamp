package com.example.controller;

import com.example.dto.UserDto;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    @Operation(summary = "Get all registered users", 
               description = "Retrieve a paginated list of all registered users in the system. Requires authentication.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Successfully retrieved users",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "401", 
                     description = "Unauthorized - Authentication required",
                     content = @Content),
        @ApiResponse(responseCode = "403", 
                     description = "Forbidden - Insufficient permissions",
                     content = @Content)
    })
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Sort field", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            
            @Parameter(description = "Sort direction (ASC or DESC)", example = "DESC")
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        log.info("Fetching users - page: {}, size: {}, sortBy: {}, sortDirection: {}", 
                 page, size, sortBy, sortDirection);
        
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC") 
                ? Sort.Direction.ASC 
                : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<UserDto> users = userService.getAllUsers(pageable);
        
        log.info("Retrieved {} users out of {} total", 
                 users.getNumberOfElements(), users.getTotalElements());
        
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/all")
    @Operation(summary = "Get all registered users without pagination", 
               description = "Retrieve a complete list of all registered users. Use with caution for large datasets.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Successfully retrieved users",
                     content = @Content(mediaType = "application/json",
                                       schema = @Schema(implementation = List.class))),
        @ApiResponse(responseCode = "401", 
                     description = "Unauthorized - Authentication required",
                     content = @Content),
        @ApiResponse(responseCode = "403", 
                     description = "Forbidden - Insufficient permissions",
                     content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')") // Optional: Restrict to admin users only
    public ResponseEntity<List<UserDto>> getAllUsersWithoutPagination() {
        log.info("Fetching all users without pagination");
        List<UserDto> users = userService.getAllUsers();
        log.info("Retrieved {} users", users.size());
        return ResponseEntity.ok(users);
    }
}