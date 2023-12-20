package com.projects.blog.Repo;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.projects.blog.Models.Blog;

@Repository
public interface BlogRepo  extends MongoRepository<Blog, String>{
    
    public List<Blog> findTop5ByOrderByBCreatedOnDesc();
}
