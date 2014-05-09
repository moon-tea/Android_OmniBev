package edu.tableservice;

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

import edu.tableservice.data.Check;
import edu.tableservice.data.DataStore;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class FetchDataTask extends AsyncTask<String, Void, List<Check>> {
	
	@Override
	protected List<Check> doInBackground(String... params) {
		List<Check> list = null;
		
		// Download the data
		String data = downloadData(params[0]);

		// Now parse it into some useful Java objects for the rest of the app
		
		list = parseCheckList(data);
		
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
	
	
	private List<Check> parseCheckList(String data) {
		
		// NOTE: This is not a very elegant way to parse response, but its straight-forward
		// enough for this example
		
		ArrayList<Check> checkList = new ArrayList<Check>();
		DataStore.clear();
		/*String[] parts = data.split(",");
		
		for (int i = 0; i < parts.length; i+=3) {
			
			// Make sure there will be enough fields to create an stock quote
			// (Handle bad data);
			if (i+2 > parts.length) {
				break;
			}
			
			User quote = new User(parts[i+0], parts[i+1], parts[i+2]);
			list.add(quote);
			
		}
		
		return list;*/
	
		//Quick JSON Example
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
			System.out.println(jsonObject.toString());
			//JSONObject jsonObject = new JSONObject(data);
			//String string  = jsonObject.getString("someStringValue");
			JSONArray array = jsonObject.getJSONArray("checks");
			System.out.println("*****JARRAY*****"+ array.length());
			for(int i = 0; i < array.length(); i++)
			{
				JSONObject json_table = array.getJSONObject(i);
				Check chck = new Check(json_table.getInt("tableId"), json_table.getString("tableName"));
				JSONArray items = json_table.getJSONArray("items");				
				for(int j = 0; j < items.length(); j++) {
					JSONObject json_item = items.getJSONObject(j);
					chck.addItem(json_item.getString("desc"), json_item.getDouble("priceUSD"));
				}					
				 
				 /*Log.i("id","_id"+json_data.getInt("id")+
				  ", username"+json_data.getString("username")+
				  ", privs"+json_data.getString("privs")
				 );*/
				 				 
				 checkList.add(chck);
				 DataStore.addItem(chck);
			}			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSON parse exception");
			System.out.println(jsonObject.toString());
		}
		return checkList;
	}
}
