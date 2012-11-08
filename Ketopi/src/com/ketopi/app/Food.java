package com.ketopi.app;

import org.json.JSONObject;

/**
 * The Class Food.
 */
public class Food {

    /** The Id. */
    private String mId              = "";

    /** The Ndb no. */
    private String mNdbNo           = "";

    /** The Description. */
    private String mDescription     = "";

    /** The Amount. */
    private String mAmount          = "";

    /** The Measure. */
    private String mMeasure         = "";

    /** The Grams. */
    private String mGrams           = "";

    /* Macros */
    /** The Calories. */
    private String mCalories        = "";

    /** The Protein. */
    private String mProtein         = "";

    /** The Fat. */
    private String mFat             = "";

    /** The Carbs. */
    private String mCarbs           = "";

    /** The Fiber. */
    private String mFiber           = "";

    /** The Sugars. */
    private String mSugars          = "";

    /** The Net carbs. */
    private String mNetCarbs        = "";

    /* Fats */
    /** The Mono unsaturated. */
    private String mMonoUnsaturated = "";

    /** The Poly unsaturated. */
    private String mPolyUnsaturated = "";

    /** The Trans fat. */
    private String mTransFat        = "";

    /* Carbs */
    /** The Sucrose. */
    private String mSucrose         = "";

    /** The Fructose. */
    private String mFructose        = "";

    /** The Lactose. */
    private String mLactose         = "";

    /** The Maltose. */
    private String mMaltose         = "";

    /** The Galactose. */
    private String mGalactose       = "";

    /** The Starch. */
    private String mStarch          = "";

    /* Electrolytes */
    /** The Calcium. */
    private String mCalcium         = "";

    /** The Magnesium. */
    private String mMagnesium       = "";

    /** The Potassium. */
    private String mPotassium       = "";

    /** The Sodium. */
    private String mSodium          = "";

    /**
     * Instantiates a new food.
     */
    public Food() {
    }

    /*
     * {"ndb_no":"21473", "long_desc":
     * "DIGIORNO Pizza, cheese topping, cheese stuffed crust, frozen, baked",
     * "carbs":"30", "calories":"279", "fat":"12", "protein":"13", "fiber":"2",
     * "sugars":null, "net_carbs":"28", "amount":"1", "measure":"slice 1\/4 of
     * pie", "grams":"164", "rank":"4.67262887954712"}
     */

    /**
     * Instantiates a new food.
     *
     * @param jsonObject the json object
     */
    public Food(final JSONObject jsonObject) {

        mId = jsonObject.optString("id");
        mNdbNo = jsonObject.optString("ndb_no");
        setDescription(jsonObject.optString("long_desc"));
        setAmount(jsonObject.optString("amount"));
        setMeasure(jsonObject.optString("measure"));
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
     * jAutodoc
     *
     * @return the amount
     */
    public final String getAmount() {
        return mAmount;
    }

    /**
     * Gets the calcium.
     * jAutodoc
     *
     * @return the calcium
     */
    public final String getCalcium() {
        return mCalcium;
    }

    /**
     * Gets the calories.
     * jAutodoc
     *
     * @return the calories
     */
    public final String getCalories() {
        return mCalories;
    }

    /**
     * Gets the carbs.
     * jAutodoc
     *
     * @return the carbs
     */
    public final String getCarbs() {
        return mCarbs;
    }

    /**
     * Gets the description.
     * jAutodoc
     *
     * @return the description
     */
    public final String getDescription() {
        return mDescription;
    }

    /**
     * Gets the fat.
     * jAutodoc
     *
     * @return the fat
     */
    public final String getFat() {
        return mFat;
    }

    /**
     * Gets the fiber.
     * jAutodoc
     *
     * @return the fiber
     */
    public final String getFiber() {
        return mFiber;
    }

    /**
     * Gets the fructose.
     * jAutodoc
     *
     * @return the fructose
     */
    public final String getFructose() {
        return mFructose;
    }

    /**
     * Gets the galactose.
     * jAutodoc
     *
     * @return the galactose
     */
    public final String getGalactose() {
        return mGalactose;
    }

    /**
     * Gets the grams.
     * jAutodoc
     *
     * @return the grams
     */
    public final String getGrams() {
        return mGrams;
    }

    /**
     * Gets the id.
     * jAutodoc
     *
     * @return the id
     */
    public final String getID() {
        return mId;
    }

    /**
     * Gets the lactose.
     * jAutodoc
     *
     * @return the lactose
     */
    public final String getLactose() {
        return mLactose;
    }

    /**
     * Gets the magnesium.
     *
     * @return the magnesium
     */
    public final String getMagnesium() {
        return mMagnesium;
    }

    /**
     * Gets the maltose.
     *
     * @return the maltose
     */
    public final String getMaltose() {
        return mMaltose;
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
     * Gets the mono unsaturated.
     *
     * @return the mono unsaturated
     */
    public final String getMonoUnsaturated() {
        return mMonoUnsaturated;
    }

    /**
     * Gets the ndb no.
     *
     * @return the ndb no
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
     * Gets the poly unsaturated.
     *
     * @return the poly unsaturated
     */
    public final String getPolyUnsaturated() {
        return mPolyUnsaturated;
    }

    /**
     * Gets the potassium.
     *
     * @return the potassium
     */
    public final String getPotassium() {
        return mPotassium;
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
     * Gets the sodium.
     *
     * @return the sodium
     */
    public final String getSodium() {
        return mSodium;
    }

    /**
     * Gets the starch.
     * jAutodoc
     *
     * @return the starch
     */
    public final String getStarch() {
        return mStarch;
    }

    /**
     * Gets the sucrose.
     * jAutodoc
     *
     * @return the sucrose
     */
    public final String getSucrose() {
        return mSucrose;
    }

    /**
     * Gets the sugars.
     * jAutodoc
     *
     * @return the sugars
     */
    public final String getSugars() {
        return mSugars;
    }

    /**
     * Gets the trans fat.
     * jAutodoc
     *
     * @return the trans fat
     */
    public final String getTransFat() {
        return mTransFat;
    }

    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public final void setAmount(final String amount) {
        mAmount = amount;
    }

    /**
     * Sets the calcium.
     *
     * @param calcium the new calcium
     */
    public final void setCalcium(final String calcium) {
        mCalcium = calcium;
    }

    /**
     * Sets the calories.
     *
     * @param calories the new calories
     */
    public final void setCalories(final String calories) {
        mCalories = calories;
    }

    /**
     * Sets the carbs.
     *
     * @param carbs the new carbs
     */
    public final void setCarbs(final String carbs) {
        mCarbs = carbs;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public final void setDescription(final String description) {
        mDescription = description;
    }

    /**
     * Sets the fat.
     *
     * @param fat the new fat
     */
    public final void setFat(final String fat) {
        mFat = fat;
    }

    /**
     * Sets the fiber.
     *
     * @param fiber the new fiber
     */
    public final void setFiber(final String fiber) {
        mFiber = fiber;
    }

    /**
     * Sets the fructose.
     *
     * @param fructose the new fructose
     */
    public final void setFructose(final String fructose) {
        mFructose = fructose;
    }

    /**
     * Sets the galactose.
     *
     * @param galactose the new galactose
     */
    public final void setGalactose(final String galactose) {
        mGalactose = galactose;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public final void setID(final String id) {
        this.mId = id;
    }

    /**
     * Sets the lactose.
     *
     * @param lactose the new lactose
     */
    public final void setLactose(final String lactose) {
        mLactose = lactose;
    }

    /**
     * Sets the magnesium.
     *
     * @param magnesium the new magnesium
     */
    public final void setMagnesium(final String magnesium) {
        mMagnesium = magnesium;
    }

    /**
     * Sets the maltose.
     *
     * @param maltose the new maltose
     */
    public final void setMaltose(final String maltose) {
        mMaltose = maltose;
    }

    /**
     * Sets the measure.
     *
     * @param measure the new measure
     */
    public final void setMeasure(final String measure) {
        mMeasure = measure;
    }

    /**
     * Sets the mono unsaturated.
     *
     * @param monoUnsaturated the new mono unsaturated
     */
    public final void setMonoUnsaturated(final String monoUnsaturated) {
        mMonoUnsaturated = monoUnsaturated;
    }

    /**
     * Sets the ndb no.
     *
     * @param ndbno the new ndb no
     */
    public final void setNdbNo(final String ndbno) {
        mNdbNo = ndbno;
    }

    /**
     * Sets the net carbs.
     *
     * @param netCarbs the new net carbs
     */
    public final void setNetCarbs(final String netCarbs) {
        mNetCarbs = netCarbs;
    }

    /**
     * Sets the poly unsaturated.
     *
     * @param polyUnsaturated the new poly unsaturated
     */
    public final void setPolyUnsaturated(final String polyUnsaturated) {
        mPolyUnsaturated = polyUnsaturated;
    }

    /**
     * Sets the potassium.
     *
     * @param potassium the new potassium
     */
    public final void setPotassium(final String potassium) {
        mPotassium = potassium;
    }

    /**
     * Sets the protein.
     *
     * @param protein the new protein
     */
    public final void setProtein(final String protein) {
        mProtein = protein;
    }

    /**
     * Sets the sodium.
     *
     * @param sodium the new sodium
     */
    public final void setSodium(final String sodium) {
        mSodium = sodium;
    }

    /**
     * Sets the starch.
     *
     * @param starch the new starch
     */
    public final void setStarch(final String starch) {
        mStarch = starch;
    }

    /**
     * Sets the sucrose.
     *
     * @param sucrose the new sucrose
     */
    public final void setSucrose(final String sucrose) {
        mSucrose = sucrose;
    }

    /**
     * Sets the sugars.
     *
     * @param sugars the new sugars
     */
    public final void setSugars(final String sugars) {
        mSugars = sugars;
    }

    /**
     * Sets the trans fat.
     *
     * @param transFat the new trans fat
     */
    public final void setTransFat(final String transFat) {
        mTransFat = transFat;
    }

}
