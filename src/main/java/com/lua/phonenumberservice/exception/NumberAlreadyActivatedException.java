package com.lua.phonenumberservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Phone is already activated")
public class NumberAlreadyActivatedException extends RuntimeException {
    public NumberAlreadyActivatedException() {
        super();
    }

    public NumberAlreadyActivatedException(String msg) {
        super(msg);
    }
}
