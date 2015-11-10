package com.chanpinzazhi.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chanpinzazhi.entity.Product;

/**
 * 数据库业务处理类
 * 
 * @author Administrator
 * 
 */
public class DBPdService {
	private DBHelper helper;

	public DBPdService(Context context) {
		this.helper = new DBHelper(context);
	}

	/**
	 * 增加一条产品类型的信息
	 * 
	 * @param site
	 */
	public void add(Product product) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into t_product (productId,categoryId,number,cname,name,desc,imgUrl,serverTime) values (?,?,?,?,?,?,?)",
				new Object[] { product.getProductId(), product.getCategoryId(),
						product.getNumber(), product.getCategoryName(),product.getName(),
						product.getDesc(), product.getImgUrl() });
		db.close();
	}

	/**
	 * 增加一个列表的产品类型信息
	 * 
	 * @param photo
	 */
	public void addList(List<Product> productList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (Product product : productList) {
			db.execSQL(
					"insert into t_product (productId,categoryId,number,cname,name,desc,imgUrl,relatedImages,rData) values (?,?,?,?,?,?,?,?,?)",
					new Object[] { product.getProductId(),
							product.getCategoryId(), product.getNumber(),product.getCategoryName(),
							product.getName(), product.getDesc(),
							product.getImgUrl(), product.getRelatedImages(),
							product.getrData() });
		}
		db.close();
	}

	/**
	 * 查询所有的产品类型信息,以集合形式保存
	 * 
	 * @return
	 */
	public ArrayList<Product> queryPdList(int categoryId) {
		ArrayList<Product> productList = new ArrayList<Product>();
		SQLiteDatabase db = helper.getWritableDatabase();
		// select * from t_category order by serverTime desc
		Cursor cursor = db.rawQuery(
				"select * from t_product where categoryId = " + categoryId,
				null);
		while (cursor.moveToNext()) {
			int productId = cursor.getInt(cursor.getColumnIndex("productId"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String cname = cursor.getString(cursor.getColumnIndex("cname"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String desc = cursor.getString(cursor.getColumnIndex("desc"));
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String relatedImages = cursor.getString(cursor
					.getColumnIndex("relatedImages"));
			String rData = cursor.getString(cursor
					.getColumnIndex("rData"));
			Product product = new Product(productId, categoryId, number,cname, name,
					desc, imgUrl, relatedImages, rData);
			productList.add(product);
		}
		db.close();
		return productList;
	}

	/**
	 * 查询所有产品类型的信息,以Map形式保存
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryPdMap(int categoryId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from t_product where categoryId = " + categoryId,
				null);
		while (cursor.moveToNext()) {
			int productId = cursor.getInt(cursor.getColumnIndex("productId"));
			String number = cursor.getString(cursor.getColumnIndex("number"));
			String cname = cursor.getString(cursor.getColumnIndex("cname"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String desc = cursor.getString(cursor.getColumnIndex("desc"));
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String serverTime = cursor.getString(cursor
					.getColumnIndex("serverTime"));
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			map.put("categoryId", categoryId);
			map.put("number", number);
			map.put("cname", cname);
			map.put("name", name);
			map.put("desc", desc);
			map.put("imgUrl", imgUrl);
			map.put("serverTime", serverTime);
			data.add(map);
		}
		db.close();
		return data;
	}

	/**
	 * 删除表中的数据
	 */
	public void deleteAll(int typeId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from t_product where categoryId = "+typeId); 
		db.close();
	}
}