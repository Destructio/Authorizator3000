package com.github.destructio.authorizator3000.model.user.oauth2;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.user.oauth2.provider.GitHubUserDto;
import com.github.destructio.authorizator3000.model.user.oauth2.provider.GoogleUserDto;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@NoArgsConstructor
public class OAuth2UserFactory {
    public static OAuth2UserDto getOAuth2User(OAuth2UserRequest request, OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();

        JpaUserProvider provider = JpaUserProvider.valueOf(request.getClientRegistration().getClientName());

        return switch (provider) {
            case Google -> new GoogleUserDto(attributes);
            case GitHub -> new GitHubUserDto(attributes);
            default -> throw new RuntimeException("Sorry! Login with " + provider + " is not supported yet.");
        };
    }
}
