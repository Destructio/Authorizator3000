package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.JpaUserDetails;
import com.github.destructio.authorizator3000.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(JpaUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));

    }
}
