package com.example.networkcalls;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class CreateWineActivity extends Activity {
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    //EditText inputPrice;
    EditText inputImg;
    EditText inputVarietal;
    EditText inputRegion;
    EditText inputVintage;
    EditText inputProfile;
    EditText inputColor;
    EditText inputAlcoholContent;
    EditText inputRating;
 
    // url to create new wine
    private static String url_create_wine = 
    		"http://android.montenichols.com/wine/store";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wine_create);
 
        // Edit Text
        inputName = (EditText) findViewById(R.id.input_wine_name);
        //inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputImg = (EditText) findViewById(R.id.input_wine_img);
        inputVarietal = (EditText) findViewById(R.id.input_wine_varietal);
        inputRegion = (EditText) findViewById(R.id.input_wine_region);
        inputVintage = (EditText) findViewById(R.id.input_wine_vintage);
        inputProfile = (EditText) findViewById(R.id.input_wine_profile);
        inputColor = (EditText) findViewById(R.id.input_wine_color);
        inputAlcoholContent = (EditText) findViewById(R.id.input_wine_alcohol_content);
        inputRating = (EditText) findViewById(R.id.input_wine_rating);
 
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateWine);
 
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewWine().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class CreateNewWine extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateWineActivity.this);
            pDialog.setMessage("Creating Wine..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = inputName.getText().toString();
            //String price = inputImg.getText().toString();
            String img_file_path = inputImg.getText().toString();
            String varietal = inputVarietal.getText().toString();
            String region = inputRegion.getText().toString();
            String vintage = inputVintage.getText().toString();
            String profile = inputProfile.getText().toString();
            String color = inputColor.getText().toString();
            String alcohol_content = inputAlcoholContent.getText().toString();
            String rating = inputRating.getText().toString();
            
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            //params.add(new BasicNameValuePair("price", price));
            params.add(new BasicNameValuePair("img_file_path", img_file_path));
            params.add(new BasicNameValuePair("varietal", varietal));
            params.add(new BasicNameValuePair("region", region));
            params.add(new BasicNameValuePair("vintage", vintage));
            params.add(new BasicNameValuePair("profile", profile));
            params.add(new BasicNameValuePair("color", color));
            params.add(new BasicNameValuePair("alcohol_content", alcohol_content));
            params.add(new BasicNameValuePair("rating", rating));
            
            // getting JSON Object
            // Note that create product url accepts POST method
            jsonParser.makeHttpPost(url_create_wine, params);
 
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