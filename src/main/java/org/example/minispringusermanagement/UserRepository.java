package org.example.minispringusermanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Method to check if a user exists by username
    boolean existsByUsername(String username);

    // Method to check if a user exists by email
    boolean existsByEmail(String email);

    // Method to find a user by username
    Optional<User> findByUsername(String username);

    // Optional method to find user by email
    Optional<User> findByEmail(String email);
}
