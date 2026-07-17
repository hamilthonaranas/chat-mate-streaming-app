# ChattMate Spring Boot Application
## Complete Backend Architecture

A production-ready Spring Boot application for video streaming and real-time chat platform.

### Features
✅ User Management & Authentication (JWT)  
✅ KYC Verification Integration  
✅ Payment Processing with Token System  
✅ LiveKit Video Streaming Integration  
✅ WebSocket Real-time Messaging  
✅ Role-based Access Control  
✅ Comprehensive Error Handling  
✅ Database Migrations  
✅ API Documentation (Swagger/OpenAPI)  

### Tech Stack
- **Framework**: Spring Boot 3.3.0
- **Language**: Java 17
- **Database**: MySQL 8.0 / PostgreSQL
- **Authentication**: JWT
- **Video Streaming**: LiveKit SDK
- **Real-time Chat**: WebSocket
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito

### Project Structure

```
src/main/java/com/chattmate/api/
├── controller/          → REST API endpoints
├── service/             → Business logic
├── repository/          → Data access layer
├── model/
│   ├── entity/         → JPA entities
│   ├── dto/            → Data transfer objects
│   └── enum/           → Enumerations
├── config/             → Spring configuration
├── security/           → JWT & authentication
├── exception/          → Error handling
├── validation/         → Business validators
├── middleware/         → Filters & interceptors
├── messaging/          → WebSocket config
├── streaming/          → LiveKit integration
└── util/               → Utility functions
```

### Quick Start

#### Prerequisites
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+ or PostgreSQL
- LiveKit server (optional for streaming)

#### Installation

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/chat-mate-streaming-app.git
cd chat-mate-streaming-app
```

2. **Configure Database**
```bash
# Create MySQL database
mysql -u root -p
CREATE DATABASE chattmate_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Set Environment Variables**
```bash
export DB_URL=jdbc:mysql://localhost:3306/chattmate_db
export DB_USERNAME=root
export DB_PASSWORD=your_password
export LIVEKIT_API_KEY=your_api_key
export LIVEKIT_API_SECRET=your_secret
export JWT_SECRET=your_jwt_secret
```

4. **Build & Run**
```bash
# Build
mvn clean install

# Run with development profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Run with production profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

5. **Access API**
```
API Base URL: http://localhost:8080/api
Swagger UI: http://localhost:8080/swagger-ui.html
```

### API Endpoints

#### Authentication
```
POST   /api/auth/login              → User login
POST   /api/auth/register           → New user registration
POST   /api/auth/refresh-token      → Refresh JWT token
GET    /api/auth/validate-token     → Validate token
```

#### User Management
```
GET    /api/users/{id}              → Get user profile
POST   /api/users/register          → Register user
PUT    /api/users/{id}/profile      → Update profile
GET    /api/users/{id}/stats        → Get user statistics
```

#### KYC Verification
```
POST   /api/kyc/initiate            → Start KYC process
GET    /api/kyc/{id}/status         → Get KYC status
POST   /api/kyc/webhook             → KYC provider webhook
```

#### Payments & Tokens
```
POST   /api/transactions/cash-in    → Purchase tokens
GET    /api/transactions/{id}       → Get transaction history
POST   /api/transactions/transfer   → Transfer tokens
```

#### Streaming
```
POST   /api/streaming/room/create   → Create live room
GET    /api/streaming/room/{id}     → Get room info
POST   /api/streaming/join          → Join live stream
DELETE /api/streaming/room/{id}     → End live stream
```

#### Messaging
```
WS     /ws/chat/{roomId}            → WebSocket chat connection
POST   /api/chat/send               → Send message
GET    /api/chat/history/{roomId}   → Get chat history
```

### Environment Profiles

#### Development
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

#### Production
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

### Configuration Files

- `application.properties` - Main configuration
- `application-dev.properties` - Development settings
- `application-prod.properties` - Production settings

### Database Schema

Key tables:
- `users` - User profiles and verification status
- `kyc_records` - KYC verification data
- `transactions` - Payment transactions
- `chat_messages` - Chat message history
- `streams` - Live stream records

### Security Features

✅ **JWT Authentication** - Stateless token-based auth  
✅ **CORS Configuration** - Controlled cross-origin requests  
✅ **Password Encryption** - Bcrypt hashing  
✅ **Input Validation** - Request validation at controller layer  
✅ **Role-based Access** - User & Admin roles  
✅ **Token Signing** - HMAC-SHA256 for LiveKit tokens  
✅ **Secure Headers** - Security headers in responses  

### Testing

Run unit tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

Test coverage report:
```bash
mvn test jacoco:report
```

### Docker Deployment

Build image:
```bash
docker build -t chattmate-api:latest .
```

Run container:
```bash
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://db:3306/chattmate_db \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=password \
  chattmate-api:latest
```

### Documentation

- [Architecture Guide](ARCHITECTURE.md) - Complete architecture documentation
- [Quick Start Guide](QUICK_START.md) - Detailed setup instructions
- [Project Structure](PROJECT_STRUCTURE.md) - Directory structure details
- [API Documentation](http://localhost:8080/swagger-ui.html) - Interactive API docs

### Common Issues & Solutions

**Issue**: Database connection error
```
Solution: Ensure MySQL is running and credentials are correct in environment variables
```

**Issue**: JWT token expired
```
Solution: Call /api/auth/refresh-token to get new token
```

**Issue**: LiveKit token generation fails
```
Solution: Verify LIVEKIT_API_KEY and LIVEKIT_API_SECRET are set correctly
```

### Performance Optimization

- Connection pooling (HikariCP)
- Query optimization with indexes
- Caching strategies
- Async request processing
- Database migration versioning

### Monitoring & Logging

- Spring Boot Actuator for metrics
- SLF4J with Logback logging
- Request/response logging
- Error tracking

### Future Enhancements

- [ ] Message encryption
- [ ] Advanced analytics
- [ ] AI-powered recommendations
- [ ] Multi-language support
- [ ] Mobile push notifications
- [ ] Rate limiting & throttling

### Contributing

1. Create feature branch
2. Make changes
3. Write tests
4. Submit pull request

### Support

For issues and questions:
- Create GitHub issue
- Contact: support@chattmate.com
- Documentation: docs.chattmate.com

### License

This project is licensed under the MIT License - see LICENSE file for details

---

**Version**: 1.0.0  
**Last Updated**: 2026-07-16  
**Maintainer**: Development Team
