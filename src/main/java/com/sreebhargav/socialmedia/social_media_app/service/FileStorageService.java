package com.sreebhargav.socialmedia.social_media_app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    public String uploadFile(MultipartFile file) {
        // Upload file to AWS S3 or Cloudinary
        // Placeholder logic: Return file URL
        return "https://your-storage-url.com/" + file.getOriginalFilename();
    }
}
