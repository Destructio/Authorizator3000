package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.model.JpaUserDetails;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.provider.ProviderUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService()
                .loadUser(userRequest);

        log.debug("\nUser: {} \nAttributes: {} \nAuthorities: {}",
                oAuth2User.getName(), oAuth2User.getAttributes(), oAuth2User.getAuthorities());

        ProviderUser jpaUser = ProviderUser.Factory.getOAuth2User(userRequest, oAuth2User);

        User user = userService.getUser(jpaUser);

        return new JpaUserDetails(user);
    }

}

