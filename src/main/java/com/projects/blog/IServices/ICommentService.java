package com.projects.blog.IServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projects.blog.Models.Comments;

@Service
public interface ICommentService {
    
    public Comments createComment(Comments comment);

    public Comments getComment(String id);

    public Comments updateComment(Comments comment);

    public void deleteComment(String id);

    public List<Comments> getCommentsByBlog(String blogId);

    public List<Comments> getCommentsByUser(String userId);
}
