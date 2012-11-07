package com.ketopi.app;

import org.json.JSONObject;

public class Food {

	private String mId = "";
	private String mNdb_No = "";
	private String mDescription = "";
	private String mAmount = "";
	private String mMeasure = "";
	private String mGrams = "";

	/*  Macros */
	private String mCalories = "";
	private String mProtein = "";
	private String mFat = "";
	private String mCarbs = "";
	private String mFiber = "";
	private String mSugars = "";
	private String mNetCarbs = "";

	/* Fats */
	private String mMonoUnsaturated = "";
	private String mPolyUnsaturated = "";
	private String mTransFat = "";

	/* Carbs */
	private String mSucrose = "";
	private String mFructose = "";
	private String mLactose = "";
	private String mMaltose = "";
	private String mGalactose = "";
	private String mStarch = "";

	/* Electrolytes */
	private String mCalcium = "";
	private String mMagnesium = "";
	private String mPotassium = "";
	private String mSodium = "";


	public Food() {
	}
	/*
	 * {"ndb_no":"21473", "long_desc":
	 * "DIGIORNO Pizza, cheese topping, cheese stuffed crust, frozen, baked",
	 * "carbs":"30", "calories":"279", "fat":"12", "protein":"13", "fiber":"2",
	 * "sugars":null, "net_carbs":"28", "amount":"1", "measure":"slice 1\/4 of
	 * pie", "grams":"164", "rank":"4.67262887954712"}
	 */

	public Food(final JSONObject jsonObject) {
		mId = jsonObject.optString("id");
		mNdb_No = jsonObject.optString("ndb_no");
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

	public String getAmount() {
		return mAmount;
	}

	/**
	 * @return the calcium
	 */
	public String getCalcium() {
		return mCalcium;
	}

	/**
	 * @return the calories
	 */
	public String getCalories() {
		return mCalories;
	}
	/**
	 * @return the carbs
	 */
	public String getCarbs() {
		return mCarbs;
	}
	/**
	 * @return the food
	 */
	public String getDescription() {
		return mDescription;
	}
	/**
	 * @return the fat
	 */
	public String getFat() {
		return mFat;
	}
	/**
	 * @return the fiber
	 */
	public String getFiber() {
		return mFiber;
	}

	/**
	 * @return the fructose
	 */
	public String getFructose() {
		return mFructose;
	}
	/**
	 * @return the galactose
	 */
	public String getGalactose() {
		return mGalactose;
	}
	/**
	 * @return the grams
	 */
	public String getGrams() {
		return mGrams;
	}
	public String getID() {
		return mId;
	}
	/**
	 * @return the lactose
	 */
	public String getLactose() {
		return mLactose;
	}
	/**
	 * @return the magnesium
	 */
	public String getMagnesium() {
		return mMagnesium;
	}
	/**
	 * @return the maltose
	 */
	public String getMaltose() {
		return mMaltose;
	}
	/**
	 * @return the measure
	 */
	public String getMeasure() {
		return mMeasure;
	}

	/**
	 * @return the monoUnsaturated
	 */
	public String getMonoUnsaturated() {
		return mMonoUnsaturated;
	}
	/**
	 * @return the ndb_No
	 */
	public String getNdbNo() {
		return mNdb_No;
	}

	public String getNetCarbs() {
		return mNetCarbs;
	}

	/**
	 * @return the polyUnsaturated
	 */
	public String getPolyUnsaturated() {
		return mPolyUnsaturated;
	}

	/**
	 * @return the potassium
	 */
	public String getPotassium() {
		return mPotassium;
	}

	/**
	 * @return the protein
	 */
	public String getProtein() {
		return mProtein;
	}

	/**
	 * @return the serving
	 */
	public String getServing() {
		return getAmount() + " " + getMeasure();
	}
	/**
	 * @return the sodium
	 */
	public String getSodium() {
		return mSodium;
	}
	/**
	 * @return the starch
	 */
	public String getStarch() {
		return mStarch;
	}
	/**
	 * @return the sucrose
	 */
	public String getSucrose() {
		return mSucrose;
	}
	/**
	 * @return the sugars
	 */
	public String getSugars() {
		return mSugars;
	}
	/**
	 * @return the transFat
	 */
	public String getTransFat() {
		return mTransFat;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(final String amount) {
		mAmount = amount;
	}

	/**
	 * @param calcium the calcium to set
	 */
	public void setCalcium(final String calcium) {
		mCalcium = calcium;
	}

	/**
	 * @param calories the calories to set
	 */
	public void setCalories(final String calories) {
		mCalories = calories;
	}

	/**
	 * @param carbs the carbs to set
	 */
	public void setCarbs(final String carbs) {
		mCarbs = carbs;
	}
	/**
	 * @param Description
	 *            of the food to set
	 */
	public void setDescription(final String description) {
		mDescription = description;
	}
	/**
	 * @param fat the fat to set
	 */
	public void setFat(final String fat) {
		mFat = fat;
	}
	/**
	 * @param fiber the fiber to set
	 */
	public void setFiber(final String fiber) {
		mFiber = fiber;
	}
	/**
	 * @param fructose the fructose to set
	 */
	public void setFructose(final String fructose) {
		mFructose = fructose;
	}
	/**
	 * @param galactose the galactose to set
	 */
	public void setGalactose(final String galactose) {
		mGalactose = galactose;
	}
	public void setID(final String mId) {
		this.mId = mId;
	}
	/**
	 * @param lactose the lactose to set
	 */
	public void setLactose(final String lactose) {
		mLactose = lactose;
	}
	/**
	 * @param magnesium the magnesium to set
	 */
	public void setMagnesium(final String magnesium) {
		mMagnesium = magnesium;
	}
	/**
	 * @param maltose the maltose to set
	 */
	public void setMaltose(final String maltose) {
		mMaltose = maltose;
	}
	/**
	 * @param measure the measure to set
	 */
	public void setMeasure(final String measure) {
		mMeasure = measure;
	}
	/**
	 * @param monoUnsaturated the monoUnsaturated to set
	 */
	public void setMonoUnsaturated(final String monoUnsaturated) {
		mMonoUnsaturated = monoUnsaturated;
	}
	/**
	 * @param ndb_No the ndb_No to set
	 */
	public void setNdbNo(final String ndb_No) {
		mNdb_No = ndb_No;
	}
	/**
	 * @param carbs
	 *            the carbs to set
	 */
	public void setNetCarbs(final String netCarbs) {
		mNetCarbs = netCarbs;
	}

	/**
	 * @param polyUnsaturated the polyUnsaturated to set
	 */
	public void setPolyUnsaturated(final String polyUnsaturated) {
		mPolyUnsaturated = polyUnsaturated;
	}
	/**
	 * @param potassium the potassium to set
	 */
	public void setPotassium(final String potassium) {
		mPotassium = potassium;
	}
	/**
	 * @param protein the protein to set
	 */
	public void setProtein(final String protein) {
		mProtein = protein;
	}
	/**
	 * @param sodium the sodium to set
	 */
	public void setSodium(final String sodium) {
		mSodium = sodium;
	}

	/**
	 * @param starch the starch to set
	 */
	public void setStarch(final String starch) {
		mStarch = starch;
	}

	/**
	 * @param sucrose the sucrose to set
	 */
	public void setSucrose(final String sucrose) {
		mSucrose = sucrose;
	}

	/**
	 * @param sugars the sugars to set
	 */
	public void setSugars(final String sugars) {
		mSugars = sugars;
	}

	/**
	 * @param transFat the transFat to set
	 */
	public void setTransFat(final String transFat) {
		mTransFat = transFat;
	}


}
