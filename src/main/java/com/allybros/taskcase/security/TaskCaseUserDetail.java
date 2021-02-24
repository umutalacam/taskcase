package com.allybros.taskcase.security;

import com.allybros.taskcase.data.domain.User;
import javassist.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class TaskCaseUserDetail implements UserDetails {

    private User user;

    public TaskCaseUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (this.user.getRole() == User.Role.ADMIN)
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        else
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_STD_USER"));

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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
        return true;
    }
}
