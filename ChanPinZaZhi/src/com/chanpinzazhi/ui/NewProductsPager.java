package com.chanpinzazhi.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.R;
import com.chanpinzazhi.entity.Product;
import com.chanpinzazhi.manager.ConfigManager;
import com.chanpinzazhi.manager.DeviceManager;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.ShareManager;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;
import com.chanpinzazhi.view.WrapSlidingDrawer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;

public class NewProductsPager extends BaseActivity {
	private static final String TAG = "NewProductsPager";
	int typeId;
	Product pro = null;
	ImageView imageView;
	TextView tv_intro;
	private TextView tv_title;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SoapConstants.FAIL_RESULT:
			case SoapConstants.NO_NET:
				ToastManager.show(NewProductsPager.this, "网络不给力，请售后重试");
				break;
			case SoapConstants.FAIL_XML_RESULT:
				ToastManager.show(NewProductsPager.this, "字段解析出错");
				break;
			case SoapConstants.TIMEOUT_RESULT:
				ToastManager.show(NewProductsPager.this, "请求超时");
				break;
			case SoapConstants.GETPRODUCT:
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "推荐的产品==" + result);
					try {
						parseProResult(result);
						initView();

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newpd);
		TitleManager.showTitle(this, new int[] { 1 }, R.string.txt_tuijian);
		initData();
		initNet(typeId);
	}

	void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(pro.getName());
		imageView = (ImageView) findViewById(R.id.new_normal_image);
		ImageLoader.getInstance().displayImage(pro.getImgUrl(), imageView,
				options, new SimpleImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						String message = null;
						switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
						}
						Toast.makeText(NewProductsPager.this, message,
								Toast.LENGTH_SHORT).show();

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
					}
				});

		WrapSlidingDrawer detailLayout = (WrapSlidingDrawer) findViewById(R.id.new_detailLayout);
		detailLayout.setmTouchableIds(new int[] { R.id.new_xiangce,
				R.id.new_btn_share });
		tv_intro = (TextView) findViewById(R.id.new_tv_introduce);
		detailLayout.setmHandleId(R.id.new_tv_introduce);
		if (pro.getDesc() == null || pro.getDesc().equals("")) {
			tv_intro.setText("该商品未添加详细说明");
		} else {
			tv_intro.setText(Html.fromHtml(pro.getDesc()));
		}
		TextView tv_bianhao = (TextView) findViewById(R.id.new_tv_bianhao);
		tv_bianhao.setText("编号：" + pro.getProductId());

		TextView tv_title = (TextView) findViewById(R.id.new_tv_title);
		Log.e(TAG, "推荐的名称==" + pro.getName());
		tv_title.setText(pro.getName());

		TextView tv_category = (TextView) findViewById(R.id.new_tv_category);
		tv_category.setText(pro.getCategoryName());

		ImageButton tv_xiangce = (ImageButton) findViewById(R.id.new_xiangce);

		tv_xiangce.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(NewProductsPager.this,
						GalleryActivity.class);
				int productId = (Integer) pro.getProductId();
				intent.putExtra("id", productId);
				String rImage = pro.getRelatedImages();
				intent.putExtra("rImage", rImage);
				intent.putExtra("pName", pro.getName());
				startActivity(intent);
			}
		});
		ImageView btn_share = (ImageView) findViewById(R.id.new_btn_share);
		btn_share.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				int navigateId = Integer.parseInt(PreferenceManager.getString(
						NewProductsPager.this, "_id", "3"));
				ShareManager shareManager = new ShareManager(new UMImage(
						NewProductsPager.this, pro.getImgUrl().toString()), pro
						.getName().toString(), StringManager.htmlRemoveTag(pro
						.getDesc().toString()), ConfigManager
						.getSiteDomain(NewProductsPager.this)
						+ " /share.aspx?nid=" + navigateId + "id=" + typeId);
				shareManager.share(NewProductsPager.this);
				UMServiceFactory.getUMSocialService("分享测试！！",
						RequestType.SOCIAL).registerListener(
						new OnSensorListener() {
							@Override
							public void onStart() {
								Toast.makeText(NewProductsPager.this,
										getString(R.string.share_start),
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onComplete(SHARE_MEDIA platform,
									int eCode, SocializeEntity entity) {
								Toast.makeText(NewProductsPager.this,
										getString(R.string.share_over),
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onActionComplete(SensorEvent event) {

								Toast.makeText(NewProductsPager.this,
										getString(R.string.share_doing),
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onButtonClick(WhitchButton button) {
								if (button == WhitchButton.BUTTON_CANCEL) {
									Toast.makeText(NewProductsPager.this,
											getString(R.string.share_cancel),
											Toast.LENGTH_SHORT).show();
								} else {

								}
							}
						});
			}
		});
	}

	void initData() {
		typeId = Integer.parseInt(getIntent().getStringExtra("typeId"));
	}

	/**
	 * 根据栏目ID查询产品集合带相关连接
	 * 
	 * @param navigateId
	 */
	void initNet(int typeId) {
		SoapHttpRequest request = new SoapHttpRequest(this);
		request.GetByNavigateId(
				SoapConstants.GETPRODUCT,
				this,
				mHandler,
				DeviceManager.getMyUUID(this),
				4,
				2,
				"[Id],[App_TypeName],[Name],[App_Introduce],[App_BigImageUrl],[RelatedImages]",
				"[App_IsShow]=1 and [Id]=" + typeId, "[Order] DESC", 0, 10, "App_Introduce",
				false, "2014-04-16 08:55:00", false);
	}

	void parseProResult(String result) throws Exception {
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
						L.d("ChanpinActivity", "请求成功");
					} else {
						L.d("ChanpinActivity", "请求失败");
					}
					break;
				} else if (name.equalsIgnoreCase("Row")) {
					pro = new Product();
					pro.setCategoryId(typeId);
					break;
				} else if (name.equalsIgnoreCase("Id")) {
					eventType = parser.next();
					int id = Integer.parseInt(parser.getText()); // 产品类别ID
					pro.setProductId(id);
					break;
				} else if (name.equalsIgnoreCase("App_TypeName")) {
					eventType = parser.next();
					String cName = parser.getText(); // 类别图片的相对路径
					pro.setCategoryName(cName);
					break;
				} else if (name.equalsIgnoreCase("Name")) {
					eventType = parser.next();
					String pName = parser.getText(); // 类别图片的相对路径
					pro.setName(pName);
					break;
				} else if (name.equalsIgnoreCase("App_Introduce")) {
					eventType = parser.next();
					String introduce = parser.getText(); // 产品类别的介绍
					pro.setDesc(introduce);
					break;
				} else if (name.equalsIgnoreCase("App_BigImageUrl")) {
					eventType = parser.next();
					String imgurl = parser.getText(); // 产品类别的介绍
					pro.setImgUrl(StringManager.getBigImgUrl(this, imgurl));
					break;
				} else if (name.equalsIgnoreCase("RelatedImages")) {
					eventType = parser.next();
					String rImgurl = parser.getText(); // 产品类别的介绍
					pro.setRelatedImages(rImgurl);
					break;
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {
					L.e(TAG, "推荐的产品实例==" + pro.toString());
				}
				break;
			}
			eventType = parser.next();
		}
	}
}
