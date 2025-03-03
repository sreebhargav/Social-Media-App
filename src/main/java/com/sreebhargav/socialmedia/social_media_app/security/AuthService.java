package com.sreebhargav.socialmedia.social_media_app.security;

import com.sreebhargav.socialmedia.social_media_app.dto.RegisterRequest;
import com.sreebhargav.socialmedia.social_media_app.dto.AuthenticationRequest;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.model.Role;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("❌ Username already taken!");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("❌ Email already registered!");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        log.info("🔑 Hashed password for {}: {}", request.getUsername(), hashedPassword);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        log.info("✅ User registered: {}, ID: {}", savedUser.getUsername(), savedUser.getId());

        return jwtUtil.generateToken(user.getUsername());
    }

    public String authenticate(AuthenticationRequest request) {
        log.info("🔐 Attempting login for user: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("❌ User not found"));

        log.info("📌 Stored password hash: {}", user.getPassword());
        log.info("📌 Entered password: {}", request.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("❌ Password does not match for user: {}", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            log.info("✅ Authentication successful for user: {}", request.getUsername());

            String token = jwtUtil.generateToken(user.getUsername());
            log.info("🎫 Token generated for {}: {}", request.getUsername(), token);

            return token;
        } catch (BadCredentialsException e) {
            log.error("❌ Invalid username or password");
            throw new IllegalArgumentException("Invalid username or password");
        } catch (AuthenticationException e) {
            log.error("❌ Authentication failed: {}", e.getMessage());
            throw new IllegalArgumentException("Authentication failed: " + e.getMessage());
        }
    }
}
