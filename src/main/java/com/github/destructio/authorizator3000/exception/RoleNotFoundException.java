package com.github.destructio.authorizator3000.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(UUID uuid) {
        super("Role with id " + uuid + " was not found!");
    }

    public RoleNotFoundException(String name) {
        super("Role with name " + name + " was not found!");
    }
}
