package com.chanpinzazhi.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chanpinzazhi.entity.Category;
/**
 * 数据库业务处理类
 * @author Administrator
 *
 */
public class DBCategoryService {
	private DBHelper helper;

	public DBCategoryService(Context context) {
		this.helper = new DBHelper(context);
	}

	/**
	 * 增加一条产品类型的信息
	 * 
	 * @param site
	 */
	public void add(Category category) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into t_category (categoryId,name,desc,imgUrl,serverTime) values (?,?,?,?,?)",
				new Object[] { category.getmCategoryId(), category.getmName(),
						category.getmDesc(), category.getmImgUrl(),
						category.getmServerTime() });
		db.close();
	}

	/**
	 * 增加一个列表的产品类型信息
	 * 
	 * @param photo
	 */
	public void addList(List<Category> categorysList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (Category category : categorysList) {
			db.execSQL(
					"insert into t_category (categoryId,name,desc,imgUrl,serverTime) values (?,?,?,?,?)",
					new Object[] { category.getmCategoryId(), category.getmName(),
							category.getmDesc(), category.getmImgUrl(),
							category.getmServerTime() });
		}
		db.close();
	}

	/**
	 * 查询所有的产品类型信息,以集合形式保存
	 * 
	 * @return
	 */
	public ArrayList<Category> queryCategoryList() {
		ArrayList<Category> newsList = new ArrayList<Category>();
		SQLiteDatabase db = helper.getWritableDatabase();
		//select * from t_category order by serverTime desc
		Cursor cursor = db.rawQuery(
				"select * from t_category", null);
		while (cursor.moveToNext()) { 
			int categoryId = cursor.getInt(cursor.getColumnIndex("categoryId"));
			String name = cursor.getString(cursor
					.getColumnIndex("name"));
			String desc = cursor.getString(cursor
					.getColumnIndex("desc"));
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String serverTime = cursor.getString(cursor
					.getColumnIndex("serverTime")); 
			Category category = new Category(categoryId, name, desc, imgUrl,
					serverTime); 
			newsList.add(category);
		}
		db.close();
		return newsList;
	}
	
	/**
	 * 查询所有的产品类型信息,以集合形式保存
	 * 
	 * @return
	 */
	public ArrayList<Category> queryCategoryList(int start,int end) {
		ArrayList<Category> newsList = new ArrayList<Category>();
		SQLiteDatabase db = helper.getWritableDatabase();
		//select * from t_category order by serverTime desc
		Cursor cursor = db.rawQuery(
				"select * from t_category limit "+start+","+end, null);
		while (cursor.moveToNext()) { 
			int categoryId = cursor.getInt(cursor.getColumnIndex("categoryId"));
			String name = cursor.getString(cursor
					.getColumnIndex("name"));
			String desc = cursor.getString(cursor
					.getColumnIndex("desc"));
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String serverTime = cursor.getString(cursor
					.getColumnIndex("serverTime")); 
			Category category = new Category(categoryId, name, desc, imgUrl,
					serverTime); 
			newsList.add(category);
		}
		db.close();
		return newsList;
	}

	/**
	 * 查询所有产品类型的信息,以Map形式保存
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryCategoryMap() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from t_category ", null);
		while (cursor.moveToNext()) {
			int categoryId = cursor.getInt(cursor.getColumnIndex("categoryId"));
			String name = cursor.getString(cursor
					.getColumnIndex("name"));
			String desc = cursor.getString(cursor
					.getColumnIndex("desc"));
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String serverTime = cursor.getString(cursor
					.getColumnIndex("serverTime")); 
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("categoryId", categoryId);
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
	 * 根据id删除
	 * 
	 * @return
	 */
	public void deleteAll() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete("t_category", null, null);
		db.close();
	}
}