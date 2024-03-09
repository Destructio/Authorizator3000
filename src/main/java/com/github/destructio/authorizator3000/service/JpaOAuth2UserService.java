package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.entity.Role;
import com.github.destructio.authorizator3000.model.entity.User;
import com.github.destructio.authorizator3000.model.oauth2.JpaOAuth2User;
import com.github.destructio.authorizator3000.model.oauth2.JpaUserDetails;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import com.github.destructio.authorizator3000.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class JpaOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public JpaOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService()
                .loadUser(userRequest);

        log.info("""
                        User: {}
                        Attributes: {}
                        Authorities: {}
                        """,
                oAuth2User.getName(),
                oAuth2User.getAttributes(),
                oAuth2User.getAuthorities()
        );

        JpaOAuth2User auth2User = JpaOAuth2User.Factory.getOAuth2User(userRequest, oAuth2User);

        User user = userRepository.findByEmail(auth2User.getEmail())
                .orElseGet(() -> createUser(auth2User));

        return new JpaUserDetails(user);
    }

    @Transactional
    public User createUser(JpaOAuth2User auth2User) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new OAuth2AuthenticationException("Default role USER is not found!"));

        User user = new User();
        user.setName(auth2User.getName());
        user.setEmail(auth2User.getEmail());
        user.setUsername(auth2User.getUsername());
        user.setProvider(auth2User.getAuthProvider());
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return user;
    }

}

