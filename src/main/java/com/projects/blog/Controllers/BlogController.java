package com.projects.blog.Controllers;
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

    @Autowired
    private IUserService userService;

    @Autowired
    private IBlogService blogService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private ICommentService commentService;

    @GetMapping("/recent-blogs")
    public List<Blog> getRecentBlogs() {
        return blogService.getRecentBlogs();
    }

    @GetMapping("/blogs")
    public List<Blog> getBlogs() {
        List<Blog> blogs = blogService.getBlogs();

        return blogs;
    }

    @GetMapping("/blogs/{id}")
    public Blog getBlog(@PathVariable("id") String id) {
        return blogService.getBlog(id);
    }
    
    @GetMapping("/blogs/{id}/likes")
    public List<Likes> getLikesByBlog(@PathVariable("id") String id) {
        return likeService.getLikesByBlog(id);
    }

    @GetMapping("/blogs/{id}/comments")
    public List<Comments> getCommentsByBlog(@PathVariable("id") String id) {
        List<Comments> comments = commentService.getCommentsByBlog(id);

        if(comments.size() == 0)
            System.out.println("No comments found");
        
        return comments;
    }

    @GetMapping("/blogs/{id}/user")
    public User getUserByBlog(@PathVariable("id") String id) {
        return blogService.findUserByBlog(id);
    }

    @GetMapping("/blogs/categories")
    public List<String> getAllCategories() {
        return blogService.getAllCategories();
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
        blog.setBId(userId + "_" + System.currentTimeMillis());
        blog.setBTitle(title);
        blog.setBContent(content);
        blog.setBAuthor(user);
        blog.setBSlug(slug);
        blog.setBCategory(category);
        blog.setBTags(tags);
        blog.setBImage(image);
        blog.setBUpdatedOn(null);
        blog.setBCreatedOn(LocalDate.now().format(formatter));
        blog.setBExcerpt(excerpt);
        return blogService.createBlog(blog);
    }

    @PutMapping("/blogs")
    public Blog updateBlog(
            @RequestParam("blogId") String blogId,
            @RequestBody BlogRequest blogRequest
    ) 
    {
        String userId = blogRequest.getUserId();
    
        if(!blogId.equals(userId))
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
        blog.setBTitle(title);
        blog.setBContent(content);
        blog.setBSlug(slug);
        blog.setBCategory(category);
        blog.setBTags(tags);
        blog.setBImage(image);
        blog.setBUpdatedOn(LocalDate.now().format(formatter));
        blog.setBExcerpt(excerpt);
        return blogService.updateBlog(blog);
    }

    @PostMapping("/blogs/{id}/like")
    public void likeBlog(@PathVariable("id") String id, @RequestBody String userId) {
        likeService.likeBlog(id, userId);
    }

    @PostMapping("/blogs/{id}/unlike")
    public void unlikeBlog(@PathVariable("id") String id, @RequestBody String userId) {
        likeService.unlikeBlog(id, userId);
    }

    @PostMapping("/blogs/{id}/comment")
    public Comments commentBlog(
            @RequestBody CommentRequest commentRequest
    ) 
    {
        String userId = commentRequest.getUserId();
        String content = commentRequest.getComments();
        String blogId = commentRequest.getBlogId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        User user = userService.getUser(userId);
        Blog blog = blogService.getBlog(blogId);
        Comments comment = new Comments();
        comment.setCId(userId + blogId + "_" + System.currentTimeMillis());
        comment.setCContent(content);
        comment.setCBlog(blog);
        comment.setCUser(user);
        comment.setCCreatedOn(LocalDate.now().format(formatter));
        return commentService.createComment(comment);
    }

    @PutMapping("/blogs/{id}/comment")
    public Comments updateComment(
            @PathVariable("cid") String cid, 
            @RequestBody CommentRequest commentRequest
    ) 
    {
        String userId = commentRequest.getUserId();
        String content = commentRequest.getComments();
        String blogId = commentRequest.getBlogId();

        if(userService.getUser(userId) != commentService.getComment(cid).getCUser())
            throw new RuntimeException("User not authorized to update comment");

        if(blogService.getBlog(blogId) != commentService.getComment(cid).getCBlog())
            throw new RuntimeException("Blog not authorized to update comment");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Comments comment = commentService.getComment(cid);
        comment.setCContent(content);
        comment.setCUpdatedOn(LocalDate.now().format(formatter));
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/blogs/{id}/comment")
    public void deleteComment(@PathVariable("id") String id) {
        commentService.deleteComment(id);
    }

    @DeleteMapping("/blogs/{id}")
    public void deleteBlog(@PathVariable("id") String id) {
        blogService.deleteBlog(id);
    }
}
