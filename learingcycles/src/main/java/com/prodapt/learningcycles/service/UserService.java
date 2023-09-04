package com.prodapt.learningcycles.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prodapt.learningcycles.entity.User;
import com.prodapt.learningcycles.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Implement UserRepository


    public void register(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Set user enabled (you can customize this based on your requirements)
        user.setEnabled(true);

        userRepository.save(user);
    }
}
