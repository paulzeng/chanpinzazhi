package com.chanpinzazhi.manager;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanpinzazhi.R;

public class TitleManager {
	/**
	 * app title工具,下面的常量用于setTiele方法中的flag，flag代表的是title中我要显示的iv
	 */

	public static final int BACK = 1;// 返回（箭头）
	public static final int SETTING = 2;// ok
	public static final int SEARCH = 3;
	/**
	 * 
	 * @param from
	 *            当前的界面
	 * @param flags
	 *            title中需要显示的view
	 * @param title
	 *            title正中需要显示的文本
	 * @param left
	 *            title左边要显示的文本资源
	 * @param right
	 *            title右边需要显示的文本资源
	 * @param to
	 *            需要调整到那一个界面
	 */
	public static void showTitle(final Activity from, int[] flags, int title) {

		if (title > 0) {
			TextView tv = (TextView) from.findViewById(R.id.tv_title);
			tv.setText(title);
		}
		if (flags != null) {// 为null 是没有title bar
			for (int flag : flags) {
				switch (flag) {
				case BACK: {
					ImageView iv = (ImageView) from
							.findViewById(R.id.btn_return);
					iv.setVisibility(View.VISIBLE);
					iv.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							from.finish();
						}
					});
					break;
				}
				case SETTING: {
					from.findViewById(R.id.btn_setting).setVisibility(
							View.VISIBLE);
					break;
				}

				case SEARCH: {
					from.findViewById(R.id.btn_search).setVisibility(
							View.VISIBLE);
					break;
				}
				}
			}
		}

	}

}
