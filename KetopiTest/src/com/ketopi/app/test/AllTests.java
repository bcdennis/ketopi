package com.ketopi.app.test;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * The TestSuite to execute all Ketopi tests.
 */
public final class AllTests {

    /**
     * Instantiates a new all tests.
     */
    private AllTests() {

    }

    /**
     * Suite.
     *
     * @return the test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(MainActivityTests.class);
        //suite.addTestSuite(SearchResultsActivityTest.class);
        //$JUnit-END$
        return suite;
    }

}