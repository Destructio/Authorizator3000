package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.model.user.JpaUserDetails;
import com.github.destructio.authorizator3000.model.user.oauth2.OAuth2UserDto;
import com.github.destructio.authorizator3000.model.user.oauth2.OAuth2UserFactory;
import com.github.destructio.authorizator3000.model.user.oidc.OidcUserDto;
import com.github.destructio.authorizator3000.model.user.oidc.OidcUserFactory;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import com.github.destructio.authorizator3000.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class JpaOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public JpaOidcUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService()
                .loadUser(userRequest);

        log.info(
                """
                User: {}
                Attributes: {}
                Authorities: {}
                """,
                oidcUser.getName(),
                oidcUser.getAttributes(),
                oidcUser.getAuthorities()
        );

        OidcUserDto oidcUserDto = OidcUserFactory.getOidcUser(userRequest, oidcUser);

        User user = userRepository.findByEmail(oidcUser.getEmail())
                .orElseGet(() -> createUser(oidcUserDto));

        return new JpaUserDetails(user);
    }

    private User createUser(OidcUserDto oidcUser) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new OAuth2AuthenticationException("Default role USER is not found!"));

        User user = new User();
        user.setName(oidcUser.getName());
        user.setEmail(oidcUser.getEmail());
        user.setUsername(oidcUser.getUsername());
        user.setProvider(oidcUser.getAuthProvider());
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return user;
    }
}
