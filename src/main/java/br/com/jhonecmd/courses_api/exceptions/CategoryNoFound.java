package br.com.jhonecmd.courses_api.exceptions;

public class CategoryNoFound extends RuntimeException {
    public CategoryNoFound() {
        super("Category not found!");
    }
}
