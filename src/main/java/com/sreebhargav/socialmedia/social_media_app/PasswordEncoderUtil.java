package com.sreebhargav.socialmedia.social_media_app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("newpassword");
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
