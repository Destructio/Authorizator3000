package com.github.destructio.authorizator3000.provider;

import java.util.Map;

public class GoogleUser extends ProviderUser {

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
    public Provider getAuthProvider() {
        return Provider.Google;
    }

}
