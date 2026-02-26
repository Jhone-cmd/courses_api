package br.com.jhonecmd.courses_api.exceptions;

public class CategoryAlreadyExists extends RuntimeException {
    public CategoryAlreadyExists() {
        super("CAtegory Already exists!");
    }
}
