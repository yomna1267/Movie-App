package com.example.demo.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String s) {
        super(s);
    }
}
