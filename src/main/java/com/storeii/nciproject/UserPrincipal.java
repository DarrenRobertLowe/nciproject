package com.storeii.nciproject;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Darren Robert Lowe
 */
public class UserPrincipal implements UserDetails {
    private User user;
    
    public UserPrincipal(User user) {this.user = user;}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    public User getUser() {
        return user;
    }
    
    @Override
    public String getPassword() { return user.getUserPass(); }

    @Override
    public String getUsername() { return user.getUserName(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
