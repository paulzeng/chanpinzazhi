package com.chanpinzazhi.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chanpinzazhi.manager.L;

public class ControlPushBroadCastReceiver extends BroadcastReceiver {
	private static final String TAG = "ControlPushBroadCastReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();		
		L.d(TAG, "AutoBootReceiver action"+action);
		if("com.chanpinzazhi.pushservice".equals(action)){
			int status = intent.getIntExtra("status", 0);
			L.d(TAG, " action status "+status);
			Intent newIntent = new Intent(context,PushService.class);
			if (status == 1) {
				context.startService(newIntent);
			}else{
				context.stopService(newIntent);			
			}
		}
	}

}
