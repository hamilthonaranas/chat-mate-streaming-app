package com.chattmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.persistence.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@SpringBootApplication
public class ChattMateApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChattMateApplication.class, args);
    }
}

@Configuration
class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Maps to your Next.js local frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

@Entity
@Table(name = "users")
class User {
    @Id
    private String id = UUID.randomUUID().toString();
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(unique = true, nullable = false)
    private String googleId;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    private String placeOfBirth;
    private String cityLocation;
    
    // Privacy and Preference Selections
    private boolean showFullName = false;
    private boolean showAge = false;
    private String interestedIn; // Man, Woman, Straight, Gay, Bisexual
    private boolean smoke = false;
    private boolean drink = false;
    
    // Guardrail validation metrics
    private boolean isVerifiedAdult = false;
    private boolean hasDeposited = false;
    private int tokenBalance = 0;
    
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus = KycStatus.PENDING;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getPlaceOfBirth() { return placeOfBirth; }
    public void setPlaceOfBirth(String placeOfBirth) { this.placeOfBirth = placeOfBirth; }
    public String getCityLocation() { return cityLocation; }
    public void setCityLocation(String cityLocation) { this.cityLocation = cityLocation; }
    public boolean isShowFullName() { return showFullName; }
    public void setShowFullName(boolean showFullName) { this.showFullName = showFullName; }
    public boolean isShowAge() { return showAge; }
    public void setShowAge(boolean showAge) { this.showAge = showAge; }
    public String getInterestedIn() { return interestedIn; }
    public void setInterestedIn(String interestedIn) { this.interestedIn = interestedIn; }
    public boolean isSmoke() { return smoke; }
    public void setSmoke(boolean smoke) { this.smoke = smoke; }
    public boolean isDrink() { return drink; }
    public void setDrink(boolean drink) { this.drink = drink; }
    public boolean isVerifiedAdult() { return isVerifiedAdult; }
    public void setVerifiedAdult(boolean verifiedAdult) { isVerifiedAdult = verifiedAdult; }
    public boolean isHasDeposited() { return hasDeposited; }
    public void setHasDeposited(boolean hasDeposited) { this.hasDeposited = hasDeposited; }
    public int getTokenBalance() { return tokenBalance; }
    public void setTokenBalance(int tokenBalance) { this.tokenBalance = tokenBalance; }
    public KycStatus getKycStatus() { return kycStatus; }
    public void setKycStatus(KycStatus kycStatus) { this.kycStatus = kycStatus; }
}

enum KycStatus {
    PENDING, APPROVED, REJECTED
}

@Repository
interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleId(String googleId);
}

@Service
class ValidationService {
    public boolean isEligibleForFeatures(User user) {
        // Core Gating Rule: Fully verified and has completed initial deposit
        return user != null && user.isVerifiedAdult() && user.isHasDeposited();
    }

    public int calculateAge(LocalDate dob) {
        if (dob == null) return 0;
        return Period.between(dob, LocalDate.now()).getYears();
    }
}

@Service
class LiveKitTokenService {
    
    // Cryptographically signs standard JWT tokens using native Java crypto packages
    public String generateToken(String room, String identity, boolean isBroadcaster) throws Exception {
        String apiKey = System.getenv("LIVEKIT_API_KEY") != null ? System.getenv("LIVEKIT_API_KEY") : "devkey";
        String apiSecret = System.getenv("LIVEKIT_API_SECRET") != null ? System.getenv("LIVEKIT_API_SECRET") : "secret";

        long nowSeconds = System.currentTimeMillis() / 1000;
        long expSeconds = nowSeconds + 7200; // valid for 2 hours

        // 1. Construct JWT Header
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        
        // 2. Construct LiveKit Specific Video Grant mapping
        String videoGrant = String.format(
            "{\"roomJoin\":true,\"room\":\"%s\",\"canPublish\":%b,\"canSubscribe\":true}",
            room, isBroadcaster
        );

        // 3. Construct Payload
        String payload = String.format(
            "{\"iss\":\"%s\",\"sub\":\"%s\",\"nbf\":%d,\"exp\":%d,\"video\":%s}",
            apiKey, identity, nowSeconds - 5, expSeconds, videoGrant
        );

        // 4. Encode Payload and Header components
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String signatureInput = encodedHeader + "." + encodedPayload;

        // 5. Sign via HMAC SHA256 using standard server secret key
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256HMAC.init(secretKeySpec);
        byte[] rawSignature = sha256HMAC.doFinal(signatureInput.getBytes(StandardCharsets.UTF_8));
        String encodedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(rawSignature);

        return signatureInput + "." + encodedSignature;
    }
}

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public UserController(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationData) {
        // Enforce basic programmatic age limits before processing registration
        int calculatedAge = validationService.calculateAge(registrationData.getDateOfBirth());
        if (calculatedAge < 18) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access Denied. You must be at least 18 years of age to access ChattMate."));
        }

        registrationData.setVerifiedAdult(false); // Awaits formal KYC validation step
        User savedUser = userRepository.save(registrationData);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable String id, @RequestBody User profileUpdates) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }

        User user = userOpt.get();

        // Enforce gating requirement rule
        if (!validationService.isEligibleForFeatures(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Verification and initial Cash-in required to update profile."));
        }

        // Apply changes safely
        user.setShowFullName(profileUpdates.isShowFullName());
        user.setShowAge(profileUpdates.isShowAge());
        user.setInterestedIn(profileUpdates.getInterestedIn());
        user.setSmoke(profileUpdates.isSmoke());
        user.setDrink(profileUpdates.isDrink());
        user.setCityLocation(profileUpdates.getCityLocation());

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("status", "Profile updated successfully"));
    }
}

@RestController
@RequestMapping("/api/kyc")
class KycWebhookController {

    private final UserRepository userRepository;

    public KycWebhookController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleKycUpdate(@RequestHeader("x-kyc-signature") String signature, 
                                             @RequestBody Map<String, Object> payload) {
        // 1. In production, verify the cryptographic signature sent from Sumsub/Smile Identity
        
        String userId = (String) payload.get("userId");
        String status = (String) payload.get("status"); // APPROVED or REJECTED

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        User user = userOpt.get();

        if ("APPROVED".equalsIgnoreCase(status)) {
            user.setVerifiedAdult(true);
            user.setKycStatus(KycStatus.APPROVED);
        } else {
            user.setVerifiedAdult(false);
            user.setKycStatus(KycStatus.REJECTED);
        }

        userRepository.save(user);
        return ResponseEntity.ok("Handled successfully");
    }
}

@RestController
@RequestMapping("/api/transactions")
class PaymentAndTokenController {

    private final UserRepository userRepository;
    private final LiveKitTokenService tokenService;

    public PaymentAndTokenController(UserRepository userRepository, LiveKitTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/cash-in")
    public ResponseEntity<?> cashInTokens(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("userId");
        int tokensPurchased = (Integer) payload.get("tokens");

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User profile not found");
        }

        User user = userOpt.get();
        
        // Simulates payment verification success webhook callback
        user.setTokenBalance(user.getTokenBalance() + tokensPurchased);
        user.setHasDeposited(true); // Gating flag is now true, unlocking profile edits

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/generate-webrtc-token")
    public ResponseEntity<?> generateLiveKitToken(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("userId");
        String room = (String) payload.get("roomName");
        boolean isBroadcaster = (Boolean) payload.get("isBroadcaster");

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Profile missing");
        }

        User user = userOpt.get();

        // Prevent broadcasting if the user has not completed the verification and cash-in checks
        if (isBroadcaster && (!user.isVerifiedAdult() || !user.isHasDeposited())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Broadcasting requires complete over-18 verification and initial token deposit.");
        }

        try {
            String token = tokenService.generateToken(room, user.getUsername(), isBroadcaster);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token signing error");
        }
    }
}
