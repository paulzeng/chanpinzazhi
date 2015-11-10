package com.chanpinzazhi.entity;

public class MenuNode {
	private int level;
	private String id;
	private String name;
	private String parentId;
	private String parentIdList;

	public MenuNode() {
	}

	public MenuNode(int level,String id, String name, String parentId, String parentIdList) {
		this.level = level;
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.parentIdList = parentIdList;
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIdList() {
		return parentIdList;
	}

	public void setParentIdList(String parentIdList) {
		this.parentIdList = parentIdList;
	}
	public int getListSize(){
		String[] str = parentIdList.split(",");
		return str.length;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return level+"|"+id+"|"+name+"|"+parentId+"|"+parentIdList+"长度="+getListSize();
	}
}
