package com.sreebhargav.socialmedia.social_media_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sreebhargav.socialmedia")
public class SocialMediaAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaAppApplication.class, args);
    }
}
