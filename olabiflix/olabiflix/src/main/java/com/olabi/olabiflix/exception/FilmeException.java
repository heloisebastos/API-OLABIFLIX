package com.olabi.olabiflix.exception;

public class FilmeException extends RuntimeException {

    public FilmeException(String message) {
        super(message);
    }

    public static class DuplicateFilmeException extends FilmeException {
        public DuplicateFilmeException() {
            super("filme já cadastrado");
        }

    }
}
