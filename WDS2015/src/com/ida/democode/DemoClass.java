package com.ida.democode;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.ida.utils.DateUtils;

import android.util.Log;

public class DemoClass {
	
	
	public static void check(final String json){
		Log.i("DemoClass", ""+json);
		try {
			HashMap<String,String> hasmap=new HashMap<String, String>();
			JSONObject obj = new JSONObject(json).getJSONObject("root").getJSONObject("subroot");
			Iterator<String> keys =obj.keys();
			while(keys.hasNext()){
				String key = keys.next();
				hasmap.put(keys.next(), obj.getString(key));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getDate(){
		
		Calendar cal = Calendar.getInstance();
		DateUtils.getMonth(cal);
		DateUtils.isToday(cal);
	}
}
//For Date Formate change code..
//Date d = DateUtils.parseDate(""+p.getFromDate(), "yyyy-MM-dd");
//DateUtils.formatDate(d, "dd MMM");
//Calendar = Calendar.getInstance();    
//String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());