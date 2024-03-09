package com.github.destructio.authorizator3000.model.oauth2.providers;

import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.model.oauth2.JpaOAuth2User;

import java.util.Map;

public class GitHubUser extends JpaOAuth2User {

    public GitHubUser(Map<String, Object> attributes) {
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
