package br.com.jhonecmd.courses_api.exceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
        super("User already exists!");
    }
}
