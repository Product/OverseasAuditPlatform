package com.thinvent.rules.dao.exception;

/**
 * @author arnxdr
 */
public class TwgFileDaoException extends WlsconnectDaoException{

    private static final long serialVersionUID = 1L;

    public TwgFileDaoException() {
        super();
    }

    public TwgFileDaoException(String message) {
        super(message);
    }

    public TwgFileDaoException(String message, Throwable t) {
        super(message, t);
    }

    public TwgFileDaoException(Throwable t) {
        super(t);
    }
}
