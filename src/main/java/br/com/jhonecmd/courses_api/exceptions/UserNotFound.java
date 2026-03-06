package br.com.jhonecmd.courses_api.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found!");
    }
}
