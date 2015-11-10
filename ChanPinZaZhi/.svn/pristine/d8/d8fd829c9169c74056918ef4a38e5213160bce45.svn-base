package com.chanpinzazhi.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库辅助类
 * 
 * @author Administrator
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String name = "db_eims"; // 数据库名称
	private static final int version = 4; // 数据库版本

	public DBHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建一张类别表，字段：_id,categoryId,name,desc,imgUrl,serverTime
		db.execSQL("CREATE TABLE IF NOT EXISTS t_category (_id integer primary key autoincrement,categoryId integer,name varchar(20),desc varchar(20),imgUrl varchar(20),serverTime varchar(20))");
		// 创建产品表
		db.execSQL("CREATE TABLE IF NOT EXISTS t_product (_id integer primary key autoincrement,productId integer,categoryId integer,number varchar(20),cname varchar(20),name varchar(20),desc varchar(20),imgUrl varchar(20),relatedImages varchar(20),rData varchar(20))");
		// 创建图片表
		db.execSQL("CREATE TABLE IF NOT EXISTS t_album (_id integer primary key autoincrement,productId integer,imgUrl varchar(20),imgDesc varchar(20))");
		Log.e("DB", "创建数据库");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// upgradeTables(db, "t_category");
		onCreate(db);
	}

	/**
	 * Upgrade tables. In this method, the sequence is: <b>
	 * <p>
	 * [1] Rename the specified table as a temporary table.
	 * <p>
	 * [2] Create a new table which name is the specified name.
	 * <p>
	 * [3] Insert data into the new created table, data from the temporary
	 * table.
	 * <p>
	 * [4] Drop the temporary table. </b>
	 * 
	 * @param db
	 *            The database.
	 * @param tableName
	 *            The table name.
	 * @param columns
	 *            The columns range, format is "ColA, ColB, ColC, ... ColN";
	 */
	protected void upgradeTables(SQLiteDatabase db, String tableName) {
		try {
			db.beginTransaction();

			// 1, Rename table.
			String tempTableName = tableName + "_temp";
			String sql = "ALTER TABLE " + tableName + " RENAME TO "
					+ tempTableName;
			db.execSQL(sql, null);

			// 2, Create table.增加auther字段
			db.execSQL("CREATE TABLE"
					+ tableName
					+ "(_id integer primary key autoincrement,categoryId integer,name varchar(20),desc varchar(20),imgUrl varchar(20),serverTime varchar(20),auther varchar(20))");

			// 3, Load data
			sql = "INSERT INTO " + tableName + " SELECT * FROM "
					+ tempTableName;

			db.execSQL(sql, null);

			// 4, Drop the temporary table.
			db.execSQL("DROP TABLE IF EXISTS " + tempTableName, null);

			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}
}
