package com.projects.blog.Services;

// imports
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.projects.blog.IServices.ICommentService;
import com.projects.blog.Models.Blog;
import com.projects.blog.Models.Comments;
import com.projects.blog.Models.User;
import com.projects.blog.Repo.BlogRepo;
import com.projects.blog.Repo.CommentsRepo;
import com.projects.blog.Repo.UserRepo;

@Component
@Service
public class CommentService implements ICommentService {
    
    // dependency injections
    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlogRepo blogRepo;

    // services

    @Override
    public Comments createComment(Comments comment) {

        if(comment == null)
            throw new RuntimeException("Comment cannot be null");
        
        try{
            User user = userRepo.findById(comment.getUser()).get();

            if(user.getComments() == null)
                user.setComments(new java.util.ArrayList<String>());

            user.getComments().add(comment.getId());
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

        try{
            Blog blog = blogRepo.findById(comment.getBlog()).get();

            if(blog.getComments() == null)
                blog.setComments(new java.util.ArrayList<String>());

            blog.getComments().add(comment.getId());
            blogRepo.save(blog);
        }
        catch(Exception e){
            throw new RuntimeException("Blog not updated");
        }
        
        return commentsRepo.save(comment);
    }

    @Override
    public Comments getComment(String id) {

        if(id == null)
            throw new RuntimeException("Comment id cannot be null");

        return commentsRepo.findById(id).get();
    }

    @Override
    public Comments updateComment(Comments comment) {

        if(comment == null)
            throw new RuntimeException("Comment cannot be null");

        try{
            User user = userRepo.findById(comment.getUser()).get();

            if(user.getComments() == null)
                user.setComments(new java.util.ArrayList<String>());

            user.getComments().add(comment.getId());
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }

        try{
            Blog blog = blogRepo.findById(comment.getBlog()).get();

            if(blog.getComments() == null)
                blog.setComments(new java.util.ArrayList<String>());

            blog.getComments().add(comment.getId());
            blogRepo.save(blog);
        }
        catch(Exception e){
            throw new RuntimeException("Blog not updated");
        }

        return commentsRepo.save(comment);
    }

    @Override
    public void deleteComment(String id) {

        Comments comment = commentsRepo.findById(id).get();
        
        try{
            User user = userRepo.findById(comment.getUser()).get();
            List<String> comments = user.getComments();
            comments.remove(id);
            user.setComments(comments);
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RuntimeException("User not updated");
        }
        
        try{
            Blog blog = blogRepo.findById(comment.getBlog()).get();
            List<String> comments = blog.getComments();
            comments.remove(id);
            blog.setComments(comments);
            blogRepo.save(blog);
        }
        catch(Exception e){
            throw new RuntimeException("Blog not updated");
        }
        
        commentsRepo.deleteById(id);

        if(commentsRepo.findById(id) != null)
            throw new RuntimeException("Comment not deleted");
    }

    @Override
    public List<Comments> getCommentsByBlog(String blogId) {

        if(blogId == null)
            throw new RuntimeException("Blog id cannot be null");

        return commentsRepo.findByBlog(blogId);
    }

    @Override
    public List<Comments> getCommentsByUser(String userId) {
            
        if(userId == null)
            throw new RuntimeException("User id cannot be null");

        return commentsRepo.findByUser(userId);
    }
    
}
