package br.com.jhonecmd.courses_api.exceptions;

public class CourseNotFound extends RuntimeException {
    public CourseNotFound() {
        super("Course not found!");
    }
}
