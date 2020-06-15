package com.andrew.repositories;

import com.andrew.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

    User findUserByUsernameAndPassword(String username, String password);
}
