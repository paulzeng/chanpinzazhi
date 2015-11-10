package com.chanpinzazhi.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chanpinzazhi.entity.Photo;

/**
 * 数据库业务处理类
 * 
 * @author Administrator
 * 
 */
public class DBPhotoService {
	private DBHelper helper;

	public DBPhotoService(Context context) {
		this.helper = new DBHelper(context);
	}

	/**
	 * 增加一条产品图片的信息
	 * 
	 * @param site
	 */
	public void add(Photo photo) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(
				"insert into t_album (productId,imgUrl,imgDesc) values (?,?,?)",
				new Object[] { photo.getProductId(), photo.getImgUrl(),
						photo.getImgDesc() });
		db.close();
	}

	/**
	 * 增加一个列表的产品图片信息
	 * 
	 * @param photo
	 */
	public void addList(List<Photo> photoList) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (Photo photo : photoList) {
			db.execSQL(
					"insert into t_album (productId,imgUrl,imgDesc) values (?,?,?)",
					new Object[] { photo.getProductId(), photo.getImgUrl(),
							photo.getImgDesc()});
		}
		db.close();
	}

	/**
	 * 查询所有的产品图片的信息,以集合形式保存
	 * 
	 * @return
	 */
	public List<Photo> queryPhotoList(int productId) {
		List<Photo> photoList = new ArrayList<Photo>();
		SQLiteDatabase db = helper.getWritableDatabase();
		// select * from t_category order by serverTime desc
		Cursor cursor = db.rawQuery("select * from t_album where productId = "+productId, null);
		while (cursor.moveToNext()) { 
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String imgDesc = cursor.getString(cursor.getColumnIndex("imgDesc")); 
			Photo photo = new Photo(productId, imgUrl, imgDesc);
			photoList.add(photo);
		}
		db.close();
		return photoList;
	}

	/**
	 * 查询所有产品图片的信息,以Map形式保存
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryCategoryMap(int productId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from t_album where productId = "+productId, null);
		while (cursor.moveToNext()) { 
			String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
			String imgDesc = cursor.getString(cursor.getColumnIndex("imgDesc")); 
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("productId", productId);
			map.put("imgUrl", imgUrl);
			map.put("imgDesc", imgDesc); 
			data.add(map);
		}
		db.close();
		return data;
	}
}