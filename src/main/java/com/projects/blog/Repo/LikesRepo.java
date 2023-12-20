package com.projects.blog.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.projects.blog.Models.Likes;

@Repository
public interface LikesRepo extends MongoRepository<Likes, String>{
    
}
