package org.example.minispringusermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register endpoint (public)
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            // Collect all validation error messages
            String errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }

        // Encode password and assign default role
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);  // By default, all registered users are given USER role
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // Get user by ID - Only ADMIN can access other users, USER can access only their own profile
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id, Authentication authentication) {
        // Fetch the authenticated user
        User loggedInUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch the user being requested
        User requestedUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only allow USER to access their own profile or allow ADMIN to access any profile
        if (!loggedInUser.getRole().equals(Role.ADMIN) && !loggedInUser.getId().equals(id)) {
            return ResponseEntity.status(403).body(null);  // 403 Forbidden
        }

        return ResponseEntity.ok(requestedUser);
    }

    // Get all users - Only ADMIN can view all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(Authentication authentication) {
        // Check if the logged-in user is an ADMIN
        User loggedInUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!loggedInUser.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).build();  // 403 Forbidden for non-ADMINs
        }

        // Return all users for ADMIN
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Update user details - USER can update their own info, ADMIN can update any user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser,
                                             BindingResult bindingResult, Authentication authentication) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMessages);
        }

        // Fetch the logged-in user
        User loggedInUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        // Fetch the user being updated
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only allow USER to update their own profile or allow ADMIN to update any profile
        if (!loggedInUser.getRole().equals(Role.ADMIN) && !loggedInUser.getId().equals(id)) {
            return ResponseEntity.status(403).body("You are not allowed to update this user.");
        }

        // Update user details
        userToUpdate.setUsername(updatedUser.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(updatedUser.getPassword()));  // Re-encode password
        userToUpdate.setEmail(updatedUser.getEmail());

        userRepository.save(userToUpdate);
        return ResponseEntity.ok("User updated successfully");
    }

    // Delete user - Only ADMIN can delete users
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, Authentication authentication) {
        // Fetch the logged-in user
        User loggedInUser = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Logged-in user not found"));

        if (!loggedInUser.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.status(403).body("Only ADMIN can delete users.");
        }

        // Fetch the user being deleted
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
