package com.thinvent.rules.dao.exception;

/**
 * @author arnxdr
 */
public class WlsconnectDaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public WlsconnectDaoException() {
        super();
    }

    public WlsconnectDaoException(String message) {
        super(message);
    }

    public WlsconnectDaoException(String message, Throwable t) {
        super(message, t);
    }

    public WlsconnectDaoException(Throwable t) {
        super(t);
    }
}
