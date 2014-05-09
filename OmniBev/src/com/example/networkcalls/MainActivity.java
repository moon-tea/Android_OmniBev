package com.example.networkcalls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private View mDownloadButton;
	private View mNewWineButton;
	private FetchWineTask wDownloadTask;
	
	private ListView wListView;
	 //private ListView mListView;
	
	private ArrayList<Wine> mWineList = new ArrayList<Wine>();
	
	private WineListAdapter wAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDownloadButton = findViewById(R.id.btn_download);
		mNewWineButton = findViewById(R.id.btn_create_wine);
		mDownloadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDownload();
			}
		});
		
		// create wine button event
		mNewWineButton.setOnClickListener(new View.OnClickListener() { 
            @Override
            public void onClick(View v) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), CreateWineActivity.class);
                startActivity(i); 
            }
        });

		//get views		
		wListView = (ListView)findViewById(R.id.wine_index);
		//set default views
		wAdapter = new WineListAdapter(this, mWineList);
		
		//set views based on data
		wListView.setAdapter(wAdapter);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		//		if (mDownloadTask != null) {
		//			mDownloadTask.cancel(true);
		//			mDownloadTask = null;
		//		}
	}

	protected void startDownload() {
		// http://android.montenichols.com/public/
				
		if (wDownloadTask == null) {
			wDownloadTask = new FetchWineTask() {

				@Override
				protected void onPostExecute(List<Wine> result) {
					super.onPostExecute(result);
					mWineList.clear();
					mWineList.addAll(result);
					wAdapter.notifyDataSetChanged();
					wAdapter.notifyDataSetChanged();
					wDownloadTask = null;
					mDownloadButton.setEnabled(true);					
				}
			};
			mDownloadButton.setEnabled(false);			
			wDownloadTask.execute("http://android.montenichols.com/wine/index");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class WineListAdapter extends ArrayAdapter<Wine> {

		public WineListAdapter(Context context, List<Wine> list) {
			super(context, 0, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.wine_index, parent, false);

			Wine Wine = getItem(position);
			final String name = Wine.name;
			final String varietal= Wine.varietal;
			final String region = Wine.region;
			final String vintage= Wine.vintage;
			final String profile= Wine.profile;
			final String color= Wine.color;
			final String alcohol_content= Wine.alcohol_content;
			final String rating= Wine.rating;

			TextView textView = (TextView)view.findViewById(R.id.txt_name);
			textView.setText(Wine.name);

			textView = (TextView)view.findViewById(R.id.txt_varietal);
			textView.setText("Varietal: " + Wine.varietal);
						
			textView = (TextView)view.findViewById(R.id.txt_rating);
			textView.setText("User Rating: " + Wine.rating);
			
			 // and set OnClickListener
		    Button button = (Button) view.findViewById(R.id.download_button);                
		    button.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View view) {
		        	Intent i = new Intent(getApplicationContext(), ShowWineActivity.class);
		        	i.putExtra("name", name);
		        	i.putExtra("varietal", varietal);
		        	i.putExtra("region", region);
		        	i.putExtra("vintage", vintage);
		        	i.putExtra("profile", profile);
		        	i.putExtra("profile", profile);
		        	i.putExtra("color", color);
		        	i.putExtra("alcohol_content", alcohol_content);
		        	i.putExtra("rating", rating);
		        	startActivity(i);
		        	//finish();
		        }
		    });

			return view;
		}

	}

}
