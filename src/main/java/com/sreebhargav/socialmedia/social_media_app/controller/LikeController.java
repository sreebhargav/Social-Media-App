package com.sreebhargav.socialmedia.social_media_app.controller;

import com.sreebhargav.socialmedia.social_media_app.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, Authentication authentication) {
        String username = authentication.getName();
        boolean liked = likeService.likePost(postId, username);
        return liked ? ResponseEntity.ok("Post liked!") : ResponseEntity.badRequest().body("Already liked");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, Authentication authentication) {
        String username = authentication.getName();
        boolean unliked = likeService.unlikePost(postId, username);
        return unliked ? ResponseEntity.ok("Post unliked!") : ResponseEntity.badRequest().body("Not liked yet");
    }
}
