/**
 * 
 */
package com.ketopi.test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.ketopi.core.Food;

/**
 * @author Brad Dennis
 *
 */
public class FoodTests extends TestCase {

    private String mJson;
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
        mJson = "{\"ndb_no\": \"21228\",\"long_desc\": \"McDONALD'S, Hamburger\", \"carbs\": \"30\",\"calories\": \"264\",\"fat\": \"10\",\"protein\": \"13\", \"fiber\": \"1\",\"sugars\": \"6\",\"net_carbs\": \"29\",\"amount\": \"1\",\"measure\": \"sandwich\",\"grams\": \"95\", \"rank\": \"5.65691137313843\"}";

        JSONObject json = new JSONObject(mJson);
        mFood = Food.fromJSON(json);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testEmptyField() {
        String empty = "{\"ndb_no\": \"\",\"long_desc\": \"McDONALD'S, Hamburger\", \"carbs\": \"30\",\"calories\": \"264\",\"fat\": \"10\",\"protein\": \"13\", \"fiber\": \"1\",\"sugars\": \"6\",\"net_carbs\": \"29\",\"amount\": \"1\",\"measure\": \"sandwich\",\"grams\": \"95\", \"rank\": \"5.65691137313843\"}";

        JSONObject json;
        try {
            json = new JSONObject(empty);
            mFood = Food.fromJSON(json);

            assertTrue("".equals(mFood.getNdbNo()));
        } catch (JSONException e) {
            fail("Empty fields should be supported.");
        }

    }

    /**
     * Test method for {@link com.ketopi.core.Food#Food()}.
     */
    public final void testFood() {
        assertTrue(new Food() != null);

    }
    /**
     * Test method for {@link com.ketopi.core.Food#Food(org.json.JSONObject)}.
     */
    public final void testFoodJSONObject() {
        try {
            assertTrue(new Food(new JSONObject(mJson)) != null);
        } catch (JSONException e) {
            fail("JSON Exception was thrown: " + e.getMessage());
        }
    }

    /**
     * Test method for {@link com.ketopi.core.Food#fromJSON(org.json.JSONObject)}.
     */
    public final void testFromJSON() {
        try {
            assertTrue(Food.fromJSON(new JSONObject(mJson)) != null);
        } catch (JSONException e) {
            fail("JSON Exception was thrown: " + e.getMessage());
        }
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getAmount()}.
     */
    public final void testGetAmount() {
        assertTrue("1".equals(mFood.getAmount()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getCalories()}.
     */
    public final void testGetCalories() {
        assertTrue("264".equals(mFood.getCalories()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getCarbs()}.
     */
    public final void testGetCarbs() {
        assertTrue("30".equals(mFood.getCarbs()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getDescription()}.
     */
    public final void testGetDescription() {
        assertTrue("McDONALD'S, Hamburger".equals(mFood.getDescription()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getFat()}.
     */
    public final void testGetFat() {
        assertTrue("10".equals(mFood.getFat()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getFiber()}.
     */
    public final void testGetFiber() {
        assertTrue("1".equals(mFood.getFiber()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getGrams()}.
     */
    public final void testGetGrams() {
        assertTrue("95".equals(mFood.getGrams()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getID()}.
     */
    public final void testGetID() {
        assertTrue("".equals(mFood.getID()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getMeasure()}.
     */
    public final void testGetMeasure() {
        assertTrue("sandwich".equals(mFood.getMeasure()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getNdbNo()}.
     */
    public final void testGetNdbNo() {
        assertTrue("21228".equals(mFood.getNdbNo()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getNetCarbs()}.
     */
    public final void testGetNetCarbs() {
        assertTrue("29".equals(mFood.getNetCarbs()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getProtein()}.
     */
    public final void testGetProtein() {
        assertTrue("13".equals(mFood.getProtein()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getServing()}.
     */
    public final void testGetServing() {
        assertTrue("1 sandwich".equals(mFood.getServing()));
    }

    /**
     * Test method for {@link com.ketopi.core.Food#getSugars()}.
     */

    public final void testGetSugars() {
        assertTrue("6".equals(mFood.getSugars()));
    }

    /**
     * Tests for missing fields.
     */
    public final void testMissingJSON() {
        String missing = "{\"long_desc\": \"McDONALD'S, Hamburger\", \"carbs\": \"30\",\"calories\": \"264\",\"fat\": \"10\",\"protein\": \"13\", \"fiber\": \"1\",\"sugars\": \"6\",\"net_carbs\": \"29\",\"amount\": \"1\",\"measure\": \"sandwich\",\"grams\": \"95\", \"rank\": \"5.65691137313843\"}";

        JSONObject json;
        try {
            json = new JSONObject(missing);
            mFood = Food.fromJSON(json);

            assertTrue("".equals(mFood.getNdbNo()));

        } catch (JSONException e) {
            fail("Food should handle missing fields.");
        }
    }

}