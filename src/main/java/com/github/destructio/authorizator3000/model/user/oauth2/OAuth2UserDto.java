package com.github.destructio.authorizator3000.model.user.oauth2;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;

import java.util.Map;

public abstract class OAuth2UserDto {
    protected Map<String, Object> attributes;
    protected OAuth2UserDto(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public abstract String getName();
    public abstract String getUsername();
    public abstract String getEmail();
    public abstract String getImageUrl();
    public abstract JpaUserProvider getAuthProvider();
}
