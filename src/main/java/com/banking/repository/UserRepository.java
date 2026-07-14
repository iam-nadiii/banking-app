package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.banking.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByUsername(String username);

    boolean existsByUsername(String username);
}