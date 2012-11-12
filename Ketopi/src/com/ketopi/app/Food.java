package com.ketopi.app;

import org.json.JSONObject;

/**
 * The Class Food.
 */
public class Food {

    /**
     * Factory method for JSON-Object conversion.
     *
     * @param json      the JSON object initializer.
     * @return food     the FOOD object.
     */
    public static Food fromJSON(final JSONObject json) {
        return new Food(json);
    }

    /** The Id. */
    private String mId = "";

    /** The Ndb no. */
    private String mNdbNo = "";

    /** The Description. */
    private String mDescription = "";

    /** The Amount. */
    private String mAmount = "";

    /** The Measure. */
    private String mMeasure = "";

    /** The Grams. */
    private String mGrams = "";

    /* Macros */
    /** The Calories. */
    private String mCalories = "";

    /** The Protein. */
    private String mProtein = "";

    /** The Fat. */
    private String mFat = "";

    /** The Carbs. */
    private String mCarbs = "";

    /** The Fiber. */
    private String mFiber = "";

    /** The Sugars. */
    private String mSugars = "";

    /** The Net carbs. */
    private String mNetCarbs = "";

    /**
     * Instantiates a new food.
     */
    public Food() {
        //Empty constructor.
    }

    /**
     * Instantiates a new food.
     *
     * @param jsonObject the json object
     */
    public Food(final JSONObject jsonObject) {

        mId = jsonObject.optString("id");
        setNdbNo(jsonObject.optString("ndb_no"));
        setDescription(jsonObject.optString("long_desc"));
        setAmount(jsonObject.optString("amount"));
        setMeasure(jsonObject.optString("measure"));
        setGrams(jsonObject.optString("grams"));
        setCarbs(jsonObject.optString("carbs"));
        setNetCarbs(jsonObject.optString("net_carbs"));
        setCalories(jsonObject.optString("calories"));
        setFat(jsonObject.optString("fat"));
        setProtein(jsonObject.optString("protein"));
        setFiber(jsonObject.optString("fiber"));
        setSugars(jsonObject.optString("sugars"));

    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public final String getAmount() {
        return mAmount;
    }

    /**
     * Gets the calories.
     *
     * @return the calories
     */
    public final String getCalories() {
        return mCalories;
    }

    /**
     * Gets the carbs.
     *
     * @return the carbs
     */
    public final String getCarbs() {
        return mCarbs;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public final String getDescription() {
        return mDescription;
    }

    /**
     * Gets the fat.
     *
     * @return the fat
     */
    public final String getFat() {
        return mFat;
    }

    /**
     * Gets the fiber.
     *
     * @return the fiber
     */
    public final String getFiber() {
        return mFiber;
    }

    /**
     * Gets the grams.
     *
     * @return the grams
     */
    public final String getGrams() {
        return mGrams;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public final String getID() {
        return mId;
    }

    /**
     * Gets the measure.
     *
     * @return the measure
     */
    public final String getMeasure() {
        return mMeasure;
    }

    /**
     * Gets the Nutrition Database Number.
     *
     * @return the Nutrition Database Number
     */
    public final String getNdbNo() {
        return mNdbNo;
    }

    /**
     * Gets the net carbs.
     *
     * @return the net carbs
     */
    public final String getNetCarbs() {
        return mNetCarbs;
    }

    /**
     * Gets the protein.
     *
     * @return the protein
     */
    public final String getProtein() {
        return mProtein;
    }

    /**
     * Gets the serving.
     *
     * @return the serving
     */
    public final String getServing() {
        return getAmount() + " " + getMeasure();
    }

    /**
     * Gets the sugars.
     *
     * @return the sugars
     */
    public final String getSugars() {
        return mSugars;
    }

    /**
     * @param amount the amount to set
     */
    private void setAmount(final String amount) {
        mAmount = amount;
    }

    /**
     * @param calories the calories to set
     */
    private void setCalories(final String calories) {
        mCalories = calories;
    }

    /**
     * @param carbs the carbs to set
     */
    private void setCarbs(final String carbs) {
        mCarbs = carbs;
    }

    /**
     * @param description the description to set
     */
    private void setDescription(final String description) {
        mDescription = description;
    }

    /**
     * @param fat the fat to set
     */
    private void setFat(final String fat) {
        mFat = fat;
    }

    /**
     * @param fiber the fiber to set
     */
    private void setFiber(final String fiber) {
        mFiber = fiber;
    }

    /**
     * @param grams the grams to set
     */
    private void setGrams(final String grams) {
        mGrams = grams;
    }

    /**
     * @param measure the measure to set
     */
    private void setMeasure(final String measure) {
        mMeasure = measure;
    }

    /**
     * @param ndbNo the ndbNo to set
     */
    private void setNdbNo(final String ndbNo) {
        mNdbNo = ndbNo;
    }

    /**
     * @param netCarbs the netCarbs to set
     */
    private void setNetCarbs(final String netCarbs) {
        mNetCarbs = netCarbs;
    }

    /**
     * @param protein the protein to set
     */
    private void setProtein(final String protein) {
        mProtein = protein;
    }

    /**
     * @param sugars the sugars to set
     */
    private void setSugars(final String sugars) {
        mSugars = sugars;
    }
}
