package com.github.destructio.authorizator3000.model.user.oidc;

import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@NoArgsConstructor
public class OidcUserFactory {
    public static OidcUserDto getOidcUser(OidcUserRequest userRequest, OidcUser oidcUser) {
        return null;
    }
}
