package com.sparta.trello.auth.security;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getRole();
        String authority = role.getAuthority();

        // 해당 주석은 기능이 잘 수행되는 것이 확인 되면 삭제하도록 하겠습니다
        // SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        // Collection<GrantedAuthority> authorities = new ArrayList<>();
        // authorities.add(simpleGrantedAuthority);

        return Collections.singleton(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
