# Component Architecture Overview

## Project Structure

```
chat-mate-streaming-app/
│
├── src/
│   ├── main/
│   │   ├── java/com/chattmate/api/
│   │   │   ├── ChattMateApplication.java ........................... Main entry point
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java .......................... User management endpoints
│   │   │   │   ├── AuthController.java .......................... Authentication endpoints
│   │   │   │   ├── KycController.java ........................... KYC verification endpoints
│   │   │   │   ├── PaymentController.java ....................... Payment endpoints
│   │   │   │   ├── StreamingController.java ..................... Streaming endpoints
│   │   │   │   └── ChatController.java .......................... Chat messaging endpoints
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── UserService.java ............................. User business logic
│   │   │   │   ├── AuthenticationService.java .................. Auth logic
│   │   │   │   ├── ValidationService.java ....................... Validation rules
│   │   │   │   ├── KycService.java ............................. KYC service logic
│   │   │   │   ├── PaymentService.java ......................... Payment processing
│   │   │   │   ├── LiveKitTokenService.java .................... Token generation
│   │   │   │   ├── StreamingService.java ....................... Streaming logic
│   │   │   │   └── ChatService.java ............................ Chat logic
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java ......................... User data access
│   │   │   │   ├── ChatRepository.java ......................... Chat data access
│   │   │   │   ├── StreamRepository.java ....................... Stream data access
│   │   │   │   ├── TransactionRepository.java .................. Transaction data access
│   │   │   │   └── KycRecordRepository.java .................... KYC record data access
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── entity/
│   │   │   │   │   ├── User.java .............................. User entity
│   │   │   │   │   ├── Chat.java .............................. Chat entity
│   │   │   │   │   ├── Stream.java ............................ Stream entity
│   │   │   │   │   ├── Transaction.java ....................... Transaction entity
│   │   │   │   │   └── KycRecord.java ......................... KYC record entity
│   │   │   │   │
│   │   │   │   ├── dto/
│   │   │   │   │   ├── UserDTO.java ........................... User DTO
│   │   │   │   │   ├── LoginRequest.java ...................... Login request
│   │   │   │   │   ├── RegisterRequest.java ................... Register request
│   │   │   │   │   ├── ProfileUpdateRequest.java .............. Profile update
│   │   │   │   │   ├── ChatMessageDTO.java .................... Chat message DTO
│   │   │   │   │   └── StreamingTokenRequest.java ............. Token request
│   │   │   │   │
│   │   │   │   └── enum/
│   │   │   │       ├── KycStatus.java ......................... KYC status enum
│   │   │   │       ├── UserRole.java .......................... User role enum
│   │   │   │       ├── TransactionType.java ................... Transaction type enum
│   │   │   │       └── StreamStatus.java ...................... Stream status enum
│   │   │   │
│   │   │   ├── config/
│   │   │   │   ├── CorsConfig.java ........................... CORS configuration
│   │   │   │   ├── JpaConfig.java ............................ JPA configuration
│   │   │   │   ├── SecurityConfig.java ....................... Security configuration
│   │   │   │   └── LiveKitConfig.java ........................ LiveKit configuration
│   │   │   │
│   │   │   ├── security/
│   │   │   │   ├── JwtTokenProvider.java ..................... JWT token provider
│   │   │   │   ├── CustomUserDetailsService.java ............ User details service
│   │   │   │   └── SecurityUtil.java ......................... Security utilities
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java .............. Exception handler
│   │   │   │   ├── ResourceNotFoundException.java ........... 404 exception
│   │   │   │   ├── ValidationException.java ................. Validation exception
│   │   │   │   └── UnauthorizedException.java ............... 401 exception
│   │   │   │
│   │   │   ├── validation/
│   │   │   │   ├── AgeValidator.java ......................... Age validation
│   │   │   │   ├── EmailValidator.java ....................... Email validation
│   │   │   │   └── KycValidator.java ......................... KYC validation
│   │   │   │
│   │   │   ├── middleware/
│   │   │   │   ├── RequestLoggingFilter.java ................ Request logging
│   │   │   │   ├── AuthenticationFilter.java ................ Auth filter
│   │   │   │   └── ErrorHandlingFilter.java ................. Error handling
│   │   │   │
│   │   │   ├── messaging/
│   │   │   │   ├── WebSocketConfig.java ..................... WebSocket config
│   │   │   │   ├── ChatWebSocketHandler.java ................ WebSocket handler
│   │   │   │   └── MessageService.java ....................... Message service
│   │   │   │
│   │   │   ├── streaming/
│   │   │   │   ├── StreamingService.java .................... Streaming logic
│   │   │   │   ├── RoomManagementService.java ............... Room management
│   │   │   │   └── StreamEventListener.java ................. Event listener
│   │   │   │
│   │   │   └── util/
│   │   │       ├── CryptoUtil.java ........................... Crypto utilities
│   │   │       ├── JwtUtil.java ............................. JWT utilities
│   │   │       ├── DateUtil.java ............................ Date utilities
│   │   │       └── ResponseUtil.java ........................ Response utilities
│   │   │
│   │   └── resources/
│   │       ├── application.properties ........................ Main config
│   │       ├── application-dev.properties ................... Dev config
│   │       ├── application-prod.properties .................. Prod config
│   │       └── db/
│   │           └── migration/
│   │               ├── V1__initial_schema.sql
│   │               └── V2__add_tables.sql
│   │
│   └── test/
│       └── java/com/chattmate/api/
│           ├── controller/
│           │   ├── UserControllerTest.java
│           │   └── AuthControllerTest.java
│           ├── service/
│           │   ├── UserServiceTest.java
│           │   └── ValidationServiceTest.java
│           └── integration/
│               └── IntegrationTest.java
│
├── pom.xml .................................................... Maven configuration
├── ARCHITECTURE.md ............................................ Full documentation
├── QUICK_START.md ............................................. Quick start guide
├── check-architecture.sh ...................................... Verification script
├── .gitignore ................................................. Git ignore file
├── README.md .................................................. Project README
└── docker-compose.yml ......................................... Docker composition

```

## Component Layers

### 1. **Controller Layer** (Presentation)
- Handles HTTP requests/responses
- Input validation
- Request routing
- Response formatting
- **Examples**: UserController, AuthController

### 2. **Service Layer** (Business Logic)
- Core business rules
- Transaction management
- Data processing
- **Examples**: ValidationService, LiveKitTokenService

### 3. **Repository Layer** (Data Access)
- Database queries
- CRUD operations
- JPA/Hibernate abstraction
- **Examples**: UserRepository, ChatRepository

### 4. **Model Layer** (Data Objects)
- **Entities**: Database mappings (@Entity)
- **DTOs**: Request/Response objects
- **Enums**: Type-safe constants

### 5. **Configuration Layer**
- Spring bean configuration
- Third-party service setup
- **Examples**: CorsConfig, SecurityConfig

### 6. **Security Layer**
- Authentication & authorization
- JWT token handling
- **Examples**: JwtTokenProvider

### 7. **Exception Layer**
- Custom exceptions
- Global exception handling
- Error response formatting

### 8. **Utility Layer**
- Helper functions
- Crypto operations
- **Examples**: ResponseUtil, CryptoUtil

## API Endpoints Structure

```
/api/users              → User management
/api/auth               → Authentication
/api/kyc                → KYC verification
/api/transactions       → Payments & tokens
/api/chat               → Messaging
/api/streaming          → Video streaming
/api/profile            → User profile
```

## Development Commands

```bash
# Build project
mvn clean install

# Run tests
mvn test

# Run application
mvn spring-boot:run

# Build JAR
mvn clean package

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Verify architecture
./check-architecture.sh
```

## Next Steps

1. **Implement Services**: Complete business logic in service layer
2. **Add DTOs**: Create request/response objects for all endpoints
3. **Write Tests**: Unit and integration tests for each component
4. **Database**: Configure MySQL/PostgreSQL connection
5. **Authentication**: Implement JWT security
6. **Documentation**: API docs via Swagger/OpenAPI

---

**Generated**: 2026-07-16  
**Version**: 1.0
