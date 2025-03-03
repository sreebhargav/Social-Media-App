package com.sreebhargav.socialmedia.social_media_app.service;

import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.repository.PostRepository;
import com.sreebhargav.socialmedia.social_media_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class LikeService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean likePost(Long postId, String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            // Ensure likedByUsers is initialized
            if (post.getLikedByUsers() == null) {
                post.setLikedByUsers(new HashSet<>());
            }

            if (!post.getLikedByUsers().contains(user)) {
                post.getLikedByUsers().add(user);
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean unlikePost(Long postId, String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Post> postOptional = postRepository.findById(postId);

        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();

            if (post.getLikedByUsers() != null && post.getLikedByUsers().contains(user)) {
                post.getLikedByUsers().remove(user);
                postRepository.save(post);
                return true;
            }
        }
        return false;
    }
}
