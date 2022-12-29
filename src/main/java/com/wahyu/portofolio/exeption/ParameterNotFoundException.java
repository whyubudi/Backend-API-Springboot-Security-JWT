package com.wahyu.portofolio.exeption;

public class ParameterNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -7011965426134599393L;

    public ParameterNotFoundException(String message) {
        super(message);
    }
}
