package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUser(User user, int id) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }
}
