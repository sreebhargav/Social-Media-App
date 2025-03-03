package com.sreebhargav.socialmedia.social_media_app.repository;

import com.sreebhargav.socialmedia.social_media_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // ✅ FIXED!
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
