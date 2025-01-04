package com.ercanbeyen.authservice.config;

import com.ercanbeyen.authservice.entity.UserCredential;
import com.ercanbeyen.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> optionalUserCredential = userCredentialRepository.findByUsername(username);
        return optionalUserCredential.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username is not found " + username));
    }
}
