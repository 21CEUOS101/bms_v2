package com.projects.blog.Services;

// imports
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.projects.blog.IServices.ILikeService;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.Likes;
import com.projects.blog.Models.User;
import com.projects.blog.Repo.BlogRepo;
import com.projects.blog.Repo.LikesRepo;
import com.projects.blog.Repo.UserRepo;

@Component
@Service
public class LikeService implements ILikeService {

    // dependency injections
    @Autowired
    private BlogRepo blogRepo;

    @Autowired
    private LikesRepo likesRepo;

    @Autowired
    private UserRepo userRepo;

    // services

    @Override
    public void likeBlog(String blogId, String userId) {


        if(blogRepo.findById(blogId).orElse(null) == null)
            throw new RuntimeException("Blog not found");
        else if(userRepo.findById(userId).orElse(null) == null)
            throw new RuntimeException("User not found");
        else if(likesRepo.findByUserAndBlog(userId, blogId) != null)
            throw new RuntimeException("Like already exists");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Random rand = new Random();
        int num = rand.nextInt(1000000);

        Likes like = new Likes();
        like.setBlog(blogId);
        like.setUser(userId);
        like.setCreatedOn(LocalDate.now().format(formatter));
        like.setId(userId + blogId + String.valueOf(num));

        try{
            likesRepo.save(like);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Like not saved");
        }

        try{
            Blog blog = blogRepo.findById(blogId).orElse(null);

            if(blog.getLikes() == null)
                blog.setLikes(new ArrayList<String>());

            blog.getLikes().add(like.getId());
            blogRepo.save(blog);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Blog not updated");
        }

        try{
            User user = userRepo.findById(userId).orElse(null);

            if(user.getLikes() == null)
                user.setLikes(new ArrayList<String>());

            user.getLikes().add(like.getId());
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

    }

    @Override
    public void unlikeBlog(String blogId, String userId) {

        if(blogRepo.findById(blogId).orElse(null) == null)
            throw new RuntimeException("Blog not found");
        else if(userRepo.findById(userId).orElse(null) == null)
            throw new RuntimeException("User not found");
        else if(likesRepo.findByUserAndBlog(userId, blogId) == null)
            throw new RuntimeException("Like does not exist");

        Likes like = likesRepo.findByUserAndBlog(userId, blogId);
        
        try{
            Blog blog = blogRepo.findById(blogId).orElse(null);
            List<String> likes = blog.getLikes();
            likes.remove(like.getId());
            blog.setLikes(likes);
            blogRepo.save(blog);
        }
        catch(Exception e){
            throw new RuntimeException("Blog not updated");
        }
        
        try{
            User user = userRepo.findById(userId).orElse(null);
            List<String> likes = user.getLikes();
            likes.remove(like.getId());
            user.setLikes(likes);
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

        likesRepo.deleteByUserAndBlog(userId, blogId);
    }

    @Override
    public List<Likes> getLikesByBlog(String blogId) {
        return likesRepo.findByBlog(blogId);
    }

    @Override
    public List<Likes> getLikesByUser(String userId) {
        return likesRepo.findByUser(userId);
    }
}
