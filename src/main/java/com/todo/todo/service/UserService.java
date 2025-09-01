package com.todo.todo.service;

import com.todo.todo.model.User;
import com.todo.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getByUserId(long userId) {
        return userRepository.getById(userId);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public boolean isValid(User user) {
        User existingUser = userRepository.findByLogin(user.getLogin());
        if (existingUser == null) return false;
        return user.getLogin().equals(existingUser.getLogin());
    }
}
