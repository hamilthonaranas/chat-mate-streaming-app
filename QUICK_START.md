# ChattMate Architecture Guide

## Quick Start

### Directory Purpose Map

```
controller/     → REST API endpoints (@RestController)
service/        → Business logic (@Service)
repository/     → Database access (@Repository)
model/entity/   → Database entities (@Entity)
model/dto/      → Request/Response objects
model/enum/     → Enumerations
config/         → Spring configuration (@Configuration)
exception/      → Custom exceptions & handlers
validation/     → Business rule validators
security/       → Authentication & JWT
middleware/     → Filters & interceptors
messaging/      → WebSocket & real-time messaging
streaming/      → LiveKit integration
util/           → Utility functions
```

### Creating a New Feature - Step by Step

#### Step 1: Create Entity (model/entity/)
```java
@Entity
@Table(name = "your_table")
public class YourEntity {
    @Id private String id;
    // Properties...
}
```

#### Step 2: Create Repository (repository/)
```java
@Repository
public interface YourRepository extends JpaRepository<YourEntity, String> {
    // Custom queries...
}
```

#### Step 3: Create DTOs (model/dto/)
```java
public class YourEntityDTO {
    private String id;
    // Getters and Setters...
}
```

#### Step 4: Create Service (service/)
```java
@Service
public class YourService {
    @Autowired private YourRepository repository;
    
    public YourEntity create(YourEntity entity) {
        // Business logic...
        return repository.save(entity);
    }
}
```

#### Step 5: Create Controller (controller/)
```java
@RestController
@RequestMapping("/api/your")
public class YourController {
    @Autowired private YourService service;
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody YourEntity entity) {
        return ResponseEntity.ok(service.create(entity));
    }
}
```

#### Step 6: Add Tests (src/test/)
```java
@SpringBootTest
public class YourServiceTest {
    @Autowired private YourService service;
    
    @Test
    public void testCreate() {
        // Test logic...
    }
}
```

### Key Technologies

- **Spring Boot**: Framework
- **Spring Data JPA**: Database ORM
- **Hibernate**: JPA implementation
- **MySQL/PostgreSQL**: Database
- **JWT**: Authentication
- **LiveKit SDK**: Video streaming
- **WebSocket**: Real-time messaging
- **JUnit 5**: Testing

### API Response Format

All endpoints follow this response format:

**Success:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* response data */ }
}
```

**Error:**
```json
{
  "success": false,
  "error": "Error message",
  "timestamp": "2026-07-16T12:00:00"
}
```

### Dependency Injection Pattern

```java
// Constructor Injection (Preferred)
@Service
public class MyService {
    private final MyRepository repository;
    
    public MyService(MyRepository repository) {
        this.repository = repository;
    }
}

// Field Injection (Not preferred but acceptable)
@Service
public class MyService {
    @Autowired
    private MyRepository repository;
}
```

### Transaction Management

```java
@Service
public class MyService {
    @Transactional  // Read-write
    public void modifyData() {
        // Changes will be committed
    }
    
    @Transactional(readOnly = true)  // Read-only
    public Data fetchData() {
        return repository.findAll();
    }
}
```

### Exception Handling

```java
@PostMapping
public ResponseEntity<?> create(@RequestBody Entity entity) {
    if (entity == null) {
        throw new ValidationException("Entity cannot be null");
    }
    
    if (!userExists(entity.userId)) {
        throw new ResourceNotFoundException("User not found");
    }
    
    return ResponseEntity.ok(service.create(entity));
}
```

### Configuration Properties

Access via `@Value` annotation:
```java
@Service
public class MyService {
    @Value("${livekit.api.key}")
    private String apiKey;
}
```

---

**Documentation Version**: 1.0  
**Last Updated**: 2026-07-16
