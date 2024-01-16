package com.projects.blog.Services;

// imports
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.projects.blog.IServices.IBlogService;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.User;
import com.projects.blog.Repo.BlogRepo;
import com.projects.blog.Repo.UserRepo;

@Component
@Service
public class BlogService implements IBlogService {
    
    // dependency injections
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;

    // services

    @Override
    public Blog createBlog(Blog blog) {

        if(blog == null)
            throw new RuntimeException("Blog cannot be null");

        User user = userRepo.findById(blog.getAuthor()).get();

        if(user == null)
            throw new RuntimeException("User not found");

        if(user.getBlogs() == null)
            user.setBlogs(new ArrayList<String>());

        user.getBlogs().add(blog.getId());

        try{
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

        return blogRepo.save(blog);
    }

    @Override
    public Blog getBlog(String id) {

        if(id == null)
            throw new RuntimeException("Blog id cannot be null");

        return blogRepo.findById(id).get();
    }

    @Override
    public Blog updateBlog(Blog blog) {

        if(blog == null)
            throw new RuntimeException("Blog cannot be null");
        
        User user = userRepo.findById(blog.getAuthor()).get();

        if(user == null)
            throw new RuntimeException("User not found");

        user.getBlogs().add(blog.getId());

        try{
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

        return blogRepo.save(blog);
    }

    @Override
    public void deleteBlog(String id) {

        if(id == null)
            throw new RuntimeException("Blog id cannot be null");

        try{
            User user = userRepo.findById(blogRepo.findById(id).get().getAuthor()).get();
            user.getBlogs().remove(id);
            userRepo.save(user);
        }
        catch (Exception e) {
            throw new RuntimeException("User not updated");
        }
        
        blogRepo.deleteById(id);
            
        if(blogRepo.findById(id) != null)
            throw new RuntimeException("Blog not deleted");
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public List<Blog> getBlogsByUser(String userId) {

        if(userId == null)
            throw new RuntimeException("User id cannot be null");
        else if(userRepo.findById(userId) == null)
            throw new RuntimeException("User not found");

        List<Blog> blogs = blogRepo.findByAuthor(userId);

        if(blogs == null)
            throw new RuntimeException("No blogs found");

        return blogs;
    }

    @Override
    public User findUserByBlog(String blogId) {
        String userId = blogRepo.findById(blogId).get().getAuthor();

        if(userId == null)
            throw new RuntimeException("User id cannot be null");
        else if(userRepo.findById(userId) == null)
            throw new RuntimeException("User not found");

        return userRepo.findById(userId).get();
    }

    @Override
    public List<String> getAllCategories() {
        
        Set<String> categories = new HashSet<String>();

        for(Blog blog : blogRepo.findAll())
            categories.add(blog.getCategory());

        List<String> categoriesList = List.copyOf(categories);

        return categoriesList;
    }

    @Override
    public List<Blog> getRecentBlogs() {
        return blogRepo.findTop5ByOrderByCreatedOnDesc();
    }

    @Override
    public List<Blog> getBlogsByCategory(String category) {

        if(category == null)
            throw new RuntimeException("Category cannot be null");

        List<Blog> blogs = blogRepo.findByCategory(category);

        if(blogs == null)
            throw new RuntimeException("No blogs found");

        return blogs;
    }
    
}
