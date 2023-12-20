package com.projects.blog.Services;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    private BlogRepo blogRepo;
    private LikesRepo likesRepo;
    private UserRepo userRepo;

    @Override
    public void likeBlog(String blogId, String userId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Likes like = new Likes();
        like.setLBlog(blogRepo.findById(blogId).get());
        like.setLUser(userRepo.findById(userId).get());
        like.setLCreatedOn(LocalDate.now().format(formatter));
        like.setLId(userId + blogId);

        likesRepo.save(like);

        Blog blog = blogRepo.findById(blogId).orElse(null);
        blog.getBLikes().add(like);
        blogRepo.save(blog);

        User user = userRepo.findById(userId).orElse(null);
        user.getULikes().add(like);
        userRepo.save(user);

    }

    @Override
    public void unlikeBlog(String blogId, String userId) {
    
        Likes like = likesRepo.findById(userId + blogId).orElse(null);

        likesRepo.deleteById(userId + blogId);

        Blog blog = blogRepo.findById(blogId).orElse(null);
        blog.getBLikes().remove(like);
        blogRepo.save(blog);

        User user = userRepo.findById(userId).orElse(null);
        user.getULikes().remove(like);
        userRepo.save(user);
    }

    @Override
    public List<Likes> getLikesByBlog(String blogId) {
        return blogRepo.findById(blogId).get().getBLikes();
    }

    @Override
    public List<Likes> getLikesByUser(String userId) {
        return userRepo.findById(userId).get().getULikes();
    }
}
