package com.example.networkcalls;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.networkcalls.CreateWineActivity.CreateNewWine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.PorterDuff;

public class ShowWineActivity extends Activity {

	// Progress Dialog
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
	
	private View mDeleteButton;
	private View mStoreButton;
	private FetchWineTask wDownloadTask;
	
	private ListView wListView;
	 //private ListView mListView;
	
	private ArrayList<Wine> mWineList = new ArrayList<Wine>();
	String deleteId;
	
	// url to delete wine
    private static String url_delete_wine = 
    		"http://android.montenichols.com/wine/delete";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wine_show);
		
		mDeleteButton = findViewById(R.id.btnDeleteWine);
		mDeleteButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
		mStoreButton = findViewById(R.id.btnCreateWine);
		deleteId = getIntent().getStringExtra("id");
		
		mDeleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DeleteWine().execute();
				// Launching main activity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i); 
			}
		});
		
		// create wine button event
		mStoreButton.setOnClickListener(new View.OnClickListener() { 
            @Override
            public void onClick(View v) {
            	//startStore();
            	// Launching create new product activity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i); 
            }
        });
		String id = getIntent().getStringExtra("id");
		String name = getIntent().getStringExtra("name");
		String varietal = getIntent().getStringExtra("varietal");
		String region = getIntent().getStringExtra("region");
		String vintage = getIntent().getStringExtra("vintage");
		String profile = getIntent().getStringExtra("profile");
		String color = getIntent().getStringExtra("color");
		String alcohol_content = getIntent().getStringExtra("alcohol_content");
		String rating = getIntent().getStringExtra("rating");
				
		//Wine Wine = mWineList.get(Integer.parseInt(data));

		EditText editText = (EditText)findViewById(R.id.input_wine_name);
		editText.setText(name);

		editText = (EditText)findViewById(R.id.input_wine_varietal);
		editText.setText(varietal);
		
		editText = (EditText)findViewById(R.id.input_wine_region);
		editText.setText(region);
		
		editText = (EditText)findViewById(R.id.input_wine_vintage);
		editText.setText(vintage);
		
		editText = (EditText)findViewById(R.id.input_wine_profile);
		editText.setText(profile);
		
		editText = (EditText)findViewById(R.id.input_wine_color);
		editText.setText(color);
		
		editText = (EditText)findViewById(R.id.input_wine_alcohol_content);
		editText.setText(alcohol_content);
		
		editText = (EditText)findViewById(R.id.input_wine_rating);
		editText.setText(rating);		
	}

	@Override
	protected void onPause() {
		super.onPause();
		//		if (mDownloadTask != null) {
		//			mDownloadTask.cancel(true);
		//			mDownloadTask = null;
		//		}
	}

	/*protected void startDownload() {
		// http://android.montenichols.com/public/
				
		if (wDownloadTask == null) {
			wDownloadTask = new FetchWineTask() {

				@Override
				protected void onPostExecute(List<Wine> result) {
					super.onPostExecute(result);
					mWineList.clear();
					mWineList.addAll(result);
					//wAdapter.notifyDataSetChanged();
					//wAdapter.notifyDataSetChanged();
					wDownloadTask = null;
					//mDownloadButton.setEnabled(true);
					}
			};
			//mDownloadButton.setEnabled(false);
			wDownloadTask.execute("http://android.montenichols.com/wine/index");
		}
	}*/
	
	/*protected void startStore() {
		// http://android.montenichols.com/public/wine/update/{id}
				
		if (wDownloadTask == null) {
			wDownloadTask = new FetchWineTask() {

				@Override
				protected void onPostExecute(List<Wine> result) {
					super.onPostExecute(result);
					mWineList.clear();
					mWineList.addAll(result);
					//wAdapter.notifyDataSetChanged();
					//wAdapter.notifyDataSetChanged();
					wDownloadTask = null;
					//mDownloadButton.setEnabled(true);
					}
			};
			//mDownloadButton.setEnabled(false);
			wDownloadTask.execute("http://android.montenichols.com/wine/index");
		}
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
     * Background Async Task to Delete new product
     * */
    class DeleteWine extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowWineActivity.this);
            pDialog.setMessage("Deleting Wine..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {
            String id = deleteId;            
            Log.d("<<<>>>>", ("http://android.montenichols.com/wine/delete/" + deleteId));
            // Building Parameters
            // List<NameValuePair> params = new ArrayList<NameValuePair>();
            //params.add(new BasicNameValuePair("delete", deleteId));
            try {
            	
            URL url = new URL("http://android.montenichols.com/wine/delete/" + deleteId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setConnectTimeout(36000);
			
			// Make a connection
			connection.connect();
            
            
            // getting JSON Object
            // Note that create product url accepts POST method
            //jsonParser.makeHttpGet(url_delete_wine + "/" + deleteId);
 
            // check log cat fro response
            //Log.d("Create Response", json.toString());
 
            // check for success tag
           // try {
                int success = 1;//json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
 
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            //} catch (JSONException e) {
                //e.printStackTrace();
            //}
            } catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
    }
}
