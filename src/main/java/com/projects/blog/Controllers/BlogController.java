package com.projects.blog.Controllers;

// imports
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.projects.blog.IServices.IBlogService;
import com.projects.blog.IServices.ICommentService;
import com.projects.blog.IServices.ILikeService;
import com.projects.blog.IServices.IUserService;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.Comments;
import com.projects.blog.Models.Likes;
import com.projects.blog.Models.User;
import com.projects.blog.Requests.BlogRequest;
import com.projects.blog.Requests.CommentRequest;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "Authorization" , methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api")
public class BlogController {

    // necessary dependencies
    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private ICommentService commentService;

    // controllers

    @GetMapping("/recent-blogs")
    public List<Blog> getRecentBlogs() {
        try{
            List<Blog> blogs = blogService.getRecentBlogs();
            return blogs;
        }
        catch(Exception e){
            throw new RuntimeException("No blogs found");
        }
    }

    @GetMapping("/blogs")
    public List<Blog> getBlogs() {
        try{
            List<Blog> blogs = blogService.getBlogs();
            return blogs;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/blogs/{id}")
    public Blog getBlog(@PathVariable("id") String id) {
        try{
            Blog blog = blogService.getBlog(id);
            return blog;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    @GetMapping("/blogs/{id}/likes")
    public List<Likes> getLikesByBlog(@PathVariable("id") String id) {
        try{
            List<Likes> likes = likeService.getLikesByBlog(id);
            return likes;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/blogs/{id}/comments")
    public List<Comments> getCommentsByBlog(@PathVariable("id") String id) {
        
        try{
            List<Comments> comments = commentService.getCommentsByBlog(id);
            return comments;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @GetMapping("/blogs/{id}/user")
    public User getUserByBlog(@PathVariable("id") String id) {
        
        try{
            User user = blogService.findUserByBlog(id);
            return user;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/blogs/categories")
    public List<String> getAllCategories() {
        try{
            List<String> categories = blogService.getAllCategories();
            return categories;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/blogs")
    public Blog createBlog(
            @RequestBody BlogRequest blogRequest
    ) 
    {
        String userId = blogRequest.getUserId();
        String title = blogRequest.getTitle();
        String content = blogRequest.getContent();
        String slug = blogRequest.getSlug();
        String excerpt = blogRequest.getExcerpt();
        String category = blogRequest.getCategory();
        List<String> tags = blogRequest.getTags();
        String image = blogRequest.getImage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        User user = userService.getUser(userId);
        Blog blog = new Blog();
        blog.setId(userId + "_" + System.currentTimeMillis());
        blog.setTitle(title);
        blog.setContent(content);
        blog.setAuthor(user.getId());
        blog.setSlug(slug);
        blog.setCategory(category);
        blog.setTags(tags);
        blog.setImage(image);
        blog.setUpdatedOn(null);
        blog.setCreatedOn(LocalDate.now().format(formatter));
        blog.setExcerpt(excerpt);
        
        try{
            blogService.createBlog(blog);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
         
        return blog;
    }

    @PutMapping("/blogs/{id}")
    public Blog updateBlog(
            @PathVariable("id") String blogId,
            @RequestBody BlogRequest blogRequest
    ) 
    {
        String userId = blogRequest.getUserId();

        if(!userId.equals(blogService.getBlog(blogId).getAuthor()))
            throw new RuntimeException("User not authorized to update blog");

        String title = blogRequest.getTitle();
        String content = blogRequest.getContent();
        String slug = blogRequest.getSlug();
        String excerpt = blogRequest.getExcerpt();
        String category = blogRequest.getCategory();
        List<String> tags = blogRequest.getTags();
        String image = blogRequest.getImage();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Blog blog = blogService.getBlog(blogId);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setSlug(slug);
        blog.setCategory(category);
        blog.setTags(tags);
        blog.setImage(image);
        blog.setUpdatedOn(LocalDate.now().format(formatter));
        blog.setExcerpt(excerpt);
        
        try{
            blogService.updateBlog(blog);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return blog;
    }

    @PostMapping("/blogs/{id}/like")
    public void likeBlog(@PathVariable("id") String id, @RequestParam("userId") String userId) {
        try{
            likeService.likeBlog(id, userId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/blogs/{id}/unlike")
    public void unlikeBlog(@PathVariable("id") String id, @RequestParam("userId") String userId) {
        try{
            likeService.unlikeBlog(id, userId);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/blogs/{id}/comment")
    public Comments commentBlog(
            @PathVariable("id") String id,
            @RequestBody CommentRequest commentRequest
    ) 
    {
        String content = commentRequest.getComments();
        String blogId = id;
        
        if(!blogId.equals(blogService.getBlog(blogId).getId()))
            throw new RuntimeException("Blog not authorized to comment on blog");
        
        String userId = commentRequest.getUserId();

        if(!userId.equals(blogService.getBlog(blogId).getAuthor()))
            throw new RuntimeException("User not authorized to comment on blog");
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        User user = userService.getUser(userId);
        Blog blog = blogService.getBlog(blogId);
        Comments comment = new Comments();
        comment.setId(userId + blogId + "_" + System.currentTimeMillis());
        comment.setContent(content);
        comment.setBlog(blog.getId());
        comment.setUser(user.getId());
        comment.setCreatedOn(LocalDate.now().format(formatter));
        
        try{
            commentService.createComment(comment);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return comment;
    }

    @PutMapping("/blogs/{id}/comment/{cid}")
    public Comments updateComment(
            @PathVariable("cid") String cid,
            @PathVariable("id") String id,
            @RequestBody CommentRequest commentRequest
    ) 
    {
        String userId = commentRequest.getUserId();
        String content = commentRequest.getComments();
        String blogId = id;

        if(!userId.equals(commentService.getComment(cid).getUser()))
            throw new RuntimeException("User not authorized to update comment");

        if(!blogId.equals(commentService.getComment(cid).getBlog()))
            throw new RuntimeException("Blog not authorized to update comment");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Comments comment = commentService.getComment(cid);
        comment.setContent(content);
        comment.setUpdatedOn(LocalDate.now().format(formatter));
        
        try{
            commentService.updateComment(comment);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return comment;
    }

    @DeleteMapping("/blogs/{id}/comment")
    public void deleteComment(@PathVariable("id") String id) {
        try{
            commentService.deleteComment(id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping("/blogs/{id}")
    public void deleteBlog(@PathVariable("id") String id) {
        try{
            blogService.deleteBlog(id);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
