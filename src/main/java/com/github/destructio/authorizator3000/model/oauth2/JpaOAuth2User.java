package com.github.destructio.authorizator3000.model.oauth2;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.oauth2.providers.GitHubUser;
import com.github.destructio.authorizator3000.model.oauth2.providers.GoogleUser;
import lombok.Getter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
public abstract class JpaOAuth2User {
    protected Map<String, Object> attributes;

    protected JpaOAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getName();

    public abstract String getUsername();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract JpaUserProvider getAuthProvider();

    public static class Factory {
        public static JpaOAuth2User getOAuth2User(OAuth2UserRequest request, OAuth2User user) {
            Map<String, Object> attributes = user.getAttributes();
            String provider = request.getClientRegistration().getClientName();

            return switch (provider) {
                case "Google" -> new GoogleUser(attributes);
                case "GitHub" -> new GitHubUser(attributes);
                default ->
                        throw new OAuth2AuthenticationException("Sorry! Login with " + provider + " is not supported yet.");
            };
        }
    }
}

