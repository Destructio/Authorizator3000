package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.exceptions.UserNotFoundException;
import com.github.destructio.authorizator3000.mapper.UserMapper;
import com.github.destructio.authorizator3000.model.dto.UserDto;
import com.github.destructio.authorizator3000.model.entity.User;
import com.github.destructio.authorizator3000.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.github.destructio.authorizator3000.enums.JpaUserProvider.Auth3000;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        user.setProvider(Auth3000);
        return userRepository.save(user);
    }

    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
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
