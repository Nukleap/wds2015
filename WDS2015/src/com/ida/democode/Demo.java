package com.ida.democode;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.util.Log;

public interface Demo {
	
	
}
/*Log.i("ON START COMMAND", "on start commond...");
new Thread(new Runnable() {
    @Override
    public void run() {
            try {
                final TimerTask timertask = new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Timer timer = new Timer(true);
				        timer.scheduleAtFixedRate(timertask, 0, 10*1000);
					}
				};	
            	Thread.sleep(1000);
                    count++;
                    String str = String.valueOf(count);
                    Intent intent = new Intent("xyz");
                    intent.putExtra("abc", str);
                    sendBroadcast(intent);
                    Log.i("RUNNING...", "Service running...."+str);
            } catch (Exception e) {
            }
       
        stopSelf();
    }
}).start();
return START_STICKY;
}*/