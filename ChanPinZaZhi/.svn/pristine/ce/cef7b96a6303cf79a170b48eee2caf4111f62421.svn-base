package com.chanpinzazhi.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanpinzazhi.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CategoryAdapter extends BaseAdapter {

	List<Map<String, Object>> data;
	Context c;
	private LayoutInflater inflater;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	public CategoryAdapter(Context context, List<Map<String, Object>> data) {
		inflater = LayoutInflater.from(context);
		this.c = context;
		this.data = data;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			/* 绑定相应的视图 */
			convertView = inflater.inflate(R.layout.item_chanpinleibie, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.info = (TextView) convertView.findViewById(R.id.info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// TODO 异步加载图片
		
		return convertView;
	}

	/**
	 * 自定义类 绑定视图
	 * 
	 * @author zwt
	 */
	final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView info;
	}

	

}