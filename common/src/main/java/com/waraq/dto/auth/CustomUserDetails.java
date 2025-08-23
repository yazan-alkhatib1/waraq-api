package com.waraq.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class CustomUserDetails implements UserDetails {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String role;

    private Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


    public Map<String, Object> getClaims() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", this.id);
        claims.put("isEnabled", this.isEnabled);
        claims.put("firstName", this.firstName);
        claims.put("lastName", this.lastName);
        claims.put("email", this.email);
        claims.put("phoneNumber", this.phoneNumber);
        claims.put("role", role);
        return claims;
    }

}
