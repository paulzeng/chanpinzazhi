package com.chanpinzazhi.manager;

import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class DeviceManager {
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);  
		int width = wm.getDefaultDisplay().getWidth();//屏幕宽度  
		return width;
	}
	
	public static String getLocalMacAddress(Context context) {

		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();

	}
	
	public static String getMyUUID(Context context){

		  final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);   

		  final String tmDevice, tmSerial, androidId;   

		  tmDevice = "" + tm.getDeviceId();  

		  tmSerial = "" + tm.getSimSerialNumber();   

		  androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);   

		  UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());   

		  String uniqueId = deviceUuid.toString();

		  L.d("HTTP","uuid="+uniqueId);

		  return "android_"+uniqueId;

		 }
}
