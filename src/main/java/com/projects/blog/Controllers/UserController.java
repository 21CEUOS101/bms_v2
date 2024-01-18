package com.projects.blog.Controllers;

// imports
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.projects.blog.IServices.IBlogService;
import com.projects.blog.IServices.ICommentService;
import com.projects.blog.IServices.ILikeService;
import com.projects.blog.IServices.IUserService;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.Comments;
import com.projects.blog.Models.Likes;
import com.projects.blog.Models.User;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "Authorization" , methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api")
public class UserController {

    // necessary dependencies
    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // controllers

    @GetMapping("/current-user")
    public String currentUser(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        try{
            List<User> users = userService.getUsers();
            return users;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id) {
        try{
            User user = userService.getUser(id);
            return user;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/users/{id}/blogs")
    public List<Blog> getBlogsByUser(@PathVariable("id") String id) {
        try{
            List<Blog> blogs = blogService.getBlogsByUser(id);
            return blogs;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/users/{id}/likes")
    public List<Likes> getLikesByUser(@PathVariable("id") String id) {
        try{
            List<Likes> likes = likeService.getLikesByUser(id);
            return likes;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/users/{id}/comments")
    public List<Comments> getCommentsByUser(@PathVariable("id") String id) {
        try{
            List<Comments> comments = commentService.getCommentsByUser(id);
            return comments;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/users/uid")
    public List<String> getAllUId() {
        try{
            List<String> uList = userService.getAllUId();
            return uList;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PutMapping("/users/{id}")
    public User updateUser( @PathVariable("id") String id ,@RequestBody User user) {

        if(user == null)
        {
            System.out.println("User cannot be null");
            return null;
        }

        if(!user.getId().equals(id))
        {
            System.out.println("User id mismatch");
            return null;
        }

        User u = userService.getUser(id);
        u.setAbout(user.getAbout());
        u.setBlogs(user.getBlogs());
        u.setComments(user.getComments());
        u.setCreatedOn(user.getCreatedOn());
        u.setImage(user.getImage());
        u.setEmail(user.getEmail());
        u.setGender(user.getGender());
        u.setLikes(user.getLikes());
        u.setName(user.getName());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setRole(user.getRole());

        try{
            userService.updateUser(u);
            return u;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        try{
            userService.deleteUser(id);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
