package com.levin.sjf4j.core;

public class JsonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public JsonException(String msg) {
        super(msg);
    }

    public JsonException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Creates exception with the specified cause. Consider using
     * {@link #JsonException(String, Throwable)} instead if you can
     * describe what actually happened.
     *
     * @param cause root exception that caused this exception to be thrown.
     */
    public JsonException(Throwable cause) {
        super(cause);
    }

    public static JsonException wrapAsJsonException(Throwable ex){
        if(ex instanceof JsonException){
            return  (JsonException)ex;
        }
        return new JsonException(ex.getMessage(), ex);
    }

}
