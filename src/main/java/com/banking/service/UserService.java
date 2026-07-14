package com.banking.service;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.banking.model.User;
import com.banking.repository.UserRepository;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    // FIX: was `int userId` — UserRepository is JpaRepository<User, Long>,
    // matching User's real @Id type. This would have failed at Spring
    // context startup (repository proxy generation checks the ID type
    // against the entity's actual @Id, not just what compiles).
    public User getUserById(Long userId)
    {
        return userRepository.findById(userId).orElse(null);
    }

    public User getByUserName(String username)
    {
        return userRepository.findByUsername(username);
    }

    public Long getIdByUsername(String username)
    {
        User user = userRepository.findByUsername(username);
        return Math.toIntExact(user != null ? user.getId() : -1);
    }

    public boolean exists(String username)
    {
        return userRepository.existsByUsername(username);
    }

    public User create(User user)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}