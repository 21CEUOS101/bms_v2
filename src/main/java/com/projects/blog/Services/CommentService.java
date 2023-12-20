package com.projects.blog.Services;

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
    
    @Autowired
    private CommentsRepo commentsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlogRepo blogRepo;

    @Override
    public Comments createComment(Comments comment) {
        
        User user = userRepo.findById(comment.getCUser().getUId()).get();
        user.getUComments().add(comment);
        userRepo.save(user);

        Blog blog = blogRepo.findById(comment.getCBlog().getBId()).get();
        blog.getBComments().add(comment);
        blogRepo.save(blog);
        
        return commentsRepo.save(comment);
    }

    @Override
    public Comments getComment(String id) {
        return commentsRepo.findById(id).get();
    }

    @Override
    public Comments updateComment(Comments comment) {

        User user = userRepo.findById(comment.getCUser().getUId()).get();
        user.getUComments().add(comment);
        userRepo.save(user);

        Blog blog = blogRepo.findById(comment.getCBlog().getBId()).get();
        blog.getBComments().add(comment);
        blogRepo.save(blog);

        return commentsRepo.save(comment);
    }

    @Override
    public void deleteComment(String id) {
        commentsRepo.deleteById(id);

        User user = userRepo.findById(commentsRepo.findById(id).get().getCUser().getUId()).get();
        user.getUComments().remove(commentsRepo.findById(id).get());
        userRepo.save(user);

        Blog blog = blogRepo.findById(commentsRepo.findById(id).get().getCBlog().getBId()).get();
        blog.getBComments().remove(commentsRepo.findById(id).get());
        blogRepo.save(blog);

        if(commentsRepo.findById(id) != null)
            throw new RuntimeException("Comment not deleted");
    }

    @Override
    public List<Comments> getCommentsByBlog(String blogId) {
        System.out.println(blogId);
        return blogRepo.findById(blogId).get().getBComments();
    }

    @Override
    public List<Comments> getCommentsByUser(String userId) {
        return userRepo.findById(userId).get().getUComments();
    }
    
}
