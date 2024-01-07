package com.github.destructio.authorizator3000.model.user.oauth2.provider;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.user.oauth2.JpaOAuth2User;

import java.util.Map;

public class GoogleUser extends JpaOAuth2User {

    public GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public JpaUserProvider getAuthProvider() {
        return JpaUserProvider.Google;
    }

}
