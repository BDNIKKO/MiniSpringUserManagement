package org.example.minispringusermanagement;

import jakarta.persistence.*;  // JPA annotations from Jakarta EE
import jakarta.validation.constraints.*;  // Validation annotations from Jakarta EE

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @PasswordStrength // Apply the custom annotation here
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
