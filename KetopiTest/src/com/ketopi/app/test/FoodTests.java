/**
 * 
 */
package com.ketopi.app.test;

import junit.framework.TestCase;

import com.ketopi.app.Food;

/**
 * @author Brad Dennis
 *
 */
public class FoodTests extends TestCase {

    private Food mFood;
    /**
     * @param name
     */
    public FoodTests(final String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mFood = new Food();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.ketopi.app.Food#Food()}.
     */
    public final void testFood() {
        assertTrue(new Food() != null);

    }

    /**
     * Test method for {@link com.ketopi.app.Food#Food(org.json.JSONObject)}.
     */
    public final void testFoodJSONObject() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#fromJSON(org.json.JSONObject)}.
     */
    public final void testFromJSON() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getAmount()}.
     */
    public final void testGetAmount() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getCalories()}.
     */
    public final void testGetCalories() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getCarbs()}.
     */
    public final void testGetCarbs() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getDescription()}.
     */
    public final void testGetDescription() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getFat()}.
     */
    public final void testGetFat() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getFiber()}.
     */
    public final void testGetFiber() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getGrams()}.
     */
    public final void testGetGrams() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getID()}.
     */
    public final void testGetID() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getMeasure()}.
     */
    public final void testGetMeasure() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getNdbNo()}.
     */
    public final void testGetNdbNo() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getNetCarbs()}.
     */
    public final void testGetNetCarbs() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getProtein()}.
     */
    public final void testGetProtein() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getServing()}.
     */
    public final void testGetServing() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link com.ketopi.app.Food#getSugars()}.
     */
    public final void testGetSugars() {
        fail("Not yet implemented"); // TODO
    }

}
