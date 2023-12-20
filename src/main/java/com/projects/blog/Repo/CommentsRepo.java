package com.projects.blog.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.projects.blog.Models.Comments;

@Repository
public interface CommentsRepo extends MongoRepository<Comments, String>{
    
}
