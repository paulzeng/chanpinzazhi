package com.chanpinzazhi.entity;

public class Product {
	private int productId;
	private int categoryId;
	private String number;
	private String categoryName;
	private String name;
	private String desc;
	private String imgUrl; 
	private String relatedImages;
	private String rData;

	public Product() {
	}

	public Product(int productId, int categoryId, String number,String categoryName, String name,
			String desc, String imgUrl, String relatedImages,String rData) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.number = number;
		this.categoryName = categoryName;
		this.name = name;
		this.desc = desc;
		this.imgUrl = imgUrl; 
		this.relatedImages = relatedImages;
		this.rData = rData;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	
	public String getRelatedImages() {
		return relatedImages;
	}

	public void setRelatedImages(String relatedImages) {
		this.relatedImages = relatedImages;
	}

	public String getrData() {
		return rData;
	}

	public void setrData(String rData) {
		this.rData = rData;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return productId+"|"+categoryId+"|"+number+"|"+name+"|"+
				desc+"|"+imgUrl+"|"+relatedImages+"|"+"相关链接=="+rData;
	}
	
	public String getAllRdata() {
		// TODO Auto-generated method stub
		return "相关链接=="+rData;
	}
}
