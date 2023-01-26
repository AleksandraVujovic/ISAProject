package com.example.BloodBank.service;

import com.example.BloodBank.model.User;
import com.example.BloodBank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    @Cacheable(value = "userInfo", key="#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        String role = "ROLE_" + user.getRole().toString();
        String id = Long.toString(user.getId());
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        grantedAuthorities.add(new SimpleGrantedAuthority(id));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername().trim(),
                user.getPassword().trim(),
                grantedAuthorities);
    }
}
