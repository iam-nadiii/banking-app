package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.banking.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer>
{
    Profile findByUserId(int userId);
}
