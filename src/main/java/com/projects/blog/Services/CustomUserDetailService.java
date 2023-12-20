package com.projects.blog.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projects.blog.Repo.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
    
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        
        UserDetails user = userRepo.findById(uid).get();

        return user;
    }
    
}
