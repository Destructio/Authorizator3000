package com.github.destructio.authorizator3000.exceptions;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(UUID uuid) {
        super("Role with id " + uuid + " was not found!");
    }

    public RoleNotFoundException(String name) {
        super("Role with name " + name + " was not found!");
    }
}
