package edu.tableservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BroadCastListenerEXTREME extends BroadcastReceiver {

	public static final String ACTION_TRANSACTION_COMPLETE = "edu.udallas.tableservice.PaymentTransationService.complete";
	
	@Override
	public void onReceive(Context context, Intent intent) {	 
		
		String action = intent.getAction();
			if(action.equalsIgnoreCase(ACTION_TRANSACTION_COMPLETE)) {				
				doSomething(intent.getStringExtra("message"));
				Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
			} else {				
				doSomething("that was incorrect");
				Toast.makeText(context, "that was incorrect", Toast.LENGTH_LONG).show();				
			}	
		}
	
		private void doSomething(String number){			
			Log.d("<<<>>>",number);	
		}
	}