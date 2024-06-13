package com.rogeriogregorio.securityloginjwt.security.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class NotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
