package com.chanpinzazhi.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.chanpinzazhi.soap.SoapConstants;

public class NetManager {
	/**
	 * 封装网络提示
	 * 
	 * @param handler
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Handler handler, Context context) {
		boolean flag = isNetworkConnected(context);
		if (!flag) {
			handler.sendEmptyMessage(SoapConstants.NO_NET);
		}
		return flag;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
}
