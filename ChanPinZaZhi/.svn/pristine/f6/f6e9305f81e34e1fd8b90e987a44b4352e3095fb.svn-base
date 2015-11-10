package com.chanpinzazhi.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.BaseApplication;
import com.chanpinzazhi.R;
import com.chanpinzazhi.impl.ImageLoaderOptionImpl;
import com.chanpinzazhi.manager.ConfigManager;
import com.chanpinzazhi.view.ShakeListener;
import com.chanpinzazhi.view.ShakeListener.OnShakeListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public abstract class BaseActivity extends Activity implements
		ImageLoaderOptionImpl {

	ShakeListener mShakeListener;
	AlertDialog shakeDialog;
	TextView tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((BaseApplication) getApplication()).activitys.add(this);
		shakeDialog = new AlertDialog.Builder(this).create();
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				mShakeListener.stop();
				shakeDialog.show();
				shakeDialog.getWindow().setContentView(R.layout.shake_dialog);
				tv_content = (TextView) shakeDialog.getWindow().findViewById(
						R.id.txt_shake_content);
				tv_content.setText("确定要拨打Tel:"
						+ ConfigManager.getConfigs(BaseActivity.this,
								"phone") + "嘛？");
				shakeDialog.getWindow().findViewById(R.id.btn_shake_ok)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								mShakeListener.start();
								Toast.makeText(getApplicationContext(), "拨号中",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(Intent.ACTION_CALL,
										Uri.parse("tel:"
												+ ConfigManager.getConfigs(
														BaseActivity.this,
														"url_wsdl")));
								startActivity(intent);
								shakeDialog.dismiss();
							}
						});
				shakeDialog.getWindow().findViewById(R.id.btn_shake_cancel)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								shakeDialog.dismiss();
							}
						});

			}
		});
	}

	@Override
	protected void onPause() {

		// TODO Auto-generated method stub
		super.onPause();
		mShakeListener.stop();
	}

	@Override
	protected void onResume() {

		// TODO Auto-generated method stub
		super.onResume();
		mShakeListener.start();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mShakeListener.stop();
	}

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	protected DisplayImageOptions options;// 图片显示控制类

	@Override
	public void initOption() {
		// TODO Auto-generated method stub
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.loading_default)
				.showImageForEmptyUri(R.drawable.error_default)
				.showImageOnFail(R.drawable.error_default)
				.resetViewBeforeLoading(false).cacheOnDisc(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(100)).build();
	}

	public void enterToActivity(Activity activity, Class cls) {
		Intent intent = new Intent(activity, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.in_form_left, R.anim.out_of_right);
	}

	public void backToActivity() {
		finish();
		overridePendingTransition(R.anim.in_form_left_back,
				R.anim.out_of_right_back);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			backToActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.in_form_left_back,
				R.anim.out_of_right_back);
	}
}