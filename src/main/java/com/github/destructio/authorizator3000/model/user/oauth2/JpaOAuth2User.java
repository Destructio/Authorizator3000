package com.github.destructio.authorizator3000.model.user.oauth2;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import lombok.Getter;

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
}
