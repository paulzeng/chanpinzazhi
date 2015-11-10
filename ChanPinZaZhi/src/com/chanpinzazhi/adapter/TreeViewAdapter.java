package com.chanpinzazhi.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.chanpinzazhi.R;
import com.chanpinzazhi.entity.MenuNode;
import com.chanpinzazhi.manager.ToastManager;

/**
 * 
 * 二级菜单列表显示
 * 
 * */
public class TreeViewAdapter extends BaseExpandableListAdapter {
	public static int ItemHeight;// 每项的高度
	public static int PaddingLeft = 0;// 每项的高度

	private int myPaddingLeft = 0;// 如果是由SuperTreeView调用，则作为子项需要往右移

	static public class TreeNode {
		public List<Integer> threeTypeId = new ArrayList<Integer>();
		public String typeId;
		public String parentId;
		public Object parent;
		public List<MenuNode> childs = new ArrayList<MenuNode>();

		@Override
		public String toString() {
			return "TreeNode [typeId=" + typeId + ", parentId=" + parentId
					+ ", parent=" + parent + ", threeTypeId=" + threeTypeId
					+ ",childs=" + childs + "]";
		}
	}

	List<TreeNode> treeNodes = new ArrayList<TreeNode>();
	Context parentContext;

	public TreeViewAdapter(Context view, int myPaddingLeft) {
		parentContext = view;
		this.myPaddingLeft = myPaddingLeft;
	}

	public List<TreeNode> GetTreeNode() {
		return treeNodes;
	}

	public void UpdateTreeNode(List<TreeNode> nodes) {
		treeNodes = nodes;
	}

	public void RemoveAll() {
		treeNodes.clear();
	}

	public Object getChild(int groupPosition, int childPosition) {
		return treeNodes.get(groupPosition).childs.get(childPosition);
	}

	public int getChildrenCount(int groupPosition) {
		return treeNodes.get(groupPosition).childs.size();
	}

	static public TextView getTextView(Context context) {
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, ItemHeight);

		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		return textView;
	}

	// 三级菜单
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView childTextView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parentContext).inflate(
					R.layout.childs, null);
		}

		childTextView = (TextView) convertView.findViewById(R.id.tv_childs); 
		ItemHeight = childTextView.getHeight();
		MenuNode mMenuNode = (MenuNode) getChild(groupPosition, childPosition);
		childTextView.setText(mMenuNode.getName());

		return convertView;
	}

	// 二级菜单
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		TextView groupTextView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parentContext).inflate(
					R.layout.child_groups, null);
		}
		groupTextView = (TextView) convertView
				.findViewById(R.id.tv_child_groups);
		groupTextView.setText(getGroup(groupPosition).toString());

		// 为了处理图标被拉伸的问题！这里采用偷懒的办法让textView傍边产生一个图标，如果GroupView只是一个TextView的话，推荐这样做！
		if (SuperTreeViewAdapter.twoSizeBoolean[groupPosition][groupPosition]) {
			if (SuperTreeViewAdapter.towIsOpen[groupPosition][groupPosition]) {
				Drawable leftDrawable = parentContext.getResources()
						.getDrawable(R.drawable.open_child);
				leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
						leftDrawable.getMinimumHeight());
				groupTextView.setCompoundDrawables(leftDrawable, null, null,
						null);
			} else if (!SuperTreeViewAdapter.towIsOpen[groupPosition][groupPosition]) {
				Drawable leftDrawable = parentContext.getResources()
						.getDrawable(R.drawable.close_child);
				leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
						leftDrawable.getMinimumHeight());
				groupTextView.setCompoundDrawables(leftDrawable, null, null,
						null);
			}
		} else {
			Drawable leftDrawable = parentContext.getResources()
					.getDrawable(R.drawable.open_child);
			leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(),
					leftDrawable.getMinimumHeight());
			groupTextView.setCompoundDrawables(leftDrawable, null, null,
					null);
		}
		return convertView;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public Object getGroup(int groupPosition) {
		return treeNodes.get(groupPosition).parent;
	}

	public int getGroupCount() {
		return treeNodes.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}
