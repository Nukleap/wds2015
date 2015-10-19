package com.ida.wds2015.scan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.ida.wds2015.Globals;
import com.ida.wds2015.classes.BarcodePost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BarcodeDatabaseHelper extends SQLiteOpenHelper {

	private static BarcodeDatabaseHelper mInstance = null;
	public static final String DATABASE_NAME = "db_name";
	public static final String TABLE_BARCODE = "bar_code";
	public static final String SCANE_RESULT = "scane_result";
	public static final String RESULT_TIME = "result_time";
	public static final String RESULT_SYNC = "result_sync";
	public static final String ID = "id";
	String date_time;
	int search_count;
	private Context context;

	public BarcodeDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	public static synchronized BarcodeDatabaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new BarcodeDatabaseHelper(context);
		}
		return mInstance;
	}

	public void onCreate(SQLiteDatabase db) {
		String query = " CREATE TABLE IF NOT EXISTS " + TABLE_BARCODE + "("
				+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SCANE_RESULT
				+ " TEXT, " + RESULT_TIME + " TEXT, " + RESULT_SYNC
				+ " INTEGER);";
		db.execSQL(query);
	}

	public String getSavedTime() {
		return date_time;
	}

	public long saveScaneResult(String str) {
		date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault()).format(Calendar.getInstance().getTime());
		SQLiteDatabase database = this.getReadableDatabase();
		ContentValues c = new ContentValues();
		c.put(SCANE_RESULT, str);
		c.put(RESULT_TIME, date_time);
		c.put(RESULT_SYNC, 0);
		long index = database.insert(TABLE_BARCODE, null, c);
		return index;
	}

	public long showScaneResult() {
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor c = database.rawQuery(" SELECT * FROM " + TABLE_BARCODE + ";",
				null);
		long index = c.getCount();
		Log.i("Database status", "total rows are." + index);
		if (c.moveToFirst()) {
			do {
				String s = c.getString(2);
				Log.i("Database Time", " " + s);
			} while (c.moveToNext());
		}
		return index;
	}

	public ArrayList<Barcode> getScaneDetails1() {
		SQLiteDatabase database = this.getReadableDatabase();
		ArrayList<Barcode> array = new ArrayList<Barcode>();
		Cursor c = database.rawQuery(" SELECT * FROM " + TABLE_BARCODE
				+ " GROUP BY " + SCANE_RESULT + ";", null);
		int i = c.getCount();
		Log.i("count", "list count is---" + i);
		Barcode bar = null;
		if (c.moveToFirst()) {
			do {
				bar = new Barcode();
				bar.setId(c.getLong(0));
				// Log.i(""+this.getClass().getName(), "ID: "+c.getLong(0));
				bar.setBarcode(c.getString(1));
				// bar.setDatetime(c.getString(2));
				bar.setContent(getScaneDetails(bar.getBarcode()));
				array.add(bar);
			} while (c.moveToNext());
		}
		return array;
	}
	
	public ArrayList<BarcodePost> getSyncNeedBarcodes(String id,String byname) {
		SQLiteDatabase database = this.getReadableDatabase();
		ArrayList<BarcodePost> blist = new ArrayList<BarcodePost>();
		Cursor c = database.rawQuery("SELECT * FROM " + TABLE_BARCODE
				+ " WHERE "+RESULT_SYNC+"=0;", null);
		int i = c.getCount();
		Log.i("count", "list count is---" + i);
		BarcodePost bar = null;
		if (c.moveToFirst()) {
			do {
				bar = new BarcodePost();
				bar.setDbid(c.getString(0));
				bar.setId(id);
				bar.setResult(c.getString(1));
				bar.setSdate(c.getString(2));
				bar.setStime(c.getString(2));
				bar.setSby(byname);
				bar.setProgramname(""+Globals.CURRENT_PROGRAM);
				blist.add(bar);
				
			} while (c.moveToNext());
		}
		return blist;
	}

	public String getScaneDetails(String data) {
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor c = database.rawQuery(" SELECT * FROM " + TABLE_BARCODE
				+ " WHERE " + SCANE_RESULT + "='" + data + "' ORDER BY " + ID
				+ " DESC;", null);
		StringBuilder sb = new StringBuilder();
		if (c.moveToFirst()) {
			do {
				String s = c.getString(2);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sm = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				try {
					cal.setTime(sm.parse(s));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				s = new SimpleDateFormat("hh:mm a    ddMMM ",
						Locale.getDefault()).format(cal.getTime());
				sb.append(s);
				sb.append("\r\n");
			} while (c.moveToNext());
		}
		Log.i("DATABASE", sb.toString());
		return sb.toString();
	}

	public ArrayList<Barcode> getSearchDetails(String startTime, String endTime) {
		SQLiteDatabase database = this.getReadableDatabase();
		ArrayList<Barcode> array = new ArrayList<Barcode>();
		Cursor c = database.rawQuery(" SELECT * FROM " + TABLE_BARCODE
				+ " WHERE " + RESULT_TIME + " BETWEEN datetime('" + startTime
				+ "') AND datetime('" + endTime + "') GROUP BY " + SCANE_RESULT
				+ ";", null);
		search_count = c.getCount();
		Log.i("count", "count from search is" + search_count);
		Barcode bar = null;
		if (c.moveToFirst()) {
			do {
				bar = new Barcode();
				bar.setId(c.getLong(0));
				bar.setBarcode(c.getString(1));
				bar.setContent(getSearchDetails1(bar.getBarcode(), startTime,
						endTime));
				array.add(bar);
			} while (c.moveToNext());
		}
		return array;
	}

	public String getSearchDetails1(String data, String startTime,
			String endTime) {
		SQLiteDatabase database = this.getReadableDatabase();
		Cursor c = database.rawQuery(" SELECT * FROM " + TABLE_BARCODE
				+ " WHERE " + SCANE_RESULT + "='" + data + "' AND RESULT_TIME "
				+ " BETWEEN datetime('" + startTime + "') AND datetime('"
				+ endTime + "')" + " ORDER BY " + ID + " DESC;", null);
		StringBuilder sb = new StringBuilder();
		if (c.moveToFirst()) {
			do {
				String s = c.getString(2);
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sm = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
				try {
					cal.setTime(sm.parse(s));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				s = new SimpleDateFormat("hh:mm a    ddMMM ",
						Locale.getDefault()).format(cal.getTime());
				sb.append(s);
				sb.append("\r\n");
			} while (c.moveToNext());
		}
		Log.i("DATABASE", sb.toString());
		return sb.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODE);
		onCreate(db);
	}
	
	public void updateScanList(ArrayList<BarcodePost> list){
		for(BarcodePost p:list){
			String query = "UPDATE "+TABLE_BARCODE+" SET "+RESULT_SYNC+"=1 WHERE "+ID+"="+p.getDbid()+";";
			getWritableDatabase().execSQL(query);
		}
	}
	
}
