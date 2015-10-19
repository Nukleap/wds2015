package com.ida.wds2015.database;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.utils.DateUtils;
import com.ida.wds2015.Globals;
import com.ida.wds2015.classes.FavoriteListItem;
import com.ida.wds2015.classes.TransactionRoot;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static DatabaseHelper mInstance=null;
	public static final String DATABASE_NAME="com.ida.wds2015.database";
	public static final String TABLE_EXHIBITOR="table_exhibitor";
	public static final String TABLE_CATEGORY="table_category";
	public static final String TABLE_PROGRAM="table_program";
	public static final String TABLE_USER_PROFILE="table_user_profile";
	public static final String TABLE_SPEAKER="table_speaker";
	public static final String TABLE_OUR_TRANSACTION="table_our_transaction";
	public static final String TABLE_POSTER="table_poster";
	public static final String TABLE_NEWS="table_news";
	public static final String TABLE_SCAN="table_scan";
	public static final String TABLE_FEEDBACK="table_feedback";
	public static final String ID="id";
	public static final String JSONDATA="jsondata";
	public static final String DELETED="deleted";
	public static final String SAVEDTIME="savetime";
	
	
	private Context context;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	public static synchronized DatabaseHelper getInstance(Context context){
		if(mInstance==null){
			mInstance = new DatabaseHelper(context);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		String query="CREATE TABLE IF NOT EXISTS "+TABLE_EXHIBITOR+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		db.execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_CATEGORY+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		db.execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_PROGRAM+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		db.execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_SPEAKER+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		db.execSQL(query);
		
	}

	public void checkUpdate(){
		String query="CREATE TABLE IF NOT EXISTS "+TABLE_POSTER+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
		 query="CREATE TABLE IF NOT EXISTS "+TABLE_USER_PROFILE+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_OUR_TRANSACTION+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_NEWS+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_SCAN+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
		query="CREATE TABLE IF NOT EXISTS "+TABLE_FEEDBACK+
				"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +JSONDATA+ " TEXT,"+DELETED+
				" INTEGER,"+SAVEDTIME+" TEXT);";
		this.getWritableDatabase().execSQL(query);
		
	}
	
	public long insertData(final String tableName,DatabaseRow row)
	{
		SQLiteDatabase db =getWritableDatabase(); 
		ContentValues c=new ContentValues();
		c.put(ID, 1);
		c.put(JSONDATA, row.getJsondata());
		c.put(DELETED, 0);
		c.put(SAVEDTIME,DateUtils.getSqliteTime());
		long index = db.insertWithOnConflict(tableName, null, c, SQLiteDatabase.CONFLICT_REPLACE);
		Log.i(""+this.getClass().getName(), ""+index);
		return index;
	}
	
	public long insertTransaction(DatabaseRow row){
		SQLiteDatabase db = getWritableDatabase(); 
		ContentValues c=new ContentValues();
		c.put(JSONDATA, row.getJsondata());
		c.put(DELETED, 0);
		c.put(SAVEDTIME,DateUtils.getSqliteTime());
		long index = db.insert(TABLE_OUR_TRANSACTION,null,c);
		Log.i(""+this.getClass().getName(), ""+index);
		return index;
	}
	
	public DatabaseRow getData(final String tableName){
		DatabaseRow row = null;
		SQLiteDatabase db = getReadableDatabase();
		String query = "SELECT * FROM "+tableName+" WHERE "+DELETED+" =0";
		Cursor c = db.rawQuery(query, null);
		if(c.moveToFirst()){
			do{
				if(row==null){
					row = new DatabaseRow();
					row.setId(c.getLong(0));
					row.setJsondata(c.getString(1));
					row.setDeleted(0);
					row.setSavedat(c.getString(3));
				}
			}while (c.moveToNext());
		}
		if(row!=null){
			Log.i(""+this.getClass().getName(), "Getting Data..."+row.getId());
		}else{
			Log.i(""+this.getClass().getName(), "Getting Data...null");
		}
		return row;
	}
	
	public ArrayList<TransactionRoot> getTransactionList(){
		DatabaseRow row = null;
		SQLiteDatabase db = getReadableDatabase();
		String query = "SELECT * FROM "+TABLE_OUR_TRANSACTION+" WHERE "+DELETED+" =0";
		Cursor c = db.rawQuery(query, null);
		Type type = new TypeToken<TransactionRoot>(){}.getType();
		ArrayList<TransactionRoot> list = new ArrayList<TransactionRoot>();
		if(c.moveToFirst()){
			do{
				if(row==null){
					String jsondata = c.getString(1);
					TransactionRoot root = new Gson().fromJson(jsondata, type);
					list.add(root);
				}
			}while (c.moveToNext());
		}
		return list;
	}
	public void deleteFeedbackData(){
		Log.i("DELETE", "inside delete method....");
		SQLiteDatabase db = this.getWritableDatabase();
		String que ="DELETE FROM "+TABLE_FEEDBACK;
		db.execSQL(que);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
}
