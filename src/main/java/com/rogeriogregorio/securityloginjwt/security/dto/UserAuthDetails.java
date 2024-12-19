package com.rogeriogregorio.securityloginjwt.security.dto;

import com.rogeriogregorio.securityloginjwt.security.entities.User;
import com.rogeriogregorio.securityloginjwt.security.entities.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserAuthDetails implements UserDetails {

    private final User user;

    @Autowired
    public UserAuthDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Map<UserRole, SimpleGrantedAuthority> roleAuthorityMap = new EnumMap<>(UserRole.class);
        roleAuthorityMap.put(UserRole.USER, new SimpleGrantedAuthority("ROLE_USER"));
        roleAuthorityMap.put(UserRole.MANAGER, new SimpleGrantedAuthority("ROLE_MANAGER"));
        roleAuthorityMap.put(UserRole.ADMIN, new SimpleGrantedAuthority("ROLE_ADMIN"));

        return Collections.<GrantedAuthority>singleton(roleAuthorityMap.get(user.getRole()));
    }

    public String getRole() {
        return getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }

    public String getUserId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
