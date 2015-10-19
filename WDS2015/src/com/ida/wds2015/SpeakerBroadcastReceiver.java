package com.ida.wds2015;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.manager.IDANotificationManager;
import com.ida.wds2015.classes.WdsNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SpeakerBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle b = intent.getExtras();
		if(b!=null){
		try{
			Type type = new TypeToken<WdsNotification>(){}.getType();
			String json = b.getString("json");
			WdsNotification wds = new Gson().fromJson(json, type);
			IDANotificationManager.showNotification(context, wds);
		}catch(Exception e){
			
		}
		
	  }
	}

}
