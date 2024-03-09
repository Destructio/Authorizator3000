package com.github.destructio.authorizator3000.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID uuid) {
        super("User with id " + uuid + " was not found!");
    }

}
