package com.github.destructio.authorizator3000.model.user.oidc.provider;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.user.oidc.JpaOidcUser;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public class KeycloakUser extends JpaOidcUser {
    public KeycloakUser(OidcIdToken idToken, OidcUserInfo userInfo, Map<String, Object> claims) {
        super(idToken, userInfo, claims);
    }

    @Override
    public String getName() {
        return userInfo.getFullName();
    }

    @Override
    public String getUsername() {
        return userInfo.getPreferredUsername();
    }

    @Override
    public String getEmail() {
        return userInfo.getEmail();
    }

    @Override
    public String getImageUrl() {
        return userInfo.getPicture();
    }

    @Override
    public JpaUserProvider getAuthProvider() {
        return JpaUserProvider.Keycloak;
    }
}
