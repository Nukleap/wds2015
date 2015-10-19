package com.ida.manager;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

public class ServiceManager extends Service {

	private static final String TAG = "WDS_ServiceManager"; 
	Timer timer;
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		extendCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		extendStart();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private void extendCreate() {
		timer = new Timer();
		HandlerThread thread = new HandlerThread("wdsnewsservicestartarguments",Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}

	private void extendStart() {
		Log.i(TAG, "Service Started...");
		startTimer();
	}

	private void startTimer() {
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					Message msg = mServiceHandler.obtainMessage();
					mServiceHandler.sendMessage(msg);
				} catch (Exception e) {
				}
			}
		}, 0, 60000 * 5);
	}

	private final class ServiceHandler extends Handler {
		
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			synchronized (this) {
				Log.i(TAG, "Checking News Details");
				AppSyncManager.getInstance(getApplicationContext())
						.getNewsDetails();
			}
		}
	}
}
