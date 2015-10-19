package com.ida.wds2015;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.daimajia.androidanimations.library.Techniques;
import com.ida.manager.AppSyncManager;
import com.ida.utils.InternetUtils;
import com.ida.wds2015.classes.Constants;

public class ActivitySplash extends AppCompatActivity {
	
	//TextView t1;
	boolean fired = false;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		AppSyncManager.getInstance(getApplicationContext()).init();
		checkAndGo();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus && img!=null){
			MainActivityCommon.makeAnimate(img, Techniques.SlideInUp);
			MainActivityCommon.makeAnimate(t1, Techniques.SlideInDown);
			MainActivityCommon.makeAnimate(t2, Techniques.SlideInRight);
		}
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(fired){
			unregisterReceiver(rec);
		}
		super.onDestroy();
	}
	
	private void checkAndGo(){
		if(Globals.downloaded){
			gotoNext();
		}else{
			attachUI();
			registerUI();
		}
	}
	
	private void gotoNext(){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void registerUI(){
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_REQUEST_ALL));
		fired = true;
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			AppSyncManager.getInstance(getApplicationContext()).notiWelcome();
			gotoNext();
		}
	};
	
	ImageView img;
	TextView t1,t2;
	private void attachUI(){
		
		img = (ImageView)findViewById(R.id.imageview_1);
		t1 = (TextView)findViewById(R.id.textview_1);
		t2 = (TextView)findViewById(R.id.textview_2);
		t2.setText("loading...");
		if(InternetUtils.getInstance(getApplicationContext()).available()){
			AppSyncManager.getInstance(getApplicationContext()).requestExhibitor();
		}else{
			Toast.makeText(this, "No Intenet found", Toast.LENGTH_SHORT).show();
		}
	}
}
