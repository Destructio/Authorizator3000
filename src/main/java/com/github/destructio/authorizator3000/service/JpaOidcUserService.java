package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.entity.Role;
import com.github.destructio.authorizator3000.model.entity.User;
import com.github.destructio.authorizator3000.model.oauth2.JpaUserDetails;
import com.github.destructio.authorizator3000.repository.RoleRepository;
import com.github.destructio.authorizator3000.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class JpaOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

        User user = userRepository.findByEmail(oidcUser.getEmail())
                .orElseGet(() -> createUser(oidcUser));

        return new JpaUserDetails(user);
    }

    private User createUser(OidcUser oidcUser) {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new OAuth2AuthenticationException("Default role USER is not found!"));

        User user = new User();
        user.setName(oidcUser.getName());
        user.setEmail(oidcUser.getEmail());
        user.setUsername(oidcUser.getPreferredUsername());
        user.setProvider(JpaUserProvider.OIDC);
        user.setRoles(Set.of(role));
        userRepository.save(user);

        return user;
    }
}
