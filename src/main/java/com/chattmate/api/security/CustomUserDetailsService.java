package com.chattmate.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chattmate.api.model.entity.User;
import com.chattmate.api.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Custom User Details Service
 * Loads user details from database for Spring Security
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        User user = userOpt.get();
        
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        if (user.isVerifiedAdult()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_VERIFIED"));
        }
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            "", // Password not stored in plaintext - using JWT instead
            true,
            true,
            true,
            true,
            authorities
        );
    }
}
