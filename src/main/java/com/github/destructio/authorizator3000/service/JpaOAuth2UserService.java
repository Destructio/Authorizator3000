package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.user.JpaUserDetails;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import com.github.destructio.authorizator3000.repository.UserRepository;
import com.github.destructio.authorizator3000.model.user.oauth2.OAuth2UserDto;
import com.github.destructio.authorizator3000.model.user.oauth2.OAuth2UserFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class JpaOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public JpaOAuth2UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService()
                .loadUser(userRequest);

        log.info(
                """
                User: {}
                Attributes: {}
                Authorities: {}
                """,
                oAuth2User.getName(),
                oAuth2User.getAttributes(),
                oAuth2User.getAuthorities()
        );

        OAuth2UserDto auth2User = OAuth2UserFactory.getOAuth2User(userRequest, oAuth2User);

        User user = userRepository.findByEmail(auth2User.getEmail())
                .orElseGet(() -> createUser(auth2User));

        return new JpaUserDetails(user);
    }

    @Transactional
    public User createUser(OAuth2UserDto auth2User) {
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

