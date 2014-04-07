package com.example.networkcalls;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class FetchWineTask extends AsyncTask<String, Void, List<Wine>> {

	@Override
	protected List<Wine> doInBackground(String... params) {
		List<Wine> list = null;
		
		// Download the data
		String data = downloadData(params[0]);

		// Now parse it into some useful Java objects for the rest of the app
		
		list = parseWineList(data);
		
		return list;
	}

	private String downloadData(String urlString) {
		try {
			URL url = new URL(urlString);
			
			// Create a HttpURLConnection to perform a GET
			// If you were doing a POST, had headers, etc, you could change this object
			// to handle those, too
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setConnectTimeout(10000);
			
			// Make a connection
			connection.connect();

			// Grab the input stream to read the file
			InputStream responseInStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
            	
            	// Read the stream into a string using a Reader + StirngBuilder
            	// Alternatively, you could use a FileOutputStream to write the downloaded
            	// data to a file
            	BufferedInputStream bis = new BufferedInputStream(responseInStream);
            	
            	BufferedReader r = new BufferedReader(new InputStreamReader(bis));
            	StringBuilder builder = new StringBuilder();
            	String line;
            	while ((line = r.readLine()) != null) {
            		builder.append(line);
            		//builder.append(',');
            		System.out.println("count: " + line);
            		Log.i("id", line);
            	}
            	System.out.println(builder.toString());
            	Log.i("id_", builder.toString());
            	return builder.toString();
            }

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// There was an exception or other problem, don't return anything
		return null;
	}
	
	
	private List<Wine> parseWineList(String data) {
		
		// NOTE: This is not a very elegant way to parse response, but its straight-forward
		// enough for this example		
		ArrayList<Wine> wineList = new ArrayList<Wine>();		
	
		//Quick JSON Example
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
			System.out.println(jsonObject.toString());			
			JSONArray array = jsonObject.getJSONArray("array");
			System.out.println("*****JARRAY*****"+ array.length());
			for(int i=1; i < array.length(); i++)
			{
				 JSONObject json_data = array.getJSONObject(i);
				 Log.i("id","_id"+json_data.getInt("id")+
				  ", name"+json_data.getString("name")+
				  ", img_gile_path"+json_data.getString("img_file_path")+
				  ", varietal"+json_data.getString("varietal")+
				  ", region"+json_data.getString("region")+
				  ", vintage"+json_data.getString("vintage")+
				  ", profile"+json_data.getString("profile")+
				  ", color"+json_data.getString("color")+
				  ", alcohol_content"+json_data.getString("alcohol_content")+
				  ", rating"+json_data.getString("rating")
				);
				 Wine wine = new Wine(	json_data.getInt("id"), 
						 				json_data.getString("name"),
						 				json_data.getString("img_file_path"),
						 				json_data.getString("varietal"),
						 				json_data.getString("region"),
						 				json_data.getString("vintage"),
						 				json_data.getString("profile"),
						 				json_data.getString("color"),
						 				json_data.getString("alcohol_content"),
						 				json_data.getString("rating")						 				
						 			);	
					wineList.add(wine);
			}			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.err.println("JSON parse exception");			
		}
		return wineList;
	}
}
