package edu.tableservice;

import java.util.ArrayList;

import edu.tableservice.data.Amount;
import edu.tableservice.data.Check;
import edu.tableservice.data.DataStore;
import edu.udallas.tableservice.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SignatureActivity extends Activity {
	
	private SignaturePadView mSignatureView;
	private View mDoneButton;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        
        // Find the ListView, create an adapter that reads our list of checks,
        // and connect the two
        mSignatureView = (SignaturePadView)findViewById(R.id.signature);
        mDoneButton = findViewById(R.id.done_button);
        
        mSignatureView.setBackgroundColor(0xFFCCCCCC);        
        
        mDoneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Thank You!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), TableListActivity.class);
	        	startActivity(i);
			}
		});
                
    }
}
