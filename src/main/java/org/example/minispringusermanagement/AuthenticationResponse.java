package org.example.minispringusermanagement;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Getter
public class AuthenticationResponse {
    // Getters
    private String jwt;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthenticationResponse(String jwt, Collection<? extends GrantedAuthority> authorities) {
        this.jwt = jwt;
        this.authorities = authorities;
    }

}
