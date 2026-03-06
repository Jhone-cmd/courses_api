package br.com.jhonecmd.courses_api.exceptions;

public class UserIsNotTeacher extends RuntimeException {
    public UserIsNotTeacher() {
        super("The user is not a teacher!");
    }
}
