package com.ketopi.test;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * The TestSuite to execute all Ketopi tests.
 */
public final class AllTests {

    /**
     * Suite.
     *
     * @return the test
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(SearchActivityTests.class);
        suite.addTestSuite(FoodTests.class);
        suite.addTestSuite(SearchTaskTests.class);
        //suite.addTestSuite(SearchResultsActivityTest.class);
        //$JUnit-END$
        return suite;
    }

    /**
     * Instantiates a new all tests.
     */
    private AllTests() {

    }

}