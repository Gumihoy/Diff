package com.github.gumihoy.diff.exception;

/**
 * @author Kent <ouyangkongtong@gmail.com>
 * Created on 2021-10-12
 */
public class DiffException extends RuntimeException {

    public DiffException() {
    }

    public DiffException(String message) {
        super(message);
    }

    public DiffException(String message, Throwable cause) {
        super(message, cause);
    }
}
