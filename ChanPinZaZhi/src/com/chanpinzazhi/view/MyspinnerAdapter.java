package com.chanpinzazhi.view;import java.util.ArrayList;import java.util.List;import java.util.Map;import android.content.Context;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.RelativeLayout;import android.widget.TextView;import com.chanpinzazhi.R; public class MyspinnerAdapter extends BaseAdapter {	LayoutInflater inflater;	Context context;	ArrayList<Map<String,Object>> list;	public MyspinnerAdapter(Context context, ArrayList<Map<String,Object>> list) {		super();		this.context = context;		this.list = list;		inflater = LayoutInflater.from(context);	}	@Override	public int getCount() {		// TODO Auto-generated method stub		return list.size();	}	@Override	public Object getItem(int position) {		// TODO Auto-generated method stub		return list.get(position).get("name").toString();	}	@Override	public long getItemId(int position) {		// TODO Auto-generated method stub		return position;	}	@Override	public View getView(int position, View convertView, ViewGroup parent) {		ViewHolder viewHolder = null;		if (convertView == null) {			convertView = inflater.inflate(R.layout.myspinner_dropdown_items, null);			viewHolder = new ViewHolder();			viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.myspinner_dropdown_layout);			viewHolder.textView = (TextView) convertView.findViewById(R.id.myspinner_dropdown_txt);			convertView.setTag(viewHolder);		} else {			viewHolder = (ViewHolder) convertView.getTag();		}		if(list.size() == position+1){			viewHolder.layout.setBackgroundResource(R.drawable.preference_last_item);		}else{			viewHolder.layout.setBackgroundResource(R.drawable.preference_item);		}		viewHolder.textView.setText(list.get(position).get("name").toString());		return convertView;	}	public class ViewHolder {		RelativeLayout layout;		TextView textView;	}	public void refresh(List<Map<String,Object>> l) {		this.list.clear();		list.addAll(l);		notifyDataSetChanged();	}	public void add(Map<String,Object> str) {		list.add(str);		notifyDataSetChanged();	}	public void add(ArrayList<Map<String,Object>> str) {		list.addAll(str);		notifyDataSetChanged();	}}