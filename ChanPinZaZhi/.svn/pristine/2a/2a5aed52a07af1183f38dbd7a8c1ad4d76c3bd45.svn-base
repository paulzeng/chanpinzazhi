package com.chanpinzazhi.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.chanpinzazhi.R;
import com.chanpinzazhi.manager.TitleManager;

/**
 * 关于界面
 * 
 * @author Administrator
 * 
 */
public class AboutActivity extends BaseActivity implements OnClickListener {
	private ImageView btn_setting, btn_return;
	private WebView mWebview;
	private WebSettings settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		TitleManager.showTitle(this, new int[] { 1 }, R.string.txt_about);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		btn_return = (ImageView) findViewById(R.id.btn_return);
		btn_return.setOnClickListener(this);
		btn_setting = (ImageView) findViewById(R.id.btn_setting);
		btn_setting.setVisibility(View.GONE);
		mWebview = (WebView) findViewById(R.id.mWebview);
		settings = mWebview.getSettings();
		settings.setJavaScriptEnabled(true); 
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		// 设置webview缓存
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		mWebview.loadUrl("file:///android_asset/about.html");
		mWebview.setWebViewClient(new MyWebViewClient());
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.indexOf("tel:") < 0) {// 页面上有数字会导致连接电话
				view.loadUrl(url);
			}
			return true;
		}
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_return:
			finish();
			break;

		default:
			break;
		}
	}

}
