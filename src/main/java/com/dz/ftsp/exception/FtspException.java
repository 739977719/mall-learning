package com.dz.ftsp.exception;

/**
 * 自定义异常公共父类
 */
public class FtspException extends RuntimeException{
    /**
     * 构造函数
     */
    public FtspException() {
        super();
    }

    /**
     * 构造函数
     * @param message
     */
    public FtspException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * @param message
     * @param cause
     */
    public FtspException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数
     * @param cause
     */
    public FtspException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    protected FtspException(String message, Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
