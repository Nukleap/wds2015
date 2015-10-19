package com.ida.wds2015;

import android.app.Application;
import com.ida.wds2015.database.DatabaseHelper;
public class ApplicationExtender extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}
	
	private void init(){
		DatabaseHelper.getInstance(this).checkUpdate();
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
}
