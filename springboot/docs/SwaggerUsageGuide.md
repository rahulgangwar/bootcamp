# Swagger UI JWT Authentication Guide

## Overview
This guide explains how to use JWT authentication in Swagger UI for the Todo Application API.

## Prerequisites
- The application must be running
- You need valid user credentials to obtain a JWT token

## Step-by-Step Guide

### 1. Access Swagger UI
Open your browser and navigate to:
```
http://localhost:8080/swagger-ui/index.html
```

### 2. Obtain JWT Token
Before testing protected endpoints, you need to get a JWT token:

1. **Register a new user** (if you don't have an account):
   - Expand the `Authentication` section
   - Click on `POST /api/auth/register`
   - Click "Try it out"
   - Fill in the request body:
   ```json
   {
     "username": "testuser",
     "email": "test@example.com",
     "password": "password123"
   }
   ```
   - Click "Execute"

2. **Login to get JWT token**:
   - Click on `POST /api/auth/login`
   - Click "Try it out"
   - Fill in the request body:
   ```json
   {
     "username": "testuser",
     "password": "password123"
   }
   ```
   - Click "Execute"
   - Copy the `accessToken` from the response

### 3. Configure JWT Authentication in Swagger
1. Look for the **"Authorize"** button at the top right of the Swagger UI
2. Click the "Authorize" button
3. In the "bearerAuth" section, enter your JWT token:
   ```
   Bearer <your_jwt_token_here>
   ```
   **Note**: Include the word "Bearer" followed by a space, then your token
4. Click "Authorize"
5. Click "Close"

### 4. Test Protected Endpoints
Now you can test protected endpoints like:
- `GET /api/users` - Get all users with pagination
- `GET /api/users/all` - Get all users without pagination

### 5. Token Refresh
When your token expires, you can:
1. Use the `POST /api/auth/refresh` endpoint with your refresh token
2. Or login again to get a new token

## Security Features Implemented

### OpenAPI Configuration
- JWT Bearer authentication scheme
- Comprehensive API documentation
- Security requirements for protected endpoints

### Authentication Endpoints
- **POST /api/auth/register** - User registration
- **POST /api/auth/login** - User login
- **POST /api/auth/refresh** - Token refresh
- **GET /api/auth/verify** - Email verification
- **POST /api/auth/resend-verification** - Resend verification email

### Protected Endpoints
- **GET /api/users** - Paginated user list (requires authentication)
- **GET /api/users/all** - Complete user list (requires ADMIN role)

## Troubleshooting

### Common Issues
1. **401 Unauthorized Error**:
   - Ensure you've clicked "Authorize" and entered a valid token
   - Check if your token has expired
   - Verify the token format includes "Bearer " prefix

2. **403 Forbidden Error**:
   - Your token is valid but you don't have the required permissions
   - Some endpoints may require specific roles (e.g., ADMIN)

3. **Token Format**:
   - Correct: `Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
   - Incorrect: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...` (missing "Bearer ")

### Security Configuration
The application uses Spring Security with:
- JWT-based authentication
- Stateless session management
- CORS configuration for frontend integration
- BCrypt password encoding

## API Documentation Features
- Comprehensive endpoint descriptions
- Request/response examples
- Parameter documentation
- Error response codes
- Security requirements clearly marked

## Best Practices
1. Always logout or clear tokens when done testing
2. Don't share JWT tokens
3. Use different credentials for testing vs production
4. Monitor token expiration times
5. Use HTTPS in production environments