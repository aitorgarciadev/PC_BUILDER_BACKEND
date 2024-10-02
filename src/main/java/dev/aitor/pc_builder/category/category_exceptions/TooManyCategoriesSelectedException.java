package dev.aitor.pc_builder.category.category_exceptions;

public class TooManyCategoriesSelectedException extends RuntimeException {
    public TooManyCategoriesSelectedException(String message) {
        super(message);
    }
}
