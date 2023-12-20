package com.projects.blog.IServices;
import java.util.List;
import org.springframework.stereotype.Service;

import com.projects.blog.Models.User;

@Service
public interface IUserService{
    
    public User createUser(User user);

    public List<User> getUsers();

    public User getUser(String id);

    public User updateUser(User user);

    public void deleteUser(String id);

    public List<String> getAllUId();

}
