package com.sreebhargav.socialmedia.social_media_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    // ✅ New Like Feature (Tracks Users Who Liked the Post)
    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();
    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }
    @Column(nullable = true)  
     private String imageUrl;

    

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
