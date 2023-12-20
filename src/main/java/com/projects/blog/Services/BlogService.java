package com.projects.blog.Services;
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
    
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Blog createBlog(Blog blog) {
        User user = userRepo.findById(blog.getBAuthor().getUId()).get();

        user.getBlogs().add(blog);

        userRepo.save(user);

        return blogRepo.save(blog);
    }

    @Override
    public Blog getBlog(String id) {
        return blogRepo.findById(id).get();
    }

    @Override
    public Blog updateBlog(Blog blog) {
        
        User user = userRepo.findById(blog.getBAuthor().getUId()).get();

        user.getBlogs().add(blog);

        userRepo.save(user);

        return blogRepo.save(blog);
    }

    @Override
    public void deleteBlog(String id) {
        blogRepo.deleteById(id);
        
        User user = userRepo.findById(blogRepo.findById(id).get().getBAuthor().getUId()).get();
        user.getBlogs().remove(blogRepo.findById(id).get());
        userRepo.save(user);
        
        if(blogRepo.findById(id) != null)
            throw new RuntimeException("Blog not deleted");
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepo.findAll();
    }

    @Override
    public List<Blog> getBlogsByUser(String userId) {
        return userRepo.findById(userId).get().getBlogs();
    }

    @Override
    public User findUserByBlog(String blogId) {
        return blogRepo.findById(blogId).get().getBAuthor();
    }

    @Override
    public List<String> getAllCategories() {
        
        Set<String> categories = new HashSet<String>();

        for(Blog blog : blogRepo.findAll())
            categories.add(blog.getBCategory());

        List<String> categoriesList = List.copyOf(categories);

        return categoriesList;
    }

    @Override
    public List<Blog> getRecentBlogs() {
        return blogRepo.findTop5ByOrderByBCreatedOnDesc();
    }
    
}
