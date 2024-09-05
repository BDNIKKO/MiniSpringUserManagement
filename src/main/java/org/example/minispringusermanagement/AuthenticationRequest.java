package org.example.minispringusermanagement;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    // Setter for username
    // Getter for username
    private String username;
    // Setter for password
    // Getter for password
    private String password;

    // Default constructor
    public AuthenticationRequest() {
    }

    // Constructor with parameters
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
