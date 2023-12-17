package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.controller.dto.UserDto;
import com.github.destructio.authorizator3000.enums.JpaUserProvider;
import com.github.destructio.authorizator3000.mapper.UserMapper;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.model.User;
import com.github.destructio.authorizator3000.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private static final ResponseStatusException USER_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found!");

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User createUser(UserDto userDto) {
//        User entity = userMapper.toEntity(userDto);

        User entity = new User();
        entity.setUsername(userDto.getUsername());
        entity.setPassword(getHash(userDto.getPassword()));
        entity.setEmail(userDto.getEmail());
        entity.setProvider(JpaUserProvider.Auth3000);

        entity.setRoles(userDto.getRolesId().stream()
                .map(s -> roleService.getRole(UUID.fromString(s)))
                .collect(Collectors.toSet())
        );

        userRepository.save(entity);
        return entity;
    }

    private String getHash(String password) {
        return passwordEncoder.encode(password);
    }

    public void updateUser(UUID id, UserDto userDto) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> USER_NOT_FOUND);
        // TODO: Mapping
        userRepository.save(entity);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> USER_NOT_FOUND);
    }

    public void deleteUser(UUID id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> USER_NOT_FOUND);
        userRepository.delete(entity);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(String name) {
        return userRepository.findByUsername(name);
    }

    public void createBasicUser() {
        Role userRole = roleService.createUserRole();

        User basicUser = new User();
        basicUser.setUsername("test");
        basicUser.setPassword(getHash("test"));
        basicUser.setName("Test Test");
        basicUser.setEmail("test@test.test");
        basicUser.setProvider(JpaUserProvider.Auth3000);
        basicUser.setRoles(Set.of(userRole));

        userRepository.saveAndFlush(basicUser);
    }

    public void createAdminUser() {
        Role admin = roleService.createAdminRole();

        User basicUser = new User();
        basicUser.setUsername("admin");
        basicUser.setPassword(getHash("admin"));
        basicUser.setName("Admin Admin");
        basicUser.setEmail("admin@admin.admin");
        basicUser.setProvider(JpaUserProvider.Auth3000);
        basicUser.setRoles(Set.of(admin));

        userRepository.saveAndFlush(basicUser);
    }
}
