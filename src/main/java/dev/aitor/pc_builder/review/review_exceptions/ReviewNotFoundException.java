package dev.aitor.pc_builder.review.review_exceptions;


public class ReviewNotFoundException extends ReviewException{
    
    public ReviewNotFoundException(String message) {
        super(message);
    }
    
    public ReviewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
