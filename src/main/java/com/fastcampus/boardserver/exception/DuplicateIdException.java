package com.fastcampus.boardserver.exception;

public class DuplicateIdException extends RuntimeException {

    public DuplicateIdException(String message) {
        super(message);
    }
}
