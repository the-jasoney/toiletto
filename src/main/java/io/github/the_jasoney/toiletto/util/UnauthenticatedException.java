package io.github.the_jasoney.toiletto.util;

public class UnauthenticatedException extends Exception {
    public UnauthenticatedException(String errorMessage) {
        super(errorMessage);
    }
}
