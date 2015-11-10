package com.chanpinzazhi.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.BaseApplication;
import com.chanpinzazhi.R;
import com.chanpinzazhi.adapter.ViewFlowAdapter;
import com.chanpinzazhi.entity.Xiangce_result;
import com.chanpinzazhi.manager.DeviceManager;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.NetManager;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.manager.UIManager;
import com.chanpinzazhi.manager.XmlParseManager;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;
import com.chanpinzazhi.update.UpdateManager;
import com.chanpinzazhi.view.CircleFlowIndicator;
import com.chanpinzazhi.view.ViewFlow;
import com.thoughtworks.xstream.XStream;

@SuppressLint("HandlerLeak")
public class MainActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "MainActivity";
	private ImageView setting_btn;
	SoapObject rpc;
	SoapSerializationEnvelope envelope;
	boolean flag;
	// private ProgressBar spinner;
	private TextView main_column, tv_main_desc;
	private LinearLayout main_columnname;
	private boolean pushcheck;
	private ViewFlow viewFlow; // 进行图片轮播的viewFlow

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TitleManager.showTitle(this, new int[] { 2 }, R.string.appname);
		initOption();
		initView();
		initFlow();
		// 从网络获取数据
		initData();
	}

	private void initView() {
		main_column = (TextView) findViewById(R.id.main_column);
		main_column.setOnClickListener(this);
		tv_main_desc = (TextView) findViewById(R.id.tv_main_desc);
		main_columnname = (LinearLayout) findViewById(R.id.main_columnname);
		main_columnname.setOnClickListener(this);
		setting_btn = (ImageView) findViewById(R.id.btn_setting);
		setting_btn.setOnClickListener(this);
	}

	ViewFlowAdapter viewFlowAdapter;

	void initFlow() {
		viewFlow = (ViewFlow) this.findViewById(R.id.viewflow);// 获得viewFlow对象
		viewFlowAdapter = new ViewFlowAdapter(this);
		viewFlow.setAdapter(viewFlowAdapter); // 对viewFlow添加图片
		viewFlow.setmSideBuffer(3);
		CircleFlowIndicator indic = (CircleFlowIndicator) this
				.findViewById(R.id.viewflowindic); // viewFlow下的indic
		viewFlow.setFlowIndicator(indic);

		viewFlow.setTimeSpan(3000);
		viewFlow.setSelection(3 * 1000); // 设置初始位置
		viewFlow.startAutoFlowTimer(); // 启动自动播放
	}

	void initData() {
		if (NetManager.isNetworkConnected(this)) {
			// 检测新的版本

			SoapHttpRequest mrequest = new SoapHttpRequest(this);
			mrequest.GetAPPConfig(SoapConstants.GEAPPCONFIG, this, mHandler,
					"11164,11169");

			mrequest.GetAPPElements(DeviceManager.getMyUUID(this),
					SoapConstants.GetAPPELEMENTS, this, mHandler, "1");

			// 开启消息推送                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			pushcheck = PreferenceManager.getBoolean(this, "pushcheck");
			if (pushcheck) {
				Intent intent = new Intent();
				intent.setAction("com.chanpinzazhi.pushservice");
				intent.putExtra("status", 1);
				sendOrderedBroadcast(intent, null);
			}

			SoapHttpRequest request = new SoapHttpRequest(this);
			request.getNavByTableId(SoapConstants.GETBYTABLEID, this, mHandler,
					DeviceManager.getMyUUID(this), 4, 8,
					"[Id],[App_Name],[App_Description]", "[App_IsShow]=1",
					"[Order] DESC", 0, 1, "", false, "2014-04-10 08:55:00");

		} else {// 没有网络
			if (PreferenceManager.getString(this, "_id", "").equals("")) {// 没有请求过
				ToastManager.show(MainActivity.this, "亲，没有网络,请先检查网络");
				String nav_name = PreferenceManager.getString(this,
						"_nav_name", "栏目名称");
				main_column.setText(nav_name);
			} else {// 已经请求过
				ToastManager.show(MainActivity.this, "亲，没有网络,进行离线浏览模式");
				String nav_name = PreferenceManager.getString(this,
						"_nav_name", "");
				main_column.setText(nav_name);
				String main_desc = PreferenceManager.getString(this,
						"_nav_desc", "进入");
				tv_main_desc.setText(main_desc);
			}
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_setting:
			UIManager.switcher(this, SettingActivity.class);
			break;

		case R.id.main_column:
			Intent intent = new Intent(this, ProductActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_bottom, R.anim.out_of_top);
			break;

		case R.id.main_columnname:
			Intent intent2 = new Intent(this, ProductActivity.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.in_from_bottom, R.anim.out_of_top);
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
				ToastManager.show(MainActivity.this, "请求超时");
				break;
			case SoapConstants.GETBYTABLEID:// 成功
				if (msg.obj == null) {
					ToastManager.show(MainActivity.this, "网络出了问题，请稍后再试...");
				} else {
					String result = msg.obj.toString();
					L.e(TAG, "栏目结果==" + result);
					try {
						Map<String, String> map = XmlParseManager
								.parseNav(result);
						if (map.get("Number").equals("1")) {
							PreferenceManager.setString(MainActivity.this,
									"_id", map.get("_id"));
							PreferenceManager.setString(MainActivity.this,
									"_nav_name", map.get("_name"));
							main_column.setText(map.get("_name"));

							PreferenceManager.setString(MainActivity.this,
									"_nav_desc", map.get("_desc"));
							tv_main_desc.setText(map.get("_desc"));
						} else if (map.get("Number").equals("-1006")) {
							ToastManager.show(MainActivity.this, "无效的站点ID");
						} else if (map.get("Number").equals("-1200")) {
							Dialog mDialog = UIManager
									.getCommWarnDialog2(MainActivity.this);
							mDialog.show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case SoapConstants.GEAPPCONFIG:// 成功

				if (msg.obj == null) {
					ToastManager.show(MainActivity.this, "网络出了问题，请稍后再试...");
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
							ToastManager.show(MainActivity.this, "最新的版本:"
									+ version + adds);
							UpdateManager manager = new UpdateManager(
									MainActivity.this);
							manager.checkUpdate(Integer.parseInt(version));
//							manager.checkUpdate(3);

						} else if (map.get("Number").equals("-1006")) {
							ToastManager.show(MainActivity.this, "无效的站点ID");
						} else if (map.get("Number").equals("-1200")) {
							Dialog mDialog = UIManager
									.getCommWarnDialog2(MainActivity.this);
							mDialog.show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case SoapConstants.GetAPPELEMENTS: {

				if (msg.obj == null) {
					ToastManager.show(MainActivity.this, "网络出了问题，请稍后再试...");
				} else {
					String xiangce_result = msg.obj.toString();
					L.e(TAG, "获取xiangce_result结果==" + xiangce_result);
					// xml to object
					XStream xstream = new XStream();
					// 使用注解
					xstream.processAnnotations(Xiangce_result.class);// 显示声明使用注解
					xstream.autodetectAnnotations(true);
					// 转换为对象
					Xiangce_result xc = (Xiangce_result) xstream
							.fromXML(xiangce_result);
					L.i("###########################获取xiangce_result结果=="
							+ xc.toString());

					if (xc != null) {
						if (xc.getNumber() == 1) {
							List<String> files = xc.getFiles();
							if (files != null && files.size() > 0) {
								String siteDomain = getResources().getString(
										R.string.siteDomain);
								String[] _images = new String[files.size()];
								for (int i = 0; i < files.size(); i++) {
									String temp = siteDomain + files.get(i);
									_images[i] = temp;
								}
								L.i("###########################获取_images="
										+ Arrays.toString(_images));

								viewFlowAdapter = new ViewFlowAdapter(
										MainActivity.this);
								viewFlowAdapter.setRes(_images);
								viewFlow.setAdapter(viewFlowAdapter);
								int sideBuffer = _images.length;
								if (_images.length > 5) {
									sideBuffer = 5;
								}
								viewFlow.setmSideBuffer(sideBuffer);
								CircleFlowIndicator indic = (CircleFlowIndicator) MainActivity.this
										.findViewById(R.id.viewflowindic); // viewFlow下的indic
								viewFlow.setFlowIndicator(indic);
								indic.requestLayout();
								viewFlow.setTimeSpan(3000);
								viewFlow.setSelection(3 * 1000); // 设置初始位置
								viewFlow.startAutoFlowTimer(); // 启动自动播放
								// 设置圆圈的颜色值
								indic.setStrokeColor(Color.BLACK);
							}

						} else if (xc.getNumber() == -1006) {
							ToastManager.show(MainActivity.this, "无效的站点ID");
						} else if (xc.getNumber() == -1200) {
							Dialog mDialog = UIManager
									.getCommWarnDialog2(MainActivity.this);
							mDialog.show();
						}
					}
				}

				break;
			}

			case SoapConstants.LOADING: {

				break;
			}

			case 0: {
				flag = false;
				break;
			}

			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void exit() {
		String appname = getResources().getString(R.string.appname);
		if (!flag) {
			flag = true;
			Toast.makeText(getApplicationContext(), "再按一次退出" + appname,
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);

		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}
}
