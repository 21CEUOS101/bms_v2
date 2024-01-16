package com.projects.blog.Repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.projects.blog.Models.Likes;
import java.util.List;


@Repository
public interface LikesRepo extends MongoRepository<Likes, String>{
    public Likes findByUserAndBlog(String lUser, String lBlog);

    public void deleteByUserAndBlog(String lUser, String lBlog);
    
    public List<Likes> findByUser(String lUser);

    public List<Likes> findByBlog(String lBlog);
}
