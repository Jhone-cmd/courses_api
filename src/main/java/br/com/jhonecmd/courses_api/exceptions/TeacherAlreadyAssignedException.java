package br.com.jhonecmd.courses_api.exceptions;

public class TeacherAlreadyAssignedException extends RuntimeException {
    public TeacherAlreadyAssignedException() {
        super("This professor is already enrolled in another course.");
    }
}
