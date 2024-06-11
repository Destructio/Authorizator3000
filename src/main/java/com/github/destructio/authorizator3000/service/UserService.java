package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.exception.UserNotFoundException;
import com.github.destructio.authorizator3000.mapper.UserMapper;
import com.github.destructio.authorizator3000.api.dto.UserDto;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.provider.ProviderUser;
import com.github.destructio.authorizator3000.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.github.destructio.authorizator3000.provider.Provider.Auth3000;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setProvider(Auth3000);
        return userRepository.save(user);
    }

    public User createUser(ProviderUser providerUser) {
        User user = userMapper.toUser(providerUser);
        return userRepository.save(user);
    }

    public User createUser(OidcUser oidcUser) {
        User user = userMapper.toUser(oidcUser);
        return userRepository.save(user);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUser(ProviderUser providerUser) {
        return userRepository.findByEmail(providerUser.getEmail())
                .orElseGet(() -> createUser(providerUser));
    }

    public User getUser(OidcUser oidcUser) {
        return userRepository.findByEmail(oidcUser.getEmail())
                .orElseGet(() -> createUser(oidcUser));
    }

    public User updateUser(UUID id, UserDto userDto) {
        User user = getUser(id);
        userMapper.updateUser(userDto, user);
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
