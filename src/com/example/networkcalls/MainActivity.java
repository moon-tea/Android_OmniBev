package com.example.networkcalls;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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

	private FetchDataTask mDownloadTask;

	private ListView mListView;

	private ArrayList<User> mStockList = new ArrayList<User>();

	private StockListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDownloadButton = findViewById(R.id.btn_download);
		mDownloadButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDownload();
			}
		});


		mListView = (ListView)findViewById(R.id.list_stocks);
		mAdapter = new StockListAdapter(this, mStockList);
		mListView.setAdapter(mAdapter);
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
		// http://finance.yahoo.com/d/quotes?s=AAPL+GOOG+MSFT&format=csv

		if (mDownloadTask == null) {
			mDownloadTask = new FetchDataTask() {

				@Override
				protected void onPostExecute(List<User> result) {
					super.onPostExecute(result);
					mStockList.clear();
					mStockList.addAll(result);
					mAdapter.notifyDataSetChanged();
					mDownloadTask = null;
					mDownloadButton.setEnabled(true);
					findViewById(R.id.progress).setVisibility(View.GONE);
				}
			};
			mDownloadButton.setEnabled(false);
			findViewById(R.id.progress).setVisibility(View.VISIBLE);
			mDownloadTask.execute("http://http://android.montenichols.com/public/");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private class StockListAdapter extends ArrayAdapter<User> {

		public StockListAdapter(Context context, List<User> list) {
			super(context, 0, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			View view = inflater.inflate(R.layout.item_stock, parent, false);

			User User = getItem(position);

			TextView textView = (TextView)view.findViewById(R.id.txt_stock_symbol);
			textView.setText(User.username);

			textView = (TextView)view.findViewById(R.id.txt_stock_quote);
			textView.setText(User.privs);

			return view;
		}

	}

}
