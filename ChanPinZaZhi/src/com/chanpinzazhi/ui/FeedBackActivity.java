package com.chanpinzazhi.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.chanpinzazhi.R;
import com.chanpinzazhi.manager.DeviceManager;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;
import com.chanpinzazhi.view.MyspinnerAdapter;

/**
 * 意见反馈
 * 
 * @author Administrator
 * 
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "FeedBackActivity";
	private ImageView btn_return, btn_setting;
	private EditText edt_content, edt_email, edt_mobile;
	private Button btn_post;
	private MyspinnerAdapter adapter;
	private PopupWindow popupWindow;
	private LinearLayout spinnerlayout;
	private ArrayList<Map<String, Object>> list;
	private ImageView imgView;
	private TextView textView;
	private ListView listView;
	private LinearLayout layout;
	private int typeId;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SoapConstants.POST:
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e("HTTP", "提交后的返回结果==" + result);
					try {
						parseResult(result);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case SoapConstants.GETBYTABLEID:// 成功
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "意见反馈类别==" + result);
					try {
						parseFeedbackId(result);
						adapter = new MyspinnerAdapter(FeedBackActivity.this,
								list);
						typeId = Integer.parseInt(list.get(0).get("id").toString());
						textView.setText((CharSequence) adapter.getItem(0));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					L.e(TAG, "意见反馈类别获取失败");
				}

				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initView();
		initTypeId();
		TitleManager.showTitle(this, new int[] { 1 }, R.string.txt_feedback);
	}

	private void initView() {
		btn_return = (ImageView) findViewById(R.id.btn_return);
		btn_return.setOnClickListener(this);
		btn_setting = (ImageView) findViewById(R.id.btn_setting);
		btn_setting.setVisibility(View.GONE);
		edt_content = (EditText) findViewById(R.id.edt_content);
		edt_email = (EditText) findViewById(R.id.edt_email);
		edt_mobile = (EditText) findViewById(R.id.edt_mobile);
		btn_post = (Button) findViewById(R.id.btn_post);
		btn_post.setOnClickListener(this);
		textView = (TextView) findViewById(R.id.textView2);
		imgView = (ImageView) findViewById(R.id.imageView1);

		spinnerlayout = (LinearLayout) findViewById(R.id.spinnerid);
		// 点击右侧按钮，弹出下拉框
		imgView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (list != null && list.size() > 0) {
					spinnerlayout
							.setBackgroundResource(R.drawable.preference_first_item);
					showWindow(spinnerlayout, textView);
				} else {
					ToastManager.show(FeedBackActivity.this, "暂无类别");
				}
			}
		});
	}

	/**
	 * 获取意见反馈的ID
	 */
	void initTypeId() {
		SoapHttpRequest request = new SoapHttpRequest(this);
		request.getNavByTableId(SoapConstants.GETBYTABLEID, this, mHandler,
				DeviceManager.getMyUUID(this), 4, 3, "[Id],[App_Name]",
				"App_IsShow=1", "[Order] DESC", 0, 0, "", false,
				"2014-04-10 08:55:00");
	}

	void parseFeedbackId(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(xml, "utf-8");
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:

				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Result")) {
					String mumber = parser.getAttributeValue(null, "Number");
					if (mumber.equals("1")) {
						L.e(TAG, "请求成功");
					} else {
						L.e(TAG, "请求失败");
					}
					break;
				} else if (name.equalsIgnoreCase("Row")) {
					map = new HashMap<String, Object>();
					break;
				} else if (name.equalsIgnoreCase("Id")) {
					eventType = parser.next();
					String id = parser.getText();
					map.put("id", id);
					break;
				} else if (name.equalsIgnoreCase("APP_Name")) {
					eventType = parser.next();
					String app_name = parser.getText();
					map.put("name", app_name);
					break;
				}
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {
					// mData.addFirst(mCategory);
					list.add(map);
					map = null;
				}
				break;
			}
			eventType = parser.next();
		}
	}

	public void showWindow(View position, final TextView txt) {

		layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.mypinner_dropdown, null);
		listView = (ListView) layout.findViewById(R.id.listView);
		listView.setAdapter(adapter);
		popupWindow = new PopupWindow(position);
		// 设置弹框的宽度为布局文件的宽
		popupWindow.setWidth(spinnerlayout.getWidth());
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置点击弹框外部，弹框消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setContentView(layout);
		// 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
		popupWindow.showAsDropDown(position, 0, 0);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				spinnerlayout
						.setBackgroundResource(R.drawable.preference_single_item);
			}

		});
		// listView的item点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				txt.setText(list.get(arg2).get("name").toString());// 设置所选的item作为下拉框的标题
				// 弹框消失
				popupWindow.dismiss();
				popupWindow = null;
				typeId = Integer.parseInt(list.get(arg2).get("id").toString());
			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_return:
			finish();
			break;
		case R.id.btn_post:
			if (StringManager.isEmpty(edt_content.getText().toString())) {
				ToastManager.show(this, "亲，请先填写你的宝贵意见喔！");
			} else if (!edt_mobile.getText().toString().equals("")
					&& !StringManager.isMobileNO(edt_mobile.getText()
							.toString())) {
				ToastManager.show(this, "亲，手机号码没有填对喔！");
			} else if (!edt_email.getText().toString().equals("")
					&& !StringManager
							.checkEmail(edt_email.getText().toString())) {
				ToastManager.show(this, "亲，邮箱地址没有填对喔！");
			} else {
				ToastManager.show(this, "typeId==" + typeId);
				SoapHttpRequest request = new SoapHttpRequest(this);
				request.postFeedback(
						this,
						mHandler,
						DeviceManager.getMyUUID(this),
						4,
						2,
						typeId,
						getSubmitContent(edt_content.getText().toString(),
								edt_mobile.getText().toString(), edt_email
										.getText().toString()));
			}
			break;
		default:
			break;
		}
	}

	String getSubmitContent(String content, String moblie, String email) {
		return "<Submit><Content>" + content + "</Content><Mobile>" + moblie
				+ "</Mobile><Email>" + email + "</Email></Submit>";
	}

	private void parseResult(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser(); //
		parser.setInput(xml, "utf-8"); //

		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:

				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Result")) {
					String mumber = parser.getAttributeValue(null, "Number");
					if (mumber.equals("1")) {
						ToastManager.show(this, "提交成功");

					} else {
						ToastManager.show(this, "提交失败");
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {

				}
				break;
			}
			eventType = parser.next();
		}
	}
}
