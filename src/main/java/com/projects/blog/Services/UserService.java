package com.projects.blog.Services;

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

    @Autowired
    private UserRepo userRepo;

    @Override
    public User createUser(User user) {
        System.out.println(user.getUId());
        if(userRepo.findById(user.getUId()).isPresent())
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
        
        userRepo.deleteById(id);

        if(userRepo.findById(id) != null)
            throw new RuntimeException("User not deleted");

    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<String> getAllUId() {
        List<String> uIds = new ArrayList<>();
        for(User user : userRepo.findAll())
            uIds.add(user.getUId());
        return uIds;
    }

    

}
