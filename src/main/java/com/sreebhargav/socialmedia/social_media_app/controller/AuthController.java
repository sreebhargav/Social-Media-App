package com.sreebhargav.socialmedia.social_media_app.controller;

import com.sreebhargav.socialmedia.social_media_app.security.JwtUtil;
import com.sreebhargav.socialmedia.social_media_app.security.AuthService;
import com.sreebhargav.socialmedia.social_media_app.model.AuthRequest;
import com.sreebhargav.socialmedia.social_media_app.model.AuthResponse;
import com.sreebhargav.socialmedia.social_media_app.dto.RegisterRequest; // ✅ Import RegisterRequest
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/register")  // ✅ Add this to allow user registration
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            log.info("📝 Registering user: {}", request.getUsername());

            String token = authService.register(request);

            log.info("✅ Registration successful for user: {}", request.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            log.error("❌ Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            log.info("🔐 Attempting login for user: {}", request.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT Token
            String token = jwtUtil.generateToken(authentication.getName());

            log.info("✅ Login successful for user: {}", request.getUsername());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            log.error("❌ Login failed: {}", e.getMessage());
            return ResponseEntity.status(403).body("Authentication failed");
        }
    }
}
