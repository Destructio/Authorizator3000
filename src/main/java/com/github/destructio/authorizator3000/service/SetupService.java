package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.api.dto.UserDto;
import com.github.destructio.authorizator3000.event.RoleAdminCreatedEvent;
import com.github.destructio.authorizator3000.api.dto.RoleDto;
import com.github.destructio.authorizator3000.model.Role;
import com.github.destructio.authorizator3000.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetupService {
    private final ApplicationEventPublisher eventPublisher;
    private final RoleService roleService;
    private final UserService userService;

    @Value("${AUTH3_ADMIN_USER:admin}")
    String adminUser;

    @Value("${AUTH3_ADMIN_PASSWORD:}")
    String adminPass;

    @EventListener(ApplicationReadyEvent.class)
    private void checkIfExistAdminRole() {
        if (roleService.getRole("ADMIN").isPresent()) {
            log.info("ADMIN role exists! Skipping USER role creation...");
            return;
        }
        RoleDto adminRole = new RoleDto("ADMIN");
        Role role = roleService.createRole(adminRole);
        RoleAdminCreatedEvent roleAdminEvent = new RoleAdminCreatedEvent(this, role.getId());
        eventPublisher.publishEvent(roleAdminEvent);
    }

    @EventListener(ApplicationReadyEvent.class)
    private void checkIfExistUserRole() {
        if (roleService.getRole("USER").isPresent()) {
            log.info("USER role exists! Skipping USER role creation...");
            return;
        }
        RoleDto userRole = new RoleDto("USER");
        roleService.createRole(userRole);
    }

    @EventListener(RoleAdminCreatedEvent.class)
    private void createAdminUser(RoleAdminCreatedEvent event) {
        UUID adminRoleId = event.getAdminRoleId();

        if (adminPass.isEmpty()) {
            adminPass = UUID.randomUUID().toString().replaceAll("-", "");
            log.info("Generated password: {} for default user: {}", adminPass, adminUser);
        }

        UserDto admin = UserDto.builder()
                .username(adminUser)
                .password(adminPass)
                .roles(Set.of(adminRoleId))
                .build();

        User user = userService.createUser(admin);
        log.info("Created ADMIN user: {}", user.getUsername());
    }
}
