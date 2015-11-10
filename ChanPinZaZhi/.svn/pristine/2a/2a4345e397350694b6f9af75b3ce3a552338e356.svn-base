package com.chanpinzazhi.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.R;
import com.chanpinzazhi.entity.Product;
import com.chanpinzazhi.manager.ConfigManager;
import com.chanpinzazhi.manager.DensityUtil;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.ShareManager;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.view.CustomViewPager;
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

public class SearchResultPager extends BaseActivity {
	private static final String TAG = "SearchResultPager";
	String result;
	ArrayList<Product> mData = new ArrayList<Product>();
	CustomViewPager pager;
	ImagePagerAdapter imageAdapter;
	private int width;
	private int high; 
	private TextView tv_title;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chanpinpager);
		TitleManager.showTitle(this, new int[] { 1 },
				R.string.txt_search_result);
		initData();
		initView();
	}

	void initData() {
		result = getIntent().getExtras().getString("result");
		L.e(TAG, result);
		try {
			parseResult(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(mData.get(0).getName());
		width = getWindowManager().getDefaultDisplay().getWidth();
		high = getWindowManager().getDefaultDisplay().getHeight();
		pager = (CustomViewPager) findViewById(R.id.pager);
		imageAdapter = new ImagePagerAdapter(mData);
		pager.setAdapter(imageAdapter);
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	void parseResult(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser(); //
		parser.setInput(xml, "utf-8"); //
		mData = new ArrayList<Product>();

		Product pro = null;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:

				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Row")) {
					pro = new Product();
					// pro.setCategoryId(typeId);
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
				}else if (name.equalsIgnoreCase("Name")) {
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
					pro.setImgUrl(StringManager.getBigImgUrl(this,imgurl));
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
					mData.add(pro);
					pro = null;
				}
				break;
			}
			eventType = parser.next();
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private List<Product> data;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<Product> data) {
			this.data = data;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, final int position) {
			final View imageLayout = inflater.inflate(
					R.layout.activity_chanpin, view, false);
			assert imageLayout != null;
			final ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.normal_image);

			final WrapSlidingDrawer detailLayout = (WrapSlidingDrawer) imageLayout
					.findViewById(R.id.detailLayout);
			detailLayout.setmTouchableIds(new int[] { R.id.xiangce,
					R.id.btn_share, R.id.tv_tuijian });
			detailLayout.setmHandleId(R.id.tv_introduce);

			TextView tv_intro = (TextView) imageLayout
					.findViewById(R.id.tv_introduce);
			if (data.get(position).getDesc() == null
					|| data.get(position).getDesc().equals("")) {
				tv_intro.setText("该商品未添加详细说明");
			} else {
				tv_intro.setText(Html.fromHtml(data.get(position).getDesc()));
			}
			TextView tv_bianhao = (TextView) imageLayout
					.findViewById(R.id.tv_bianhao);
			tv_bianhao.setText("编号：" + data.get(position).getProductId());

			TextView tv_title = (TextView) imageLayout
					.findViewById(R.id.tv_title);
			tv_title.setText(data.get(position).getName());

			TextView tv_category = (TextView) imageLayout
					.findViewById(R.id.tv_category); 
			tv_category.setText(data.get(position).getCategoryName());

			Bitmap title = BitmapFactory.decodeResource(getResources(),
					R.drawable.top_bg);

			final int ihigh = high
					- DensityUtil.dip2px(getApplicationContext(), 112)
					- title.getHeight()
					- DensityUtil.dip2px(getApplicationContext(), 25);

			imageView.setLayoutParams(new RelativeLayout.LayoutParams(width,
					ihigh));

			ImageButton tv_xiangce = (ImageButton) imageLayout
					.findViewById(R.id.xiangce);

			tv_xiangce.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(SearchResultPager.this,
							GalleryActivity.class);
					int productId = (Integer) data.get(position).getProductId();
					intent.putExtra("id", productId);
					String rImage = data.get(position).getRelatedImages();
					intent.putExtra("rImage", rImage);
					intent.putExtra("pName", data.get(position).getName());
					startActivity(intent);
				}
			});

			ImageView btn_share = (ImageView) imageLayout
					.findViewById(R.id.btn_share);
			btn_share.setOnClickListener(new OnClickListener() {
				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					int navigateId = Integer.parseInt(PreferenceManager.getString(SearchResultPager.this,
							"_id","3"));
					ShareManager shareManager = new ShareManager(new UMImage(
							SearchResultPager.this, data.get(position)
									.getImgUrl().toString()), data
							.get(position).getName().toString(), StringManager
							.htmlRemoveTag(data.get(position).getDesc()
									.toString()), ConfigManager
									.getSiteDomain(SearchResultPager.this)
									+ " /share.aspx?nid=" + navigateId + "id=" + 3);
					shareManager.share(SearchResultPager.this);
					UMServiceFactory.getUMSocialService("分享测试！！",
							RequestType.SOCIAL).registerListener(
							new OnSensorListener() {
								@Override
								public void onStart() {
									Toast.makeText(SearchResultPager.this,
											getString(R.string.share_start),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onComplete(SHARE_MEDIA platform,
										int eCode, SocializeEntity entity) {
									Toast.makeText(SearchResultPager.this,
											getString(R.string.share_over),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onActionComplete(SensorEvent event) {

									Toast.makeText(SearchResultPager.this,
											getString(R.string.share_doing),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onButtonClick(WhitchButton button) {
									if (button == WhitchButton.BUTTON_CANCEL) {
										Toast.makeText(
												SearchResultPager.this,
												getString(R.string.share_cancel),
												Toast.LENGTH_SHORT).show();
									} else {

									}
								}
							});
				}
			});
			L.e(TAG, "产品大图的路径" + data.get(position).getImgUrl());
			ImageLoader.getInstance().displayImage(
					data.get(position).getImgUrl(), imageView, options,
					new SimpleImageLoadingListener() {

						@Override
						public void onLoadingStarted(String imageUri, View view) {
							// spinner.setVisibility(View.VISIBLE);
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
							Toast.makeText(SearchResultPager.this, message,
									Toast.LENGTH_SHORT).show();

							// spinner.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							// spinner.setVisibility(View.GONE);
						}
					});

			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}
	}
	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			tv_title.setText(mData.get(arg0).getName());
		}

	}
}
