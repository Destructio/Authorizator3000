package com.github.destructio.authorizator3000.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class RoleAdminCreatedEvent extends ApplicationEvent {

    private final UUID adminRoleId;

    public RoleAdminCreatedEvent(Object source, UUID adminRoleId) {
        super(source);
        this.adminRoleId = adminRoleId;
    }

}
