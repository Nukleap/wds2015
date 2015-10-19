package com.ida.wds2015;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ida.adapter.SpeakarDetailAdapter;
import com.ida.manager.AppSyncManager;
import com.ida.utils.DateUtils;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.Subject;
import com.ida.wds2015.classes.WdsNotification;

public class ActivitySpeakerDetails extends MainActivityCommon {
	TextView t1,t2,t3;
	ListView listview;
	Speaker sp;
	NetworkImageView image;
	ArrayList<Subject> records;
	SpeakarDetailAdapter adapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speakar_details);
		setMaterialDesign();
		setMaterialTitle("");
		setMaterialTrans();
		showBack();
		attachUI();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animate(image, Techniques.SlideInDown);
			animate(listview,Techniques.SlideInUp);
		}
	}
	
	private void attachUI()
	{
		records = new ArrayList<Subject>();
		adapter = new SpeakarDetailAdapter(this, records);
		t1=(TextView)findViewById(R.id.textview_1);
		t1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				addTofavorite();
			}
		});
		image = (NetworkImageView)findViewById(R.id.network_image_1);
		
		t2=(TextView)findViewById(R.id.textview_2);
		t3=(TextView)findViewById(R.id.textview_3);
		listview=(ListView)findViewById(R.id.listview_1);
		listview.setAdapter(adapter);
		sp = (Speaker)Globals.clipobj;
		setFav();
		String temp = sp.getSpeakename();
		if(temp.length()>0&&!temp.equals(".")){
			t1.setText(""+sp.getSpeakename());
			Bitmap bmp = ((BitmapDrawable)image.getDrawable()).getBitmap();
			t1.setTextColor(getAverageColor(bmp));
		}
		temp = sp.getDesignation();
		if(temp.length()>0&&!temp.equals(".")){
			t2.setText(""+sp.getDesignation());
		}else{
			t2.setVisibility(View.GONE);
		}
		temp = sp.getQualification();
		if(temp.length()>0&&!temp.equals(".")){
			t3.setText(""+sp.getQualification());
		}else{
			t3.setVisibility(View.GONE);
		}
		populateSubs();
		makeImageReq();
	}
	
	private void populateSubs(){
		records.clear();
		for(Speaker spc:Globals.speakers){
			if(spc.getSpeakerid()==sp.getSpeakerid()){
				records.add(new Subject(spc.getProgramName()));
				JsonObject obj = spc.getData();
				if(obj!=null){
					try{
						JsonArray vwarr = obj.getAsJsonArray("vw");
						Type type = new TypeToken<ArrayList<Subject>>(){}.getType();
						ArrayList<Subject> sublist = new Gson().fromJson(vwarr, type);
						records.addAll(sublist);
						Log.i("JSONObject", "Array Found");
					}catch(Exception e){
						JsonObject vw = obj.getAsJsonObject("vw");
						Type type = new TypeToken<Subject>(){}.getType();
						Subject sub = new Gson().fromJson(vw, type);
						records.add(sub);
						Log.i("JSONObject", "Object Found");
					}
				}
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	private void makeImageReq(){
		final NetworkImageView net = (NetworkImageView)findViewById(R.id.network_image_1);
		String url = ""+Constants.URL_SPEAKER_PROFILE_IMG+sp.getSpeakerid()+".jpg";
		net.setImageUrl(url, AppController.getInstance(getApplicationContext()).getImageLoader());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_add_note:
			gotoAddNote();
			break;
		case R.id.action_favorite:
			addTofavorite();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void gotoAddNote(){
		Intent intent = new Intent(this,ActivityAddNote.class);
		intent.putExtra("note", ""+sp.getNote());
		startActivityForResult(intent,45);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		if(resultCode==RESULT_OK){
			Bundle b = intent.getExtras();
			if(b!=null){
				String note = ""+b.getString("note");
				sp.setNote(note);
				makeSave();
			}
		}
	}
	
	private void addTofavorite(){
		sp.setFavorite(!sp.isFavorite());
		setFav();
		makeSave();
	}
	
	private void makeSave(){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AppSyncManager.getInstance(getApplicationContext()).saveSpeaker();
			}
		});
	}
	
	private void setFav(){
		if(sp.isFavorite()){
			
			t1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
			for(Subject sub:records){
				if(!sub.isGroup()&& sub.getSelected()!= null){
					
					StringTokenizer token = new StringTokenizer(sub.getSelected(), ",");
					Date d = DateUtils.parseDate(token.nextToken()+" "+token.nextToken(), "dd-MM-yyyy hh:mm:a");
					Calendar c = Calendar.getInstance();
					c.setTime(d);
					c.add(Calendar.MINUTE, -15);
					
					WdsNotification wds = new WdsNotification();
					String json = token.nextToken();
					wds.setTitle("Now:"+sp.getSpeakename());
					wds.setDesc(token.nextToken()+":"+sp.getProgramName());
					Type type = new TypeToken<WdsNotification>(){}.getType();
					json = new Gson().toJson(wds, type);
					
					AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
					Intent intent = new Intent(getApplicationContext(),SpeakerBroadcastReceiver.class);
					intent.putExtra("json", ""+json);
					PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
					manager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pIntent);
					Log.i(this.getClass().getName(), "Set Alarm For Speaker "+sp.getSpeakename()+" "+DateUtils.formatDate(c.getTime(), "dd MMM yyyy hh:mm a"));
				}
			}
		}else{
			t1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_book, 0, 0, 0);
		}
	}	
}
