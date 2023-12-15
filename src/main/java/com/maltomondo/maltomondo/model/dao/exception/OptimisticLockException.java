package com.maltomondo.maltomondo.model.dao.exception;

public class OptimisticLockException extends Exception {
    public OptimisticLockException() {
    }

    public OptimisticLockException(String msg) {
        super(msg);
    }
}
