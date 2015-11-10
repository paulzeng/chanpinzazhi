package com.chanpinzazhi.entity;
 
import java.util.List;

import com.chanpinzazhi.adapter.TreeViewAdapter.TreeNode;

public class ExpandMenu {
	private int typeId;
	private String parent;
	private List<TreeNode> notes;

	public ExpandMenu() {
	}

	public ExpandMenu(String parent, List<TreeNode> notes) {
		this.parent = parent;
		this.notes = notes;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "ExpandMenu [typeId=" + typeId + ",parent=" + parent + ", notes=" + notes + "]";
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<TreeNode> getNotes() {
		return notes;
	}

	public void setNotes(List<TreeNode> notes) {
		this.notes = notes;
	}

	
}
