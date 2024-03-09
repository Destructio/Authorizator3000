package com.github.destructio.authorizator3000.events;

import org.springframework.context.ApplicationEvent;

public class RoleAdminCreatedEvent extends ApplicationEvent {

    public RoleAdminCreatedEvent(Object source) {
        super(source);
    }
}
