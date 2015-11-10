package com.chanpinzazhi.manager;

import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.chanpinzazhi.BaseApplication;
import com.chanpinzazhi.R;

public class UIManager {
	/**
	 * 通用的dialog提示框
	 * 
	 * @param context
	 * @param contentRes
	 *            提示内容
	 * @param sureRes
	 *            左边bt文字
	 * @param cancleRes
	 *            右边bt文字
	 * @param l
	 *            点击右边bt后的动作监听
	 * @return
	 */
	public static Dialog getCommWarnDialog(Context context, String titleRes,
			String contentRes, int sureRes, int cancleRes,
			final OnClickListener l) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.layout_common_dialog, null);
		TextView title = (TextView) view.findViewById(R.id.tv_title);
		TextView content = (TextView) view.findViewById(R.id.tv_content);
		Button sure = (Button) view.findViewById(R.id.btn_ok);
		Button cancle = (Button) view.findViewById(R.id.btn_cancel);
		title.setText(titleRes);
		content.setText(contentRes);
		sure.setText(sureRes);
		cancle.setText(cancleRes);
		dialog.setContentView(view);
		// 触摸对话框以外 dismiss对话框
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.btn_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						l.onClick(v);
						dialog.dismiss();

					}
				});
		view.findViewById(R.id.btn_cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL); // 此处可以设置dialog显示的位置

		return dialog;
	}

	/**
	 * 通用的dialog提示框
	 * 
	 * @param context
	 * @param contentRes
	 *            提示内容
	 * @param sureRes
	 *            左边bt文字
	 * @param cancleRes
	 *            右边bt文字
	 * @param l
	 *            点击右边bt后的动作监听
	 * @return
	 */
	public static Dialog getCommWarnDialog2(final Context context) {
		final Dialog dialog = new Dialog(context, R.style.float_base);
		View view = View.inflate(context, R.layout.layout_common_dialog2, null);
		dialog.setContentView(view);
		// 触摸对话框以外 dismiss对话框
		dialog.setCanceledOnTouchOutside(true);
		view.findViewById(R.id.btn_ok).setOnClickListener(
				new OnClickListener() { 
					@Override
					public void onClick(View v) { 
						dialog.dismiss();
						((BaseApplication) context.getApplicationContext()).exit(); 
					}
				});

		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = getScreenWidth(context) - dpToPx(context, 50);
		window.setGravity(Gravity.CENTER_VERTICAL); // 此处可以设置dialog显示的位置

		return dialog;
	}

	// ----------------------------------------以上为提示对话框框----------------------------------------------------------------

	// ----------------------------------------以上为有网络访问的时候的提示对话框--------------------

	/**
	 * sp或者 dp 装换为 px
	 */
	public static int dpToPx(Context context, int dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(dpValue * scale);
	}

	/**
	 * 工具方法
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	// ----------------------------------------以上为服务以上对话框的工具类--------------------

	// ----------------------------------------以上为界面切换的工具类--------------------

	/**
	 * 通过startActivityForResult 激活的界面，返回数据
	 * 
	 * @param context
	 * @param resultCode
	 */
	public static void resultActivity(Context context, int resultCode) {
		resultActivity(context, resultCode, null);
	}

	public static void resultActivity(Context context, int resultCode,
			Map<String, Object> extras) {
		Intent i = new Intent();
		putExtras(extras, i);
		((Activity) context).setResult(resultCode, i);
		((Activity) context).finish();

	}

	/**
	 * startActivityForResult 激活方式
	 * 
	 * @param from
	 * @param to
	 * @param requestCode
	 */
	public static void switcherFor(Context from, Class<?> to, int requestCode) {
		switcherFor(from, to, requestCode, null);
	}

	public static void switcherFor(Context from, Class<?> to, int requestCode,
			Map<String, Object> extras) {
		Intent i = new Intent(from, to);
		putExtras(extras, i);
		((Activity) from).startActivityForResult(i, requestCode);

	}

	/**
	 * startActivity 激活ui
	 * 
	 * @param from
	 * @param to
	 */
	public static void switcher(Activity from, Class<?> to) {
		switcher(from, to, null);
	}

	public static void switcher(Activity from, Class<?> to,
			Map<String, Object> extras) {
		Intent i = new Intent(from, to);
		putExtras(extras, i);
		from.startActivity(i);
		from.overridePendingTransition(R.anim.in_form_left, R.anim.out_of_right);
	}

	/**
	 * intent 中 传递数据
	 * 
	 * @param extras
	 * @param i
	 */
	private static void putExtras(Map<String, Object> extras, Intent i) {
		if (extras != null) {
			for (String name : extras.keySet()) {
				Object obj = extras.get(name);
				if (obj instanceof String) {
					i.putExtra(name, (String) obj);
				}
				if (obj instanceof Integer) {
					i.putExtra(name, (Integer) obj);
				}
				if (obj instanceof String[]) {
					i.putExtra(name, (String[]) obj);
				}

			}
		}
	}

	/**
	 * 网络访问时候的提示对话框
	 * 
	 * @param context
	 * @param loadingTextRes
	 * @return
	 */
	public static Dialog getLoadingDialog(Context context) {

		return getLoadingDialog(context, R.string.dialog_loading);
	}

	public static Dialog getLoadingDialog(Context context, int loadingTextRes) {
		final Dialog dialog = new Dialog(context, R.style.netLoadingDialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.custom_progress_dialog);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		// lp.width = getScreenWidth(context) - dpToPx(context, 100);
		window.setGravity(Gravity.CENTER_VERTICAL); // 此处可以设置dialog显示的位置
		TextView titleTxtv = (TextView) dialog.findViewById(R.id.dialog_tv);
		titleTxtv.setText(loadingTextRes);
		return dialog;
	}

	/**
	 * 关闭dialog
	 * 
	 * @param loadDialog
	 */
	public static void toggleDialog(Dialog loadDialog) {
		if (loadDialog != null && loadDialog.isShowing()) {
			loadDialog.dismiss();
		}

	}
}
