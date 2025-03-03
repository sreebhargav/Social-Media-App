# 📸 Social Media App - Spring Boot & Cloudinary

A **full-stack social media platform** built using **Spring Boot, Spring Security, JWT, PostgreSQL, and Cloudinary**. This project enables **user authentication, post creation, likes, comments, and media uploads** (Cloudinary). 

---

## 🚀 Features

✅ **User Authentication**  
   - User registration (email, username, password)  
   - JWT-based authentication (Spring Security)  
   - Secure login & logout  

✅ **Posts & Content**  
   - Create, edit, and delete posts  
   - Upload images/videos (Cloudinary)  
   - Like and comment on posts  
   - Real-time dashboard displaying all posts (pagination enabled)  

✅ **Security & Authorization**  
   - JWT authentication with role-based access control  
   - Only post owners can edit/delete their posts  
   - Protected API routes  

✅ **Database & Storage**  
   - PostgreSQL for data persistence  
   - Cloudinary for image hosting  

✅ **REST API Endpoints**  
   - Fully functional API to integrate with a frontend  
   - Pagination for optimized post retrieval  

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT, Hibernate, PostgreSQL  
- **Storage**: Cloudinary API for media uploads  
- **Security**: BCrypt for password hashing, JWT for authentication  
- **Build Tool**: Maven  
- **Version Control**: Git, GitHub  
- **Deployment**: TBD  

---

## 📂 Project Structure

social-media-app │── src/main/java/com/sreebhargav/socialmedia/social_media_app │
                 ├── controller # REST API Controllers │ 
                 ├── model # JPA Entities (User, Post, Comment, etc.) │
                 ├── repository # Spring Data JPA Repositories │ 
                 ├── service # Business logic services │ 
                 ├── security # JWT and authentication config │
                 ├── SocialMediaAppApplication.java # Main application entry point 
                 │── src/main/resources │ 
                 ├── application.properties # Database & Cloudinary configuration 
                 │── pom.xml # Maven dependencies 
                 │── README.md # Project documentation

yaml
Copy
Edit

---

## 🚀 Getting Started

### 1. Clone the Repository  
```sh
git clone https://github.com/sreebhargav/social-media-app.git
cd social-media-app

2. Configure Cloudinary & Database
Create a Cloudinary account and get your API keys.
Update application.properties with:
properties
Copy
Edit
cloudinary.cloud_name=your_cloud_name
cloudinary.api_key=your_api_key
cloudinary.api_secret=your_api_secret
spring.datasource.url=jdbc:postgresql://localhost:5432/socialmedia
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password


3. Build & Run the Application
sh
Copy
Edit
mvn clean install
mvn spring-boot:run


📌 API Endpoints
🔐 Authentication
POST /auth/register - Register a new user
POST /auth/login - Authenticate & get JWT


📝 Posts
GET /posts?page=0&size=5 - Get all posts (paginated)
POST /posts - Create a new post
PUT /posts/{postId} - Edit a post
DELETE /posts/{postId} - Delete a post


❤️ Likes & 💬 Comments
POST /posts/{postId}/like - Like a post
DELETE /posts/{postId}/unlike - Unlike a post
POST /posts/{postId}/comment - Add a comment
DELETE /comments/{commentId} - Delete a comment
📸 Media Upload
POST /posts/{postId}/upload - Upload an image to a post (Cloudinary)


📝 License
This project is open-source and free to use. Contributions are welcome! 🚀

⭐ Star this repo if you found it useful!
🛠️ Future Enhancements
✅ Deploy to AWS EC2 with CI/CD
✅ Frontend (React) Integration
✅ Notifications & Follow System

📝 License
This project is open-source and free to use. Contributions are welcome!

📬 Contact
🔹 Sree Bhargav Balusu
🔹 GitHub: sreebhargav
🔹 Email: sreebhargavbalusu@gmail.com

⭐ Star this repo if you found it useful!


### 📌 How to Push This to GitHub

After copying this `README.md`, run the following commands in your **project root directory**:

```sh
git add README.md
git commit -m "Added project README"
git push origin main

