package com.github.destructio.authorizator3000.model.user.oauth2;

import com.github.destructio.authorizator3000.model.user.oauth2.provider.GitHubUser;
import com.github.destructio.authorizator3000.model.user.oauth2.provider.GoogleUser;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@NoArgsConstructor
public class OAuth2UserFactory {
    public static JpaOAuth2User getOAuth2User(OAuth2UserRequest request, OAuth2User user) {
        Map<String, Object> attributes = user.getAttributes();
        String provider = request.getClientRegistration().getClientName();

        return switch (provider) {
            case "Google" -> new GoogleUser(attributes);
            case "GitHub" -> new GitHubUser(attributes);
            default -> throw new OAuth2AuthenticationException("Sorry! Login with " + provider + " is not supported yet.");
        };
    }
}
