package com.ketopi.core;


/**
 * The Class NoOp.  This class does nothing.  It's used to aid in verifying
 * branch test coverage.
 */
public final class NoOp {

    /** The Singleton. */
    private static NoOp singleton = new NoOp();

    /**
     * Get an instance of NoOp. This is used instead of a conventional constructor
     * because NoOp is a singleton.
     *
     * @return return the singleton instance.
     */
    public static NoOp getInstance() {
        return singleton;
    }

    /**
     * Private constructor blocks users from not using the singleton.
     */
    private NoOp() {
        // nothing
    }

    /**
     * Doesn't do anything!.
     */
    public void run() {
        // nothing
    }
}
