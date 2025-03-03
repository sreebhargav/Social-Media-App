package com.sreebhargav.socialmedia.social_media_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.model.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // ✅ Get posts created by a specific user
    List<Post> findByUser(User user);

    // ✅ Get posts liked by a specific user
    List<Post> findByLikedByUsers(User user);
}
