package com.chanpinzazhi.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.xmlpull.v1.XmlPullParser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Xml;

import com.chanpinzazhi.R;
import com.chanpinzazhi.entity.NewContent;
import com.chanpinzazhi.manager.DeviceManager;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;

public class PushService extends Service {

	private static final String TAG = "PushService";
	private Timer mTimer;
	private MyTimerTask mTimerTask;
	private static int index;
	ArrayList<NewContent> mData = new ArrayList<NewContent>();
	ArrayList<CharSequence> pData = new ArrayList<CharSequence>();
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SoapConstants.FAIL_RESULT:
			case SoapConstants.NO_NET:
				L.e(TAG, "网络不给力，请售后重试");
				break;
			case SoapConstants.TIMEOUT_RESULT:
				ToastManager.show(PushService.this, "请求超时");
				break;
			case SoapConstants.GETNEWCONTENTS: {
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "消息推送==" + result);
					try {
						parseResult(result);
						if (mData.size() != 0) {
							showNotifycation("产品杂志", "你好，有"+mData.size()+"条内容更新.", pData);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			}

			default:
				break;
			}
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		L.e(TAG, "onCreate");
		mTimer = new Timer(true);
	}

	@Override
	public void onStart(Intent intent, int startid) {
		L.e(TAG, "My Service Start");
		if (mTimer != null) {
			if (mTimerTask != null) {
				mTimerTask.cancel(); // 将原任务从队列中移除
			}
			mTimerTask = new MyTimerTask(); // 新建一个任务
			mTimer.schedule(mTimerTask, 1000,SoapConstants.PUSHTIME );// 10S后启动任务
		}
	}

	void parseResult(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser(); //
		parser.setInput(xml, "utf-8"); //
		mData = new ArrayList<NewContent>();

		NewContent pro = null;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:

				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Result")) {
					String currentTime = parser.getAttributeValue(null,
							"CurrentDateTime");
					PreferenceManager.setString(this, "currentTime",
							currentTime);
					break;
				} else if (name.equalsIgnoreCase("Row")) {
					pro = new NewContent(); 
					break;
				} else if (name.equalsIgnoreCase("Id")) {
					eventType = parser.next();
					String id = parser.getText(); // 产品类别ID
					pro.setId(id);
					pData.add(id);
					break;
				} else if (name.equalsIgnoreCase("Name")) {
					eventType = parser.next();
					String cname = parser.getText(); // 类别图片的相对路径
					pro.setName(cname);
					break;
				} else if (name.equalsIgnoreCase("UpdateTime")) {
					eventType = parser.next();
					String time = parser.getText(); // 类别图片的相对路径
					pro.setUpdateTime(time);
					break;
				} else if (name.equalsIgnoreCase("NavigateId")) {
					eventType = parser.next();
					String navid = parser.getText(); // 产品类别的介绍
					pro.setNavigateId(navid);
					break;
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {
					mData.add(pro);
					pro = null;
				}
				break;
			}
			eventType = parser.next();
		}
	}

	/**
	 * 新建通知
	 * 
	 * @param title
	 * @param content
	 * @param id
	 */
	@SuppressWarnings("deprecation")
	void showNotifycation(String title, String content,
			ArrayList<CharSequence> data) {
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, null, when);// 第一个参数为图标,第二个参数为标题,第三个为通知时间
		// 点击notification之后，该notification自动消失
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent openintent = null;
		openintent = new Intent(this, NotifyActivity.class);
		Bundle bundle = new Bundle(); 
		bundle.putCharSequenceArrayList("result", data);
		L.e(TAG, "传递的数据=="+data.toString());
		openintent.putExtras(bundle);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				openintent, PendingIntent.FLAG_UPDATE_CURRENT);// 当点击消息时就会向系统发送openintent意图
		notification.setLatestEventInfo(this, title, content, contentIntent);
		mNotificationManager.notify(0, notification);
	}

	@Override
	public void onDestroy() {
		L.e(TAG, "My Service Stoped");
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			L.i(TAG, "Task Run..." + index);
			index++;
			String currentTime = PreferenceManager.getString(PushService.this,
					"currentTime", formatDateTime());
			SoapHttpRequest request = new SoapHttpRequest(PushService.this);
			request.GetNewContents(SoapConstants.GETNEWCONTENTS,
					PushService.this, mHandler,
					DeviceManager.getMyUUID(PushService.this), 4,
					"17,10", currentTime);
			L.e(TAG, "请求时间==" + currentTime);
		}
	}

	/**
	 * 获取当前格式化时间
	 * 
	 * @return
	 */
	private String formatDateTime() {
		long updateTime = System.currentTimeMillis();
		PreferenceManager.setLong(this, "lastupdateTime",
				System.currentTimeMillis());
		String timestamp = StringManager.getTime(updateTime);
		return timestamp;
	}
}
