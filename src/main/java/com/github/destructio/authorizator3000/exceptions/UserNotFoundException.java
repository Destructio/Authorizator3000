package com.github.destructio.authorizator3000.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID uuid) {
        super("User with id " + uuid + " was not found!");
    }

}
