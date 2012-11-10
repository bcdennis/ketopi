package com.ketopi.rest;

/**
 * @author Brad Dennis
 *
 * Strongly Typed REST Exception
 *
 */
public class RestException extends Exception {

    /**
     * Serializability requirement.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     *
     * @param message the Error message.
     */
    public RestException(final String message) {
        super(message);
    }

}
