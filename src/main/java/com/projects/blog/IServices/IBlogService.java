package com.projects.blog.IServices;
import java.util.List;
import org.springframework.stereotype.Service;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.User;

@Service
public interface IBlogService {
    
    public Blog createBlog(Blog blog);

    public Blog getBlog(String id);

    public Blog updateBlog(Blog blog);

    public void deleteBlog(String id);

    public List<Blog> getBlogs();

    public List<Blog> getBlogsByUser(String userId);

    public User findUserByBlog(String blogId);

    public List<String> getAllCategories();

    public List<Blog> getRecentBlogs();

    public List<Blog> getBlogsByCategory(String category);
    
}
