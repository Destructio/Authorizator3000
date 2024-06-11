package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.model.JpaUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService()
                .loadUser(userRequest);

        log.debug("\nUser: {} \nAttributes: {} \nAuthorities: {}",
                oidcUser.getName(), oidcUser.getAttributes(), oidcUser.getAuthorities());

        User user = userService.getUser(oidcUser);

        return new JpaUserDetails(user);
    }
}
