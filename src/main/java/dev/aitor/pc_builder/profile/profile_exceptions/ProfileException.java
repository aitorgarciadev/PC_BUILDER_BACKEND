package dev.aitor.pc_builder.profile.profile_exceptions;

public class ProfileException extends RuntimeException{
    public ProfileException(String message) {
        super(message);
    }

    public ProfileException(String message, Throwable cause) {
        super(message, cause);
    }
}
