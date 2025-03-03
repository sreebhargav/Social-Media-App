package com.sreebhargav.socialmedia.social_media_app;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class PasswordChecker {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches("newpassword", "PASTE_HASHED_PASSWORD_HERE");
        System.out.println("Password match: " + matches);
    }
}
