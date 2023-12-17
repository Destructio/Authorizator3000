package com.github.destructio.authorizator3000.model.user.oauth2.provider;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.user.oauth2.OAuth2UserDto;

import java.util.Map;

public class GitHubUserDto extends OAuth2UserDto {
    public GitHubUserDto(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("login");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }

    @Override
    public JpaUserProvider getAuthProvider() {
        return JpaUserProvider.GitHub;
    }
}
