package com.sreebhargav.socialmedia.social_media_app.controller;

import com.sreebhargav.socialmedia.social_media_app.model.Comment;
import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.repository.CommentRepository;
import com.sreebhargav.socialmedia.social_media_app.repository.PostRepository;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 🔹 Create a comment
    @PostMapping
    public ResponseEntity<?> addComment(
        @PathVariable Long postId,
        @RequestBody Comment comment,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Unauthorized: User not authenticated");
        }

        // 🔹 Find the user
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("❌ User not found"));

        // 🔹 Find the post
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Post not found");
        }
        
        Post post = optionalPost.get();
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    // 🔹 Get all comments for a specific post
    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Post not found");
        }

        List<Comment> comments = commentRepository.findByPost(optionalPost.get());
        return ResponseEntity.ok(comments);
    }

    // 🔹 Delete a comment (Only owner can delete)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Unauthorized: User not authenticated");
        }

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Comment not found");
        }

        Comment comment = optionalComment.get();
        if (!comment.getUser().getUsername().equals(userDetails.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("❌ Forbidden: You can only delete your own comments");
        }

        commentRepository.delete(comment);
        return ResponseEntity.ok("✅ Comment deleted successfully");
    }

    // 🔹 Like a comment
    @PostMapping("/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Unauthorized: User not authenticated");
        }

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Comment not found");
        }

        Comment comment = optionalComment.get();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("❌ User not found"));

        // Add like logic (You might need to modify Comment entity to have a Set<User> likedByUsers)
        if (!comment.getLikedByUsers().contains(user)) {
            comment.getLikedByUsers().add(user);
            commentRepository.save(comment);
            return ResponseEntity.ok("✅ Comment liked!");
        }

        return ResponseEntity.ok("ℹ You already liked this comment");
    }

    @DeleteMapping("/{commentId}/unlike")
public ResponseEntity<?> unlikeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Unauthorized: User not authenticated");
    }

    Optional<Comment> optionalComment = commentRepository.findById(commentId);
    if (optionalComment.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Comment not found");
    }

    Comment comment = optionalComment.get();
    User user = userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("❌ User not found"));

    if (comment.getLikedByUsers().contains(user)) {
        comment.getLikedByUsers().remove(user);
        commentRepository.save(comment);
        return ResponseEntity.ok("✅ Comment unliked!");
    } else {
        return ResponseEntity.ok("ℹ You haven't liked this comment yet!");
    }
}

}
