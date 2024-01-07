package com.github.destructio.authorizator3000.model.user.oidc;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public abstract class JpaOidcUser {
    protected OidcIdToken idToken;
    protected OidcUserInfo userInfo;
    protected Map<String, Object> claims;

    public JpaOidcUser(OidcIdToken idToken, OidcUserInfo userInfo, Map<String, Object> claims) {
        this.idToken = idToken;
        this.userInfo = userInfo;
        this.claims = claims;
    }

    public abstract String getName();
    public abstract String getUsername();
    public abstract String getEmail();
    public abstract String getImageUrl();
    public abstract JpaUserProvider getAuthProvider();
}
