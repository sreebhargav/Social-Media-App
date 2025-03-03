package com.sreebhargav.socialmedia.social_media_app.security;

import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("🔍 Looking for user: {}", username);

        // Ensure case-insensitive lookup
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("❌ User not found in database: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        log.info("✅ User found: {} with role {}", user.getUsername(), user.getRole());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // ✅ Ensure password is included
                .authorities(Collections.emptyList()) // You can add roles if needed
                .build();
    }
}
