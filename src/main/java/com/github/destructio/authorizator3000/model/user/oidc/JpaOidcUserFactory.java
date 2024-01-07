package com.github.destructio.authorizator3000.model.user.oidc;

import com.github.destructio.authorizator3000.model.user.oidc.provider.KeycloakUser;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;

@NoArgsConstructor
public class JpaOidcUserFactory {
    public static JpaOidcUser getOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        OidcIdToken idToken = oidcUser.getIdToken();
        OidcUserInfo userInfo = oidcUser.getUserInfo();
        Map<String, Object> claims = oidcUser.getClaims();

        String provider = userRequest.getClientRegistration().getClientName();

        return switch (provider) {
            case "http://localhost:8888/realms/test-realm" -> new KeycloakUser(idToken, userInfo, claims);
            default ->
                    throw new OAuth2AuthenticationException("Sorry! Login with " + provider + " is not supported yet.");
        };
    }
}
