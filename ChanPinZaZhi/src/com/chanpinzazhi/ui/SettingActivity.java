package com.chanpinzazhi.ui;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.BaseApplication;
import com.chanpinzazhi.R;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.ShareManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.manager.UIManager;
import com.chanpinzazhi.manager.XmlParseManager;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;
import com.chanpinzazhi.update.UpdateManager;
import com.chanpinzazhi.view.SwitchButton;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;

public class SettingActivity extends BaseActivity implements OnClickListener {
	protected static final String TAG = "SettingActivity";
	private ImageView btn_return, btn_setting;
	private TextView btn_feedback, btn_share, btn_about, btn_clear, btn_update;
	private SwitchButton switchBtn;
	private static UMSocialService mController = UMServiceFactory
			.getUMSocialService("分享", RequestType.SOCIAL);
	private Dialog clearChacheDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		TitleManager.showTitle(this, new int[] { 1 }, R.string.txt_setting);
	}

	private void initView() {
		switchBtn = (SwitchButton) findViewById(R.id.checkbox);
		if (PreferenceManager.getBoolean(this, "pushcheck")) {
			switchBtn.setChecked(true);
		}
		switchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction("com.chanpinzazhi.pushservice");
				if (isChecked) {
					ToastManager.show(SettingActivity.this, "开启推送");
					intent.putExtra("status", 1);
				} else {
					ToastManager.show(SettingActivity.this, "关闭推送");
					intent.putExtra("status", 0);
				}
				PreferenceManager.setBoolean(SettingActivity.this, "pushcheck",
						isChecked);
				SettingActivity.this.sendOrderedBroadcast(intent, null);
			}
		});
		btn_feedback = (TextView) findViewById(R.id.btn_feedback);
		btn_feedback.setOnClickListener(this);
		btn_return = (ImageView) findViewById(R.id.btn_return);
		btn_return.setOnClickListener(this);
		btn_share = (TextView) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);
		btn_about = (TextView) findViewById(R.id.btn_about);
		btn_about.setOnClickListener(this);
		btn_setting = (ImageView) findViewById(R.id.btn_setting);
		btn_setting.setVisibility(View.GONE);
		btn_clear = (TextView) findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(this);
		btn_update = (TextView) findViewById(R.id.btn_update);
		btn_update.setOnClickListener(this);

	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_feedback:
			UIManager.switcher(this, FeedBackActivity.class);
			break;
		case R.id.btn_share:
			ShareManager shareManager = new ShareManager(new UMImage(
					SettingActivity.this, R.drawable.share), "产品杂志",
					"这是一个神奇的应用,谁用谁知道...", "http://www.zengwentao.com");
			shareManager.share(SettingActivity.this);
			UMServiceFactory.getUMSocialService("分享测试！！", RequestType.SOCIAL)
					.registerListener(new OnSensorListener() {
						@Override
						public void onStart() {
							Toast.makeText(SettingActivity.this,
									getString(R.string.share_start),
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode,
								SocializeEntity entity) {
							Toast.makeText(SettingActivity.this,
									getString(R.string.share_over),
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onActionComplete(SensorEvent event) {

							Toast.makeText(SettingActivity.this,
									getString(R.string.share_doing),
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onButtonClick(WhitchButton button) {
							if (button == WhitchButton.BUTTON_CANCEL) {
								Toast.makeText(SettingActivity.this,
										getString(R.string.share_cancel),
										Toast.LENGTH_SHORT).show();
							} else {

							}
						}
					});
			break;
		case R.id.btn_about:
			UIManager.switcher(this, AboutActivity.class);
			break;
		case R.id.btn_update:
			// 检测新的版本

			SoapHttpRequest mrequest = new SoapHttpRequest(this);
			mrequest.GetAPPConfig(SoapConstants.GEAPPCONFIG, this, mHandler,
					"11164,11169");
			break;
		case R.id.btn_clear:
			clearChacheDialog = UIManager.getCommWarnDialog(this, "提示",
					"确定清除缓存吗?", R.string.txt_btn_ok, R.string.txt_btn_cancel,
					this);
			clearChacheDialog.show();
			break;
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_ok:
			imageLoader.clearMemoryCache();
			imageLoader.clearDiscCache();
			ToastManager.show(SettingActivity.this, "清除缓存成功");
			break;
		case R.id.btn_cancel:
			clearChacheDialog.dismiss();
			break;

		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SoapConstants.FAIL_RESULT:
			case SoapConstants.NO_NET:
				L.e(TAG, "网络不给力，请稍后重试");
				break;
			case SoapConstants.FAIL_XML_RESULT:
				L.e(TAG, "字段解析出错");
				break;
			case SoapConstants.TIMEOUT_RESULT:
				ToastManager.show(SettingActivity.this, "请求超时");
				break;

			case SoapConstants.GEAPPCONFIG:// 成功

				if (msg.obj == null) {
					ToastManager.show(SettingActivity.this, "网络出了问题，请稍后再试...");
				} else {
					String config_result = msg.obj.toString();
					config_result = config_result.replaceAll("11164", "a11164");
					config_result = config_result.replaceAll("11169", "a11169");
					L.e(TAG, "获取appconfig结果==" + config_result);

					try {
						Map<String, String> map = XmlParseManager
								.parseAppConfig(config_result);
						if (map.get("Number").equals("1")) {

							String version = map.get("version");
							String adds = map.get("version_url");
							BaseApplication.downUrl = adds;
							ToastManager.show(SettingActivity.this, "最新的版本:"
									+ version + adds);
							UpdateManager manager = new UpdateManager(
									SettingActivity.this);
							manager.checkUpdate(Integer.parseInt(version));
							// manager.checkUpdate(3);

						} else if (map.get("Number").equals("-1006")) {
							ToastManager.show(SettingActivity.this, "无效的站点ID");
						} else if (map.get("Number").equals("-1200")) {
							Dialog mDialog = UIManager
									.getCommWarnDialog2(SettingActivity.this);
							mDialog.show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;

			case SoapConstants.LOADING: {

				break;
			}
			default:
				break;
			}
		}
	};
}