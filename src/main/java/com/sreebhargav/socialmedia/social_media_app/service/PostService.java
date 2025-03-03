package com.sreebhargav.socialmedia.social_media_app.service;

import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import com.sreebhargav.socialmedia.social_media_app.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUser(user); // ✅ Use the correct method
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}