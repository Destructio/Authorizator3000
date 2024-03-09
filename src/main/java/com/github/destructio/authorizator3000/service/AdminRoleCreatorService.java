package com.github.destructio.authorizator3000.service;

import com.github.destructio.authorizator3000.events.RoleAdminCreatedEvent;
import com.github.destructio.authorizator3000.model.dto.RoleDto;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AdminRoleCreatorService {
    private final ApplicationEventPublisher eventPublisher;
    private final RoleService roleService;

    public AdminRoleCreatorService(ApplicationEventPublisher eventPublisher, RoleService roleService) {
        this.eventPublisher = eventPublisher;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    private void adminRoleCreator() {
        RoleDto adminRole = RoleDto.builder()
                .name("ADMIN")
                .build();
        roleService.createRole(adminRole);
        eventPublisher.publishEvent(new RoleAdminCreatedEvent(this));
    }
}
