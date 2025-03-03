package com.sreebhargav.socialmedia.social_media_app.service;

import com.sreebhargav.socialmedia.social_media_app.model.Comment;
import com.sreebhargav.socialmedia.social_media_app.model.Post;
import com.sreebhargav.socialmedia.social_media_app.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post); // ✅ Use the correct method
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
