# ChattMate Spring Boot Application - Component Architecture

## Overview
This document describes the complete component architecture for the ChattMate streaming chat application built with Spring Boot.

## Directory Structure

```
src/main/java/com/chattmate/api/
├── ChattMateApplication.java          # Main application entry point
├── controller/                        # REST API endpoints & HTTP handling
│   ├── UserController.java
│   ├── AuthController.java
│   ├── KycController.java
│   ├── PaymentController.java
│   ├── StreamingController.java
│   └── ChatController.java
├── service/                           # Business logic & use cases
│   ├── UserService.java
│   ├── AuthenticationService.java
│   ├── ValidationService.java
│   ├── KycService.java
│   ├── PaymentService.java
│   ├── LiveKitTokenService.java
│   ├── StreamingService.java
│   └── ChatService.java
├── repository/                        # Data access layer (JPA)
│   ├── UserRepository.java
│   ├── ChatRepository.java
│   ├── StreamRepository.java
│   ├── TransactionRepository.java
│   └── KycRecordRepository.java
├── model/
│   ├── entity/                        # JPA Entities (Database Models)
│   │   ├── User.java
│   │   ├── Chat.java
│   │   ├── Stream.java
│   │   ├── Transaction.java
│   │   └── KycRecord.java
│   ├── dto/                           # Data Transfer Objects (Request/Response)
│   │   ├── UserDTO.java
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── ProfileUpdateRequest.java
│   │   ├── ChatMessageDTO.java
│   │   └── StreamingTokenRequest.java
│   └── enum/                          # Enumerations
│       ├── KycStatus.java
│       ├── UserRole.java
│       ├── TransactionType.java
│       └── StreamStatus.java
├── config/                            # Spring Configuration Classes
│   ├── CorsConfig.java
│   ├── JpaConfig.java
│   ├── SecurityConfig.java
│   └── LiveKitConfig.java
├── security/                          # Security & Authentication
│   ├── JwtTokenProvider.java
│   ├── CustomUserDetailsService.java
│   └── SecurityUtil.java
├── exception/                         # Custom Exception Classes
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   ├── ValidationException.java
│   └── GlobalExceptionHandler.java
├── validation/                        # Custom Validators
│   ├── AgeValidator.java
│   ├── EmailValidator.java
│   └── KycValidator.java
├── middleware/                        # Interceptors & Filters
│   ├── RequestLoggingFilter.java
│   ├── AuthenticationFilter.java
│   └── ErrorHandlingFilter.java
├── messaging/                         # WebSocket & Real-time Messaging
│   ├── WebSocketConfig.java
│   ├── ChatWebSocketHandler.java
│   └── MessageService.java
├── streaming/                         # LiveKit Streaming Integration
│   ├── StreamingService.java
│   ├── RoomManagementService.java
│   └── StreamEventListener.java
└── util/                              # Utility Classes
    ├── CryptoUtil.java
    ├── JwtUtil.java
    ├── DateUtil.java
    └── ResponseUtil.java

src/main/resources/
├── application.properties             # Main configuration
├── application-dev.properties         # Development profile
├── application-prod.properties        # Production profile
└── db/
    └── migration/                     # Flyway/Liquibase migrations

src/test/java/com/chattmate/api/
├── controller/
│   ├── UserControllerTest.java
│   └── AuthControllerTest.java
├── service/
│   ├── UserServiceTest.java
│   └── ValidationServiceTest.java
└── integration/
    └── IntegrationTest.java
```

## Layer Responsibilities

### 1. **Controller Layer** (`controller/`)
- **Purpose**: Handle HTTP requests and responses
- **Responsibilities**:
  - Parse incoming HTTP requests
  - Validate request data
  - Delegate business logic to services
  - Format and return HTTP responses
  - Handle routing via `@RequestMapping`
- **Technologies**: Spring Web, REST APIs, JSON marshalling

### 2. **Service Layer** (`service/`)
- **Purpose**: Implement core business logic
- **Responsibilities**:
  - User authentication & authorization
  - KYC verification workflows
  - Payment processing
  - Validation rules enforcement
  - LiveKit token generation
  - Chat & streaming service management
  - Transaction management
- **Technologies**: Spring Service, Transactions

### 3. **Repository Layer** (`repository/`)
- **Purpose**: Abstract database operations
- **Responsibilities**:
  - CRUD operations on entities
  - Custom database queries
  - Transaction management
  - Data persistence
- **Technologies**: Spring Data JPA, Hibernate

### 4. **Model Layer** (`model/`)

#### **Entities** (`model/entity/`)
- **Purpose**: Represent database tables
- **Mapping**: One-to-one with database schema
- **Technology**: JPA Annotations

#### **DTOs** (`model/dto/`)
- **Purpose**: Transfer data between layers
- **Responsibilities**:
  - Request/Response formatting
  - Data validation
  - Encapsulation of internal models
  - API contract definition
- **Advantages**: Decouples internal models from API contracts

#### **Enums** (`model/enum/`)
- **Purpose**: Define type-safe constants
- **Examples**: KycStatus, UserRole, TransactionType

### 5. **Config Layer** (`config/`)
- **Purpose**: Spring application configuration
- **Responsibilities**:
  - CORS configuration
  - Security bean setup
  - Database configuration
  - Third-party service integration
  - Property loading

### 6. **Security Layer** (`security/`)
- **Purpose**: Authentication & authorization
- **Responsibilities**:
  - JWT token generation/validation
  - User authentication
  - Permission checks
  - Encryption/Decryption

### 7. **Exception Layer** (`exception/`)
- **Purpose**: Custom exception handling
- **Responsibilities**:
  - Define domain-specific exceptions
  - Centralized error handling
  - Error response formatting
  - HTTP status code mapping

### 8. **Validation Layer** (`validation/`)
- **Purpose**: Business rule validation
- **Responsibilities**:
  - Age verification
  - Email validation
  - KYC requirements checking
  - Custom validators

### 9. **Middleware Layer** (`middleware/`)
- **Purpose**: Request/Response interceptors
- **Responsibilities**:
  - Request logging
  - Authentication filtering
  - Error handling
  - Response modification

### 10. **Messaging Layer** (`messaging/`)
- **Purpose**: WebSocket & real-time messaging
- **Responsibilities**:
  - WebSocket configuration
  - Chat message handling
  - Real-time notifications
  - Message persistence

### 11. **Streaming Layer** (`streaming/`)
- **Purpose**: LiveKit video streaming integration
- **Responsibilities**:
  - Room management
  - User role management (broadcaster/subscriber)
  - Stream event handling
  - Token validation

### 12. **Utility Layer** (`util/`)
- **Purpose**: Shared utility functions
- **Responsibilities**:
  - Cryptographic operations
  - JWT utilities
  - Date/time utilities
  - Response formatting

## Data Flow Architecture

```
HTTP Request
    ↓
[Controller Layer] - Parse & validate request
    ↓
[Service Layer] - Execute business logic
    ↓
[Repository Layer] - Perform database operations
    ↓
[Database]
    ↓
[Repository Layer] - Return data
    ↓
[Service Layer] - Process response
    ↓
[Controller Layer] - Format HTTP response
    ↓
HTTP Response
```

## Key Design Patterns

### 1. **Dependency Injection**
- Constructor injection for services
- Spring-managed beans
- Loose coupling between layers

### 2. **Repository Pattern**
- Abstraction of data access
- Easy to mock for testing
- Centralized query logic

### 3. **Service Layer Pattern**
- Business logic encapsulation
- Reusable service methods
- Transaction management

### 4. **DTO Pattern**
- API contract definition
- Internal model protection
- Easy versioning of APIs

### 5. **Exception Handling Pattern**
- Global exception handler
- Custom exception types
- Standardized error responses

## Security Considerations

- **JWT Authentication**: Token-based authentication for stateless API
- **CORS Configuration**: Controlled cross-origin requests
- **KYC Verification**: Adult age verification gateway
- **Payment Verification**: Deposit requirement for premium features
- **Encryption**: HMAC-SHA256 for token signing
- **Input Validation**: Request validation at controller level

## Configuration Profiles

- **dev**: Local development settings
- **prod**: Production environment settings
- Property-based configuration via `application.properties`

## Testing Strategy

```
Unit Tests
├── Service Layer Tests
├── Validation Tests
└── Utility Tests

Integration Tests
├── Controller + Service Tests
├── Repository Tests
└── End-to-end API Tests

Test Coverage Target: > 80%
```

## Development Workflow

1. **Create Entity** in `model/entity/`
2. **Create Repository** in `repository/`
3. **Create DTO** in `model/dto/`
4. **Create Service** in `service/` (business logic)
5. **Create Controller** in `controller/` (HTTP endpoints)
6. **Add Tests** in `src/test/`
7. **Update Config** if needed in `config/`

## Best Practices

✅ **DO**:
- Keep business logic in service layer
- Use DTOs for API contracts
- Implement proper exception handling
- Write unit tests for services
- Use dependency injection
- Keep controllers thin

❌ **DON'T**:
- Put business logic in controllers
- Use entities directly in API responses
- Ignore validation
- Create circular dependencies
- Use static utility methods excessively
- Skip error handling

## Common Endpoints Structure

```
/api/users              - User management
/api/auth               - Authentication
/api/kyc                - KYC verification
/api/transactions       - Payments & tokens
/api/chat               - Messaging
/api/streaming          - Video streaming
/api/profile            - User profile
```

## Dependencies (pom.xml)

- Spring Boot Web Starter
- Spring Data JPA
- Spring Security
- Hibernate Validator
- LiveKit SDK
- JWT (io.jsonwebtoken)
- Lombok (optional)
- MySQL/PostgreSQL driver
- JUnit 5 for testing

## Environment Variables Required

```
LIVEKIT_API_KEY=<your-api-key>
LIVEKIT_API_SECRET=<your-secret>
LIVEKIT_URL=<your-livekit-url>
JWT_SECRET=<your-jwt-secret>
DB_URL=<database-url>
DB_USERNAME=<db-user>
DB_PASSWORD=<db-password>
```

---

**Version**: 1.0  
**Last Updated**: 2026-07-16  
**Maintainers**: Development Team
