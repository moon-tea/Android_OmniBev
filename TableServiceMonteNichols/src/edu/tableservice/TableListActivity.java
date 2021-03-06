package edu.tableservice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.tableservice.data.Check;
import edu.tableservice.data.DataStore;
import edu.udallas.tableservice.R;

public class TableListActivity extends Activity {

    private ListView mListView;
    
    private FetchDataTask iDownloadTask;
    
    private ListView iCheckView;
    
    private ArrayList<Check> iCheckList = new ArrayList<Check>();
    
    private TableListAdapter mAdapter;
    
    private ProgressDialog progress;
    
    private BroadCastListenerEXTREME receiver;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        
        // Find the ListView, create an adapter that reads our list of checks,
        // and connect the two
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new TableListAdapter(this, DataStore.CHECKS);
        mListView.setAdapter(mAdapter);
                
        IntentFilter filter = new IntentFilter(BroadCastListenerEXTREME.ACTION_TRANSACTION_COMPLETE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new BroadCastListenerEXTREME();
        registerReceiver(receiver, filter);       
        
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	ArrayList<Check> checks = DataStore.CHECKS;
                String pos = Integer.toString(position);
                //Toast.makeText(getBaseContext(), pos, Toast.LENGTH_SHORT).show();
                
                String subtotal = Double.toString(checks.get(position).getSubtotal().getRawValue());
                
                Intent i = new Intent(getApplicationContext(), PayCheckActivity.class);
	        	i.putExtra("position", pos);
	        	i.putExtra("subtotal", subtotal);//Double.toString(check.get(position).getSubtotalDouble()));
	        	startActivity(i);
            }			
        });
                
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.table_list, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    		int id = item.getItemId();
    		
    		if (id == R.id.action_refresh) {
    			// TODO do stuff here    			
    			progress = new ProgressDialog(this);
    			progress.setTitle("Loading");
    			progress.setMessage("Getting Items...");
    			progress.show();
    			// To dismiss the dialog    			
    			startDownload();    			
    			Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
    			return true;
    		}
    		return super.onOptionsItemSelected(item);
    }
    
    protected void startDownload() {
		// http://tableservice.eu.pn/checks.json
				
		if (iDownloadTask == null) {
			iDownloadTask = new FetchDataTask() {

				@Override
				protected void onPostExecute(List<Check> result) {
					super.onPostExecute(result);
					iCheckList.clear();
					iCheckList.addAll(result);					
					mAdapter.notifyDataSetChanged();
					mAdapter.notifyDataSetChanged();
					iDownloadTask = null;
					//iDownloadButton.setEnabled(true);	
					progress.dismiss();
				}
			};			
			iDownloadTask.execute("http://tableservice.eu.pn/checks.json");
		}
	}
    
}
