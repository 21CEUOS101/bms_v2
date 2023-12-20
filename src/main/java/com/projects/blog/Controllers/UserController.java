package com.projects.blog.Controllers;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/current-user")
    public String currentUser(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @GetMapping("/users/{id}/blogs")
    public List<Blog> getBlogsByUser(@PathVariable("id") String id) {
        return blogService.getBlogsByUser(id);
    }

    @GetMapping("/users/{id}/likes")
    public List<Likes> getLikesByUser(@PathVariable("id") String id) {
        return likeService.getLikesByUser(id);
    }

    @GetMapping("/users/{id}/comments")
    public List<Comments> getCommentsByUser(@PathVariable("id") String id) {
        return commentService.getCommentsByUser(id);
    }

    @GetMapping("/users/uid")
    public List<String> getAllUId() {
        return userService.getAllUId();
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
    
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}
