package com.projects.blog.Services;

// imports
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.projects.blog.IServices.IUserService;
import com.projects.blog.Models.User;
import com.projects.blog.Repo.UserRepo;

@Component
@Service
public class UserService implements IUserService {

    // dependency injections
    @Autowired
    private UserRepo userRepo;

    // services

    @Override
    public User createUser(User user) {
        System.out.println(user.getId());
        if(userRepo.findById(user.getId()).isPresent())
        {
            System.out.println("User already exists");
            return null;
        }
        return userRepo.save(user);
    }

    @Override
    public User getUser(String id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(String id) {
        
        if(userRepo.findById(id).orElse(null) == null)
            throw new RuntimeException("User not found");

        userRepo.deleteById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<String> getAllUId() {
        List<String> uIds = new ArrayList<>();
        for(User user : userRepo.findAll())
        {
            uIds.add(user.getId());
        }
        return uIds;
    }

    

}
