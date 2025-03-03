package com.sreebhargav.socialmedia.social_media_app.controller;

import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.repository.PostRepository;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import com.sreebhargav.socialmedia.social_media_app.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;



    // 🔹 Create a new post
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    // 🔹 Upload an image to a post
    @PostMapping("/{postId}/upload")
    public ResponseEntity<?> uploadPostImage(@PathVariable Long postId,
                                             @RequestParam("file") MultipartFile file,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        try {
            String imageUrl = cloudinaryService.uploadFile(file);
            post.setImageUrl(imageUrl);
            postRepository.save(post);
            return ResponseEntity.ok("✅ Image uploaded successfully: " + imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
        }
    }
    // ✅ Get a single post by ID
        @GetMapping("/{postId}")
        public ResponseEntity<?> getPostById(@PathVariable Long postId) {
          Optional<Post> optionalPost = postRepository.findById(postId);
         if (optionalPost.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Post not found");
         }
          return ResponseEntity.ok(optionalPost.get());
         }


    // ✅ Edit a post (Only the owner can edit)
    @PutMapping("/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId,
                                      @RequestBody Post updatedPost,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only edit your own posts!");
        }

        post.setContent(updatedPost.getContent());
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    // ✅ Delete a post (Only the owner can delete)
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only delete your own posts!");
        }

        postRepository.delete(post);
        return ResponseEntity.ok("✅ Post deleted successfully");
    }

    // 🔹 Like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getLikedByUsers().contains(user)) {
            post.getLikedByUsers().add(user);
            postRepository.save(post);
            return ResponseEntity.ok("Post liked successfully");
        } else {
            return ResponseEntity.ok("Post already liked");
        }
    }
    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
       Page<Post> posts = postRepository.findAll(pageable);
       return ResponseEntity.ok(posts);
    }

    // 🔹 Unlike a post
    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: User not authenticated");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (post.getLikedByUsers().contains(user)) {
            post.getLikedByUsers().remove(user);
            postRepository.save(post);
            return ResponseEntity.ok("Post unliked successfully");
        } else {
            return ResponseEntity.ok("You have not liked this post yet");
        }
    }
}
