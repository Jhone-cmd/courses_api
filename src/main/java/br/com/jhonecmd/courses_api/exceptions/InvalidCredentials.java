package br.com.jhonecmd.courses_api.exceptions;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials() {
        super("Invalid Credentials!");
    }
}