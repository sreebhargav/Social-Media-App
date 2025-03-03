package com.sreebhargav.socialmedia.social_media_app.repository;

import com.sreebhargav.socialmedia.social_media_app.model.Comment;
import com.sreebhargav.socialmedia.social_media_app.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);  // ✅ Use Post entity instead of Long postId
}
