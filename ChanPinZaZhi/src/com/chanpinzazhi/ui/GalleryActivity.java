package com.chanpinzazhi.ui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanpinzazhi.R;
import com.chanpinzazhi.db.DBPhotoService;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.ToastManager;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;

/**
 * 产品相册
 * 
 * @author Administrator
 * 
 */
public class GalleryActivity extends BaseActivity implements OnClickListener {
	DBPhotoService service;
	private int productId;
	// List<Map<String, Object>> photoItems = new ArrayList<Map<String,
	// Object>>();
	List<String> data = new ArrayList<String>();
	ViewPager pager;
	String relativeImages;
	private int mCurrentViewID = 0; //
	ImageView closeButton, gallery_up, gallery_next;
	String[] imgs = null;
	String pName;
	private TextView tv_pname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chanpin_xiangce);
		relativeImages = getIntent().getStringExtra("rImage");
		pName = getIntent().getStringExtra("pName");
		
		L.e("GalleryActivity", "产品图片" + relativeImages);
		initOption();
		initData();
		initView();

	}

	void initData() {
		if(!relativeImages.equals("")||relativeImages!=null){
			imgs = relativeImages.split(",");
			for (int i = 0; i < imgs.length; i++) {
				data.add(imgs[i]);
			} 
			pager = (ViewPager) findViewById(R.id.pager);
			ImagePagerAdapter adapter = new ImagePagerAdapter(data);
			pager.setAdapter(adapter);
			pager.setCurrentItem(0);
			pager.setOnPageChangeListener(mOnPageChangeListener);
		}else{
			ToastManager.show(this, "亲，该产品还未添加相册");
		}
	}

	private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageSelected(int currentID) {
			// TODO Auto-generated method stub
			mCurrentViewID = currentID;
		}
	};

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.gallery_up:
				if (mCurrentViewID != 0) {
					mCurrentViewID--;
					pager.setCurrentItem(mCurrentViewID, true);
				} else {
					ToastManager.show(GalleryActivity.this, "亲，第一张...");
				}
				// mScroller.setmDuration(100);
				break;
			case R.id.gallery_next:
				if (mCurrentViewID != data.size() - 1) {
					mCurrentViewID++;
					pager.setCurrentItem(mCurrentViewID, true);
				} else {
					ToastManager.show(GalleryActivity.this, "亲,最后一张了...");
				}
				// mScroller.setmDuration(200);
				break;
			}

		}
	};

	private void initView() {
		closeButton = (ImageView) this.findViewById(R.id.close);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		gallery_up = (ImageView) this.findViewById(R.id.gallery_up);

		gallery_up.setOnClickListener(mOnClickListener);
		gallery_next = (ImageView) this.findViewById(R.id.gallery_next);

		gallery_next.setOnClickListener(mOnClickListener);
		tv_pname = (TextView)findViewById(R.id.tv_pname);
		tv_pname.setText(pName);
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private List<String> data;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<String> data) {
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
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.photo, view, false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout
					.findViewById(R.id.photo_imageView);

			// final ProgressBar spinner = (ProgressBar) imageLayout
			// .findViewById(R.id.loading);
			String img_url = StringManager.getPhotoImgUrl(GalleryActivity.this,data.get(position));
			imageLoader.displayImage(img_url, imageView, options,
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}
}
