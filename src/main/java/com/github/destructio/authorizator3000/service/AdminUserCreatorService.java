package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.events.RoleAdminCreatedEvent;
import com.github.destructio.authorizator3000.model.dto.RoleDto;
import com.github.destructio.authorizator3000.model.dto.UserDto;
import com.github.destructio.authorizator3000.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class AdminUserCreatorService {

    private final UserService userService;

    public AdminUserCreatorService(UserService userService) {
        this.userService = userService;
    }

    @EventListener(RoleAdminCreatedEvent.class)
    private void createAdminUser() {
        RoleDto adminRole = RoleDto.builder()
                .name("ADMIN")
                .build();

        String adminUser = System.getenv("AUTH3_ADMIN_USER");
        String adminPass = System.getenv("AUTH3_ADMIN_PASS");

        UserDto admin = UserDto.builder()
                .username(adminUser)
                .password(adminPass)
                .roles(Set.of(adminRole))
                .build();

        User user = userService.createUser(admin);
        log.info("Created ADMIN user: {}", user.getUsername());
    }

}
