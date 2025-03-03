package com.sreebhargav.socialmedia.social_media_app.controller;

import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import com.sreebhargav.socialmedia.social_media_app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j  // ✅ Enables Logging
public class UserController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        log.info("🔍 Looking for user profile: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("❌ User not found: {}", username);
                    return new RuntimeException("User not found");
                });

        List<Post> posts = postRepository.findByUser(user);
        log.info("✅ Found user: {} with {} posts", user.getUsername(), posts.size());

        return ResponseEntity.ok(Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "posts", posts
        ));
    }
}
