package com.projects.blog.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.projects.blog.Models.User;

@Repository
public interface UserRepo extends MongoRepository<User, String>{

}
