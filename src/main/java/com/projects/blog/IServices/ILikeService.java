package com.projects.blog.IServices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projects.blog.Models.Likes;

@Service
public interface ILikeService {
        
    public void likeBlog(String blogId, String userId);

    public void unlikeBlog(String blogId, String userId);

    public List<Likes> getLikesByBlog(String blogId);

    public List<Likes> getLikesByUser(String userId);

}
