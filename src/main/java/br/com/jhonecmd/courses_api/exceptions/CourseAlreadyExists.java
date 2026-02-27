package br.com.jhonecmd.courses_api.exceptions;

public class CourseAlreadyExists extends RuntimeException {
    public CourseAlreadyExists() {
        super("Course already exists!");
    }
}
