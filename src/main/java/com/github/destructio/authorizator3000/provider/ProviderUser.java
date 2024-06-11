package com.github.destructio.authorizator3000.provider;

import lombok.Getter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

@Getter
public abstract class ProviderUser {
    protected Map<String, Object> attributes;

    public ProviderUser(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getName();

    public abstract String getUsername();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract Provider getAuthProvider();

    public static class Factory {
        public static ProviderUser getOAuth2User(OAuth2UserRequest request, OAuth2User user) {
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

