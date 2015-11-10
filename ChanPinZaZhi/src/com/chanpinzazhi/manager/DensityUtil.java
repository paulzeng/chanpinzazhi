package com.chanpinzazhi.manager;

import android.app.Activity;
import android.content.Context;

/**
 * 分辨率转换类
 * 
 * @author lxb
 * */
public class DensityUtil {

	// int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
	// // 屏幕宽（像素，如�?80px�?
	// int screenHeight =
	// getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如�?00p�?
	// int xDip = DensityUtil.px2dip(SettingActivity.this, (float)
	// (screenWidth * 1.0));
	// int yDip = DensityUtil.px2dip(SettingActivity.this, (float)
	// (screenHeight * 1.0));

	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}