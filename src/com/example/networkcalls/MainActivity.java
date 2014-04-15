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
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private View mDownloadButton;
	private View mNewWineButton;
	private FetchUserTask uDownloadTask;
	private FetchWineTask wDownloadTask;
	
	private ListView uListView;
	private ListView wListView;
	 //private ListView mListView;
	
	private ArrayList<User> mUserList = new ArrayList<User>();
	private ArrayList<Wine> mWineList = new ArrayList<Wine>();
	
	private UserListAdapter uAdapter;
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
		uListView = (ListView)findViewById(R.id.user_index);
		wListView = (ListView)findViewById(R.id.wine_index);
		//set default views
		uAdapter = new UserListAdapter(this, mUserList);
		wAdapter = new WineListAdapter(this, mWineList);
		
		//set views based on data
		uListView.setAdapter(uAdapter);
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

		if (uDownloadTask == null) {
			uDownloadTask = new FetchUserTask() {

				@Override
				protected void onPostExecute(List<User> result) {
					super.onPostExecute(result);
					mUserList.clear();
					mUserList.addAll(result);
					uAdapter.notifyDataSetChanged();
					uAdapter.notifyDataSetChanged();
					uDownloadTask = null;
					mDownloadButton.setEnabled(true);
					findViewById(R.id.progress).setVisibility(View.GONE);
				}
			};
			mDownloadButton.setEnabled(false);
			findViewById(R.id.progress).setVisibility(View.VISIBLE);
			uDownloadTask.execute("http://android.montenichols.com/public/");
		}
		
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
					findViewById(R.id.progress).setVisibility(View.GONE);
				}
			};
			mDownloadButton.setEnabled(false);
			findViewById(R.id.progress).setVisibility(View.VISIBLE);
			wDownloadTask.execute("http://android.montenichols.com/public/");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class UserListAdapter extends ArrayAdapter<User> {

		public UserListAdapter(Context context, List<User> list) {
			super(context, 0, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.user_index, parent, false);

			User User = getItem(position);

			TextView textView = (TextView)view.findViewById(R.id.txt_user_name);
			textView.setText(User.username);

			textView = (TextView)view.findViewById(R.id.txt_user_id);
			textView.setText("id: " + User.id);

			return view;
		}

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

			TextView textView = (TextView)view.findViewById(R.id.txt_name);
			textView.setText(Wine.name);

			textView = (TextView)view.findViewById(R.id.txt_varietal);
			textView.setText("Varietal: " + Wine.varietal);
			
			textView = (TextView)view.findViewById(R.id.txt_region);
			textView.setText("Regions: " + Wine.region);
			
			textView = (TextView)view.findViewById(R.id.txt_vintage);
			textView.setText("Vintage: " + Wine.vintage);
			
			textView = (TextView)view.findViewById(R.id.txt_profile);
			textView.setText("Profile: " + Wine.profile);
			
			textView = (TextView)view.findViewById(R.id.txt_color);
			textView.setText("Color: " + Wine.color);
			
			textView = (TextView)view.findViewById(R.id.txt_alcohol_content);
			textView.setText("Alcohol Content: " + Wine.alcohol_content);
			
			textView = (TextView)view.findViewById(R.id.txt_rating);
			textView.setText("User Rating: " + Wine.rating);		

			return view;
		}

	}

}
