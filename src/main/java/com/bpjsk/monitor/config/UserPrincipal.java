package com.bpjsk.monitor.config;

import com.bpjsk.monitor.model.User;
import com.bpjsk.monitor.model.UserRole;
import com.bpjsk.monitor.repository.UserRepository;
import com.bpjsk.monitor.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {

    private final User user;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //List<UserRole> user = userRoleRepository.findAll();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        //for(UserRole userRole:userRoles){
        //System.out.println(user.getRole().getRole());
            grantedAuthorities.add( new SimpleGrantedAuthority(user.getRole().getRole()));
        //}
        return grantedAuthorities;
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
