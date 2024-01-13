package com.harmonypark.harmonypark.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("Username not found");
    }
}
