package com.example.portfolio.services;

import com.example.portfolio.entities.User;
import com.example.portfolio.repositories.UserRepository;
import com.example.portfolio.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    public User update(Long id, User user) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

            existingUser.setNome(user.getNome());
            existingUser.setEmail(user.getEmail());
            existingUser.setTelefone(user.getTelefone());
            existingUser.setPassword(user.getPassword());

            return userRepository.save(existingUser);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}
