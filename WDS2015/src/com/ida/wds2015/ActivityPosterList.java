package com.ida.wds2015;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.adapter.PosterArrayAdapter;
import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Poster;
import com.ida.wds2015.classes.PosterRoot;
import com.ida.wds2015.classes.PosterSubroot;
import com.ida.wds2015.database.DatabaseHelper;
import com.ida.wds2015.database.DatabaseRow;

public class ActivityPosterList extends MainActivityCommon{
	ListView listview;
	PosterArrayAdapter adapter;
	ArrayList<Poster> records;
	TextView tv;
 	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poster_listview);
		setMaterialDesign();
		setMaterialTitle("Posters");
		if(Globals.posterList==null){
			setMaterialTitle("Please wait...");
			AppSyncManager.getInstance(getApplicationContext()).getPoster();
		}
		showBack();
		attachUI();
		
	}
 	@Override
 	public void onWindowFocusChanged(boolean hasFocus) {
 		// TODO Auto-generated method stub
 		super.onWindowFocusChanged(hasFocus);
 		if(hasFocus){
 			animate(listview, Techniques.SlideInRight);
 		}
 	}
 	@Override
 	protected void onDestroy() {
 		// TODO Auto-generated method stub
 		unregisterReceiver(rec);
 		unregisterReceiver(recdone);
 		super.onDestroy();
 	}
 	
 	private void attachUI(){
 		listview=(ListView)findViewById(R.id.listview_1);
 		tv=(TextView)findViewById(R.id.textview_3);
 		records=new ArrayList<Poster>();
 		adapter= new PosterArrayAdapter(this, records);
 		listview.setAdapter(adapter);
 		registerUI();
 		populateList();
 	}
 	
 	private void registerUI(){
 		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_REQUEST_POSTER));
 		registerReceiver(recdone, new IntentFilter(Constants.BROADCAST_POST_VOTE_DONE));
 	}
 	
 	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			populateList();
		}
	};
	
	private BroadcastReceiver recdone = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			done();
		}
	};
	
	private void done(){
		Type type = new TypeToken<PosterRoot>(){}.getType();
		PosterRoot root = new PosterRoot();
		PosterSubroot subroot = new PosterSubroot();
		subroot.setSubroot(Globals.posterList);
		root.setRoot(subroot);
		DatabaseRow row = new DatabaseRow(new Gson().toJson(root,type));
		DatabaseHelper.getInstance(getApplicationContext()).insertData(DatabaseHelper.TABLE_POSTER,row);
		populateList();
	}
	
 	private void populateList(){
 		records.clear();
 		if(Globals.posterList!=null){
 			for(Poster p :Globals.posterList){
 				records.add(p);		
 			}
 			adapter.notifyDataSetChanged();
 			setMaterialTitle(""+Globals.posterList.size()+" Posters");
 		}
 	}
 	
}
