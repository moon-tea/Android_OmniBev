package edu.tableservice.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

/**
 * A simplistic place to store data in our sample app
 * 
 * In the real world, you would want to store your data in a better
 * place, like a ContentProvider
 */
public class DataStore {

	private static final String TAG = "DataStore";

	/**
	 * The list of all checks
	 */
	public static ArrayList<Check> CHECKS = new ArrayList<Check>();

	public static void addItem(Check check) {
		CHECKS.add(check);
	}
	
	public static void clear() {
		Log.d(TAG, "DataStore CLEAR");
		CHECKS.clear();
	}
	
	
	/**
	 * Create a static list of data to get us started
	 * 
	 * Later we will replace this with data we have downloaded
	 */
	/*static {
		Check check;
		
		check = new Check(1, "Table 3");
		check.addItem("Hamburger", 4.99);
		check.addItem("Diet Coke", 1.60);
		addItem(check);
		
		check = new Check(2, "Table 24");
		check.addItem("Cheeseburger", 5.59);
		check.addItem("Diet Coke", 1.60);
		check.addItem("Salad", 3.99);
		check.addItem("Water", 0.0);
		addItem(check);
		
		check = new Check(3, "Bar Seat 2");
		check.addItem("Hamburger", 4.99);
		check.addItem("Root Beer", 1.60);
		addItem(check);
	}*/

}
