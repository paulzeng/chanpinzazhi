package com.chanpinzazhi.ui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chanpinzazhi.R;
import com.chanpinzazhi.adapter.SuperTreeViewAdapter;
import com.chanpinzazhi.adapter.TreeViewAdapter;
import com.chanpinzazhi.adapter.TreeViewAdapter.TreeNode;
import com.chanpinzazhi.db.DBPdService;
import com.chanpinzazhi.entity.ExpandMenu;
import com.chanpinzazhi.entity.MenuNode;
import com.chanpinzazhi.entity.Product;
import com.chanpinzazhi.entity.RelevantData;
import com.chanpinzazhi.manager.ConfigManager;
import com.chanpinzazhi.manager.DensityUtil;
import com.chanpinzazhi.manager.DeviceManager;
import com.chanpinzazhi.manager.L;
import com.chanpinzazhi.manager.NetManager;
import com.chanpinzazhi.manager.PreferenceManager;
import com.chanpinzazhi.manager.ShareManager;
import com.chanpinzazhi.manager.StringManager;
import com.chanpinzazhi.manager.TitleManager;
import com.chanpinzazhi.manager.ToastManager;
import com.chanpinzazhi.manager.UIManager;
import com.chanpinzazhi.slidingmenu.lib.SlidingMenu;
import com.chanpinzazhi.slidingmenu.lib.ui.SlidingActivity;
import com.chanpinzazhi.soap.SoapConstants;
import com.chanpinzazhi.soap.SoapHttpRequest;
import com.chanpinzazhi.view.ClearEditText;
import com.chanpinzazhi.view.CustomViewPager;
import com.chanpinzazhi.view.ListViewForScrollView;
import com.chanpinzazhi.view.WrapSlidingDrawer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sensor.UMSensor.OnSensorListener;
import com.umeng.socialize.sensor.UMSensor.WhitchButton;

/**
 * 产品页面
 * 
 * @author Administrator
 * 
 */
public class ChanpinActivity extends SlidingActivity implements OnClickListener {
	private static final String TAG = "ChanpinActivity";
	private ImageView chanpin_search_btn;
	private ImageView return_btn, btn_search;
	DBPdService service;
	ArrayList<Product> mData = new ArrayList<Product>();
	ArrayList<Product> newData;
	CustomViewPager pager;
	private int width;
	private int high;
	private SlidingMenu sm;
	/** Called when the activity is first created. */
	ExpandableListView expandableList;
	TreeViewAdapter adapter;
	SuperTreeViewAdapter superAdapter;
	ClearEditText search_edit;
	private int count;
	int navigateId;
	int typeId;
	String title;
	ImagePagerAdapter imageAdapter;
	List<ExpandMenu> menus;
	public static ChanpinActivity subProCate;
	public static boolean[] sizeBoolean;
	public static boolean[] isOpen;
	private int sizeInt;
	private int startPos;
	private TextView tv_title;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chanpinpager);
		subProCate = this;
		width = getWindowManager().getDefaultDisplay().getWidth();
		high = getWindowManager().getDefaultDisplay().getHeight();
		TitleManager.showTitle(this, new int[] { 1, 3 }, R.string.txt_product);
		initOption();
		initView();
		initSlidingMenu();
		init();

	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		return_btn = (ImageView) findViewById(R.id.btn_return);
		btn_search = (ImageView) findViewById(R.id.btn_search);
		chanpin_search_btn = (ImageView) findViewById(R.id.btn_setting);
		chanpin_search_btn.setOnClickListener(this);
		return_btn.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		title = getIntent().getStringExtra("name");
		pager = (CustomViewPager) findViewById(R.id.pager);
		imageAdapter = new ImagePagerAdapter();
		pager.setAdapter(imageAdapter);
		pager.setCurrentItem(0);
		pager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	void init() {
		service = new DBPdService(this);
		typeId = getIntent().getIntExtra("id", 0);
		// TODO 1.有网络的时候，删除数据库数据，从服务器请求数据。2.没有网络的时候，从数据库获取数据
		if (NetManager.isNetworkConnected(this)) {
			// TODO 删除数据库数据
			service.deleteAll(typeId);
			L.e(TAG, "数据库的总条数：" + service.queryPdList(typeId).size());
			// TODO从服务器请求数据
			initNet(typeId, startPos);
		} else {
			L.e(TAG, "数据库的总条数：" + service.queryPdList(typeId).size());
			mData = service.queryPdList(typeId);
			imageAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 获取右边菜单的结构(类别id和parentListId)
	 */
	void initExpandData(int typeId) {
		SoapHttpRequest request = new SoapHttpRequest(this);
		L.e(TAG, "树形菜单的类别ID==" + typeId);
		request.GetByNavigateId(SoapConstants.GETTUIJIAN, this, mHandler,
				DeviceManager.getMyUUID(this), 4, 1,
				"[Id],[App_Name],[ParentId],[ParentIDList]",
				"[App_IsShow]=1 and [ParentIDList] like '%" + typeId + "%'",
				"[Order] DESC", 0, 0, "", false, formatDateTime(), false);
	}

	/**
	 * 根据栏目ID查询产品集合带相关连接
	 * 
	 * @param navigateId
	 */
	void initNet(int typeId, int pos) {
		SoapHttpRequest request = new SoapHttpRequest(this);
		request.GetContentWidthRelevantData(
				SoapConstants.GETPRODUCT,
				this,
				mHandler,
				DeviceManager.getMyUUID(this),
				4,
				"[Id],[Name],[App_TypeName],[App_Introduce],[App_BigImageUrl],[RelatedImages]",
				"[App_IsShow]=1 and [TypeId] in (select [Id] from T_ProductTypes where [ParentIDList] like '%,"
						+ typeId + ",%')" + "or [typeId] = " + typeId,
				"[Order] DESC", pos, SoapConstants.PAGESIZE, "App_Introduce",
				false, "2014-04-16 08:55:00", false);
		L.e(TAG, "request" + request);
	}

	/**
	 * 通过类别Id进行搜索
	 * 
	 * @param typeId
	 */
	void initMenuSearch(int typeId) {
		SoapHttpRequest request = new SoapHttpRequest(this);

		request.GetByNavigateId(
				SoapConstants.GETSEARCH,
				this,
				mHandler,
				DeviceManager.getMyUUID(this),
				4,
				2,
				"[Id]",
				"[App_IsShow]=1 and [TypeId] in (select [Id] from T_ProductTypes where [ParentIDList] like '%,"
						+ typeId + ",%')", "[Order] DESC", 0, 10, "", false,
				"2014-04-10 08:55:00", false);
	}

	private void parseProResult(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser(); //
		parser.setInput(xml, "utf-8"); //
		Product pro = null;
		RelevantData rData = null;
		ArrayList<RelevantData> rDatas = null;
		newData = new ArrayList<Product>();
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:

				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Result")) {
					String mumber = parser.getAttributeValue(null, "Number");
					count = Integer.parseInt(parser.getAttributeValue(null,
							"RowsCount"));
					L.d("ChanpinActivity", "总条数：" + count);
					if (count == 0) {
						ToastManager.show(ChanpinActivity.this, "该类别下没有添加产品");
						break;
					}
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
				} else if (name.equalsIgnoreCase("Name")) {
					eventType = parser.next();
					String pName = parser.getText(); // 类别图片的相对路径
					pro.setName(pName);
					break;
				} else if (name.equalsIgnoreCase("App_TypeName")) {
					eventType = parser.next();
					String categaryName = parser.getText(); // 产品类别的介绍
					pro.setCategoryName(categaryName);
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
				} else if (name.equalsIgnoreCase("RelevantData")) {
					rDatas = new ArrayList<RelevantData>();
					break;
				} else if (name.equalsIgnoreCase("RelevantItem")) {
					rData = new RelevantData();
					break;
				} else if (name.equalsIgnoreCase("RelevantNavigateId")) {
					eventType = parser.next();
					String relevantNavigateId = parser.getText(); // 相关推荐的id
					rData.setRelevantNavigateId(Integer.parseInt(StringManager
							.replaceBlank(relevantNavigateId)));
					break;
				} else if (name.equalsIgnoreCase("RelevantDataId")) {
					eventType = parser.next();
					String relevantDataId = parser.getText(); // 相关推荐的id
					rData.setRelevantDataId(Integer.parseInt(StringManager
							.replaceBlank(relevantDataId)));
					break;
				} else if (name.equalsIgnoreCase("RelevantDataName")) {
					eventType = parser.next();
					String relevantDataName = parser.getText(); // 相关推荐的id
					rData.setRelevantDataName(relevantDataName);
					break;
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("RelevantItem")) {
					rDatas.add(rData);
					rData = null;
				} else if (parser.getName().equals("RelevantData")) {
					if (rDatas != null) {
						pro.setrData(listToString(rDatas));
					} else {
						pro.setrData("");
					}
					rDatas = null;
				} else if (parser.getName().equals("Row")) {
					newData.add(pro);
					pro = null;
				}
				break;
			}
			eventType = parser.next();
		}
		L.e(TAG, "本次获取数据的条数==" + newData.size());
		// 写入数据库
		service.addList(newData);
		mData.addAll(newData);
		newData = null;
		L.e(TAG, "写入数据库完成");
	}

	String listToString(ArrayList<RelevantData> list) {
		if (list != null) {
			String str = "";
			for (RelevantData data : list) {
				str += data.toString() + ";";
			}
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 解析按类别搜索
	 * 
	 * @param result
	 */
	void parseMenuResult(String result) throws Exception {
		InputStream xml = new ByteArrayInputStream(result.getBytes());
		XmlPullParser parser = Xml.newPullParser(); //
		parser.setInput(xml, "utf-8"); //
		List<MenuNode> nodes = null;
		MenuNode node = null;
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				nodes = new ArrayList<MenuNode>();
				break;
			case XmlPullParser.START_TAG:
				String name = parser.getName();
				if (name.equalsIgnoreCase("Result")) {
					String mumber = parser.getAttributeValue(null, "Number");
					count = Integer.parseInt(parser.getAttributeValue(null,
							"RowsCount"));
					if (mumber.equals("1")) {
						L.d("ChanpinActivity", "请求成功");
						if (count != 0) {
							// ToastManager.show(this, "解析树形目录");
						} else {
							// ToastManager.show(this, "没有树形目录");
						}
					} else {
						L.d("ChanpinActivity", "请求失败");
					}
					break;
				} else if (name.equalsIgnoreCase("Row")) {
					node = new MenuNode();
					break;
				} else if (name.equalsIgnoreCase("Id")) {
					eventType = parser.next();
					String id = parser.getText(); // 产品类别的介绍
					node.setId(id);
					break;
				} else if (name.equalsIgnoreCase("App_Name")) {
					eventType = parser.next();
					String node_name = parser.getText(); // 产品类别的介绍
					node.setName(node_name);
					break;
				} else if (name.equalsIgnoreCase("ParentId")) {
					eventType = parser.next();
					String parentId = parser.getText(); // 产品类别的介绍
					node.setParentId(parentId);
					break;
				} else if (name.equalsIgnoreCase("ParentIDList")) {
					eventType = parser.next();
					String parentIdList = parser.getText(); // 产品类别的介绍
					parentIdList = parentIdList.substring(1,
							parentIdList.length() - 1);
					node.setParentIdList(parentIdList);
					break;
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("Row")) {
					nodes.add(node);
					node = null;
				}
				break;
			}
			eventType = parser.next();
		}
		menus = parse(nodes);
	}

	List<ExpandMenu> parse(List<MenuNode> resule) {
		List<ExpandMenu> treeMenus = new ArrayList<ExpandMenu>();
		List<MenuNode> mNodes1 = new ArrayList<MenuNode>();
		List<MenuNode> mNodes2 = new ArrayList<MenuNode>();
		List<MenuNode> mNodes3 = new ArrayList<MenuNode>();
		L.e(TAG, resule.toString());

		for (MenuNode menunode : resule) {
			if (menunode.getListSize() == 1) { // 第一级
				menunode.setLevel(1);
				mNodes1.add(menunode);
			}
			if (menunode.getListSize() == 2) { // 第二级
				menunode.setLevel(2);
				mNodes2.add(menunode);
			}
			if (menunode.getListSize() == 3) { // 第三级
				menunode.setLevel(3);
				mNodes3.add(menunode);
			}
		}
		List<TreeNode> notes = new ArrayList<TreeNode>();
		for (int i = 0; i < mNodes2.size(); i++) {
			MenuNode menunode = mNodes2.get(i);
			TreeNode node = new TreeNode();
			node.typeId = menunode.getId();
			node.parent = menunode.getName();
			node.parentId = menunode.getParentId();
			List<MenuNode> childs = new ArrayList<MenuNode>();
			for (int k = 0; k < mNodes3.size(); k++) {
				if (mNodes3.get(k).getParentId() == menunode.getId()) {
					childs.add(mNodes3.get(k));
				}
			}
			node.childs = childs;
			// TODO现在已经得到所有的节点了
			notes.add(node);
			node = null;
		}

		for (int i = 0; i < mNodes1.size(); i++) {
			ExpandMenu menu = new ExpandMenu();
			menu.setParent(mNodes1.get(i).getName());
			List<TreeNode> treenotes = new ArrayList<TreeNode>();
			MenuNode menunode = mNodes1.get(i);
			for (int k = 0; k < notes.size(); k++) {
				if (notes.get(k).parentId == menunode.getId()) {
					treenotes.add(notes.get(k));
				}
			}
			menu.setNotes(treenotes);
			treeMenus.add(menu);
		}
		sizeInt = mNodes1.size();
		sizeBoolean = new boolean[sizeInt];
		isOpen = new boolean[sizeInt];
		return treeMenus;
	}

	/**
	 * 解析关键字搜索
	 * 
	 * @param result
	 */
	void parseSearchResult(String result) throws Exception {
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
					count = Integer.parseInt(parser.getAttributeValue(null,
							"RowsCount"));
					if (mumber.equals("1")) {
						L.d("ChanpinActivity", "请求成功");
						if (count != 0) {
							L.d("ChanpinActivity", "搜索到结果");
							Map<String, Object> extras = new HashMap<String, Object>();
							extras.put("result", result);
							UIManager.switcher(ChanpinActivity.this,
									SearchResultPager.class, extras);
						} else {
							ToastManager.show(this, "没有搜索结果");
						}
					} else {
						L.d("ChanpinActivity", "请求失败");
					}
					break;
				} else if (name.equalsIgnoreCase("Row")) {

					break;
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

	void initSlidingMenu() {
		setBehindContentView(R.layout.layout_search);
		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setSlidingEnabled(false);
		sm.setMode(SlidingMenu.RIGHT);

		sm.setBehindOffsetRes(DeviceManager.getScreenWidth(this) * 1 / 3);
		// sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// sm.setShadowWidth(20);
		sm.setBehindScrollScale(0);
		search_edit = (ClearEditText) findViewById(R.id.search_edit);
		search_edit.setHandler(mHandler);
	}

	// 进行分级展开
	void openExpandList() {
		for (int i = 0; i < sizeInt; i++) {
			if (menus.get(i).getNotes().size() == 0) {
				sizeBoolean[i] = false;
			} else
				sizeBoolean[i] = true;
		}
		adapter = new TreeViewAdapter(this, TreeViewAdapter.PaddingLeft >> 1);
		superAdapter = new SuperTreeViewAdapter(this, mHandler);
		expandableList = (ExpandableListView) this
				.findViewById(R.id.myExpandableListView);
		expandableList.setScrollingCacheEnabled(false);
		expandableList.setDivider(null);
		OnGroupCollapseListener onGroupCollapseListener = new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				if (menus.get(groupPosition).getNotes().size() == 0) {
					initMenuSearch(typeId);
					System.out.println("一级菜单事件触发");
				}

				isOpen[groupPosition] = false;
			}

		};

		OnGroupExpandListener onGroupExpandListener = new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				// 进行判断，若2级菜单menus.get(groupPosition).getNotes().size()存在》0，则进行展开，否则进行跳转搜索
				if (menus.get(groupPosition).getNotes().size() == 0) {
					initMenuSearch(typeId);

					System.out.println("一级菜单事件触发");
				}

				isOpen[groupPosition] = true;

			}

		};
		expandableList.setOnGroupExpandListener(onGroupExpandListener);
		expandableList.setOnGroupCollapseListener(onGroupCollapseListener);
		expandableList.setGroupIndicator(null);// 不要自带的了！！！
		adapter.RemoveAll();
		adapter.notifyDataSetChanged();
		superAdapter.RemoveAll();
		superAdapter.notifyDataSetChanged();
		List<SuperTreeViewAdapter.SuperTreeNode> superTreeNode = superAdapter
				.GetTreeNode();
		for (int i = 0; i < menus.size(); i++) {
			SuperTreeViewAdapter.SuperTreeNode superNode = new SuperTreeViewAdapter.SuperTreeNode();
			superNode.parent = menus.get(i).getParent(); // 第一层
			List<TreeNode> notes = menus.get(i).getNotes();// 第二层

			for (int k = 0; k < notes.size(); k++) {
				TreeNode tempnode = notes.get(k);
				// System.out.println("tempnode=="+tempnode);
				superNode.childs.add(tempnode);// 第三层
				tempnode = null;
			}
			superTreeNode.add(superNode);
		}
		superAdapter.UpdateTreeNode(superTreeNode);
		expandableList.setAdapter(superAdapter);
	}

	private void setSlinding() {
		if (sm.isMenuShowing()) {
			toggle();
			pager.setScanScroll(true);
		} else {
			showMenu();
			// 通过类别ID，查询出搜索的菜单
			initExpandData(typeId);

			pager.setScanScroll(false);
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private LayoutInflater inflater;

		ImagePagerAdapter() {
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return mData.size();
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
			String releStr = mData.get(position).getrData();
			// 获取相关推荐
			if (!releStr.equals("") && releStr != null) {
				TextView tv_tuijian = (TextView) imageLayout
						.findViewById(R.id.tv_tuijian);
				String text = "<font color='red'><em>相关链接推荐</em></font>";
				tv_tuijian.setText(Html.fromHtml(text));

				ArrayList<RelevantData> reledatas = new ArrayList<RelevantData>();
				RelevantData relevantdata = null;

				L.e(TAG, "相关连接的字段==" + releStr);
				String[] reles = releStr.split(";");
				for (int i = 0; i < reles.length; i++) {
					relevantdata = new RelevantData();
					String[] reledata = reles[i].split(",");
					relevantdata.setRelevantDataId(Integer
							.parseInt(reledata[0]));
					relevantdata.setRelevantDataName(reledata[1]);
					reledatas.add(relevantdata);
				}
				final List<Map<String, String>> objects = new ArrayList<Map<String, String>>();
				if (reledatas.size() != 0) {

					for (RelevantData data : reledatas) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("id", data.getRelevantDataId() + "");
						map.put("name", data.getRelevantDataName());
						objects.add(map);
					}
				}

				ListViewForScrollView reledata_list = (ListViewForScrollView) imageLayout
						.findViewById(R.id.reledata_list);

				SimpleAdapter adapter = new SimpleAdapter(ChanpinActivity.this,
						objects, R.layout.list_reledata,
						new String[] { "name" }, new int[] { R.id.tv_name });
				reledata_list.setAdapter(adapter);
				reledata_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Map<String, String> relevantdata = (Map<String, String>) objects
								.get(position);
						Intent intent = new Intent(ChanpinActivity.this,
								NewProductsPager.class);
						intent.putExtra("typeId", relevantdata.get("id"));
						startActivity(intent);
					}
				});
			}
			TextView tv_intro = (TextView) imageLayout
					.findViewById(R.id.tv_introduce);

			if (mData.get(position).getDesc() == null
					|| mData.get(position).getDesc().equals("")) {
				tv_intro.setText("该商品未添加详细说明");
			} else {
				tv_intro.setText(Html.fromHtml(mData.get(position).getDesc()));
				System.out.println(mData.get(position).getDesc());
			}

			TextView tv_bianhao = (TextView) imageLayout
					.findViewById(R.id.tv_bianhao);
			tv_bianhao.setText("编号：" + mData.get(position).getProductId());

			TextView tv_title = (TextView) imageLayout
					.findViewById(R.id.tv_title);
			tv_title.setText(mData.get(position).getName());

			TextView tv_category = (TextView) imageLayout
					.findViewById(R.id.tv_category);

			tv_category.setText(mData.get(position).getCategoryName());

			Bitmap title = BitmapFactory.decodeResource(getResources(),
					R.drawable.top_bg);

			final int ihigh = high
					- DensityUtil.dip2px(getApplicationContext(), 112)
					- title.getHeight()
					- DensityUtil.dip2px(getApplicationContext(), 25);

			L.e(TAG, "图片的高度==" + ihigh);
			imageView.setLayoutParams(new RelativeLayout.LayoutParams(width,
					ihigh));

			ImageButton tv_xiangce = (ImageButton) imageLayout
					.findViewById(R.id.xiangce);

			tv_xiangce.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(ChanpinActivity.this,
							GalleryActivity.class);
					int productId = (Integer) mData.get(position)
							.getProductId();
					intent.putExtra("id", productId);
					String rImage = mData.get(position).getRelatedImages();
					intent.putExtra("rImage", rImage);
					intent.putExtra("pName", mData.get(position).getName());
					startActivity(intent);
				}
			});

			ImageView btn_share = (ImageView) imageLayout
					.findViewById(R.id.btn_share);
			btn_share.setOnClickListener(new OnClickListener() {
				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					navigateId = Integer.parseInt(PreferenceManager.getString(
							ChanpinActivity.this, "_id", "3"));
					String Cpxq = mData.get(position).getDesc().toString();

					if (mData.get(position).getDesc() == null
							|| mData.get(position).getDesc().equals("")) {
						Cpxq = StringManager.htmlRemoveTag(mData.get(position)
								.getDesc());
					}
					ShareManager shareManager = new ShareManager(new UMImage(
							ChanpinActivity.this, mData.get(position)
									.getImgUrl().toString()), mData
							.get(position).getName().toString(), Cpxq,
							ConfigManager.getSiteDomain(ChanpinActivity.this)
									+ "/share.aspx?nid=" + navigateId + "id="
									+ typeId);
					L.e(TAG, ConfigManager.getSiteDomain(ChanpinActivity.this)
							+ "/share.aspx?nid=" + navigateId + "id=" + typeId);
					shareManager.share(ChanpinActivity.this);
					UMServiceFactory.getUMSocialService("分享测试！！",
							RequestType.SOCIAL).registerListener(
							new OnSensorListener() {
								@Override
								public void onStart() {
									Toast.makeText(ChanpinActivity.this,
											getString(R.string.share_start),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onComplete(SHARE_MEDIA platform,
										int eCode, SocializeEntity entity) {
									Toast.makeText(ChanpinActivity.this,
											getString(R.string.share_over),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onActionComplete(SensorEvent event) {

									Toast.makeText(ChanpinActivity.this,
											getString(R.string.share_doing),
											Toast.LENGTH_SHORT).show();
								}

								@Override
								public void onButtonClick(WhitchButton button) {
									if (button == WhitchButton.BUTTON_CANCEL) {
										Toast.makeText(
												ChanpinActivity.this,
												getString(R.string.share_cancel),
												Toast.LENGTH_SHORT).show();
									} else {

									}
								}
							});
				}
			});
			L.e(TAG, "产品大图的路径" + mData.get(position).getImgUrl());
			imageLoader.displayImage(mData.get(position).getImgUrl(),
					imageView, options, animateFirstListener);

			view.addView(imageLayout, 0);
			return imageLayout;
		}

		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_search:
			setSlinding();
			break;

		case R.id.btn_return:

			toActivity(this, ProductActivity.class);
			break;
		default:
			break;
		}
	}

	private void toActivity(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.in_form_left_back,
				R.anim.out_of_right_back);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (sm.isMenuShowing()) {
				toggle();
				pager.setScanScroll(true);
				return true;
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private String formatDateTime() {
		String timestamp = StringManager.getTime(System.currentTimeMillis());
		return timestamp;
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

			if (arg0 == 0) {
				if (arg2 == 0) {
					L.e("CustomViewPager", "已经是第一页了");
				}
			} else if (arg0 == mData.size() - 1) {
				if (arg2 == 0) {
					startPos = mData.size();
					if (count > startPos) {
						initNet(typeId, startPos);
					} else {
						L.e("CustomViewPager", "已经是最后一页了");
					}
				}
			}
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			tv_title.setText(mData.get(arg0).getName());
		}

	}

	/**
	 * 通过类别Id进行搜索
	 * 
	 * @param typeId
	 */
	void initMenuSearch(int typeId, Handler mHandler) {
		SoapHttpRequest request = new SoapHttpRequest(this);

		request.GetByNavigateId(
				SoapConstants.GETSEARCH,
				this,
				mHandler,
				DeviceManager.getMyUUID(this),
				4,
				2,
				"[Id],[App_TypeName],[Name],[App_Introduce],[App_BigImageUrl],[RelatedImages]",
				"[App_IsShow]=1 and [TypeId] in (select [Id] from T_ProductTypes where [ParentIDList] like '%,"
						+ typeId + ",%')" + "or [TypeId] = " + typeId,
				"[Order] DESC", 0, 10, "App_Introduce", false,
				"2014-04-10 08:55:00", false);
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SoapConstants.FAIL_RESULT:
			case SoapConstants.NO_NET:
				ToastManager.show(ChanpinActivity.this, "网络不给力，请售后重试");
				break;
			case SoapConstants.FAIL_XML_RESULT:
				ToastManager.show(ChanpinActivity.this, "字段解析出错");
				break;
			case SoapConstants.TIMEOUT_RESULT:
				ToastManager.show(ChanpinActivity.this, "请求超时");
				break;
			case SoapConstants.GETPRODUCT:
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "产品集合带相关连接==" + result);
					try {
						parseProResult(result);
						imageAdapter.notifyDataSetChanged();
						L.e(TAG, "当前页码==" + startPos);
						pager.setCurrentItem(startPos - 1);
						tv_title.setText(mData.get(startPos).getName());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;

			case SoapConstants.GETTUIJIAN:
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "树形菜单==" + result);
					try {
						parseMenuResult(result);
						openExpandList();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case SoapConstants.GETSEARCH:
				if (msg.obj != null) {
					String result = msg.obj.toString();
					L.e(TAG, "搜索结果==" + result);
					try {
						parseSearchResult(result);

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
