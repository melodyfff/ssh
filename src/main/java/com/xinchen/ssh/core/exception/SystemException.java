package com.xinchen.ssh.core.exception;
/**
 * Description: SystemException Exception
 * version: 1.0
 * date:
 */
public class SystemException extends RuntimeException{
    //
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public SystemException() {
    }

    /**
     *
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     *
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     */
    public SystemException(Throwable cause) {
        super(cause);
    }
}
