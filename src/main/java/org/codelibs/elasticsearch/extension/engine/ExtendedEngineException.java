package org.codelibs.elasticsearch.extension.engine;

public class ExtendedEngineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExtendedEngineException(String msg, Exception e) {
        super(msg, e);
    }

}
