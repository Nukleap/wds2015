package com.ida.wds2015;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ida.adapter.FavoriteArrayAdapter;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.FavoriteListItem;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.UserNavigation;

public class ActivityFavoriteList extends MainActivityCommon {
	ListView listview;
	FavoriteArrayAdapter adapter;
	ArrayList<FavoriteListItem> records;
	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		setMaterialDesign();
		setMaterialTitle(""+Constants.menus[UserNavigation.SELECTED_GRID]);
		showBack();
		attachUI();
	}
	
	private void attachUI(){
		listview = (ListView)findViewById(R.id.listview_1);
		records = new ArrayList<FavoriteListItem>();
		adapter = new FavoriteArrayAdapter(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateList();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_share, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			gotoNext(position);
		}
	};
	
	private void gotoNext(final int position){
		FavoriteListItem fav = records.get(position);
		Globals.clipobj = fav.getTag();
		if(fav.isSpeaker()){
			 gotoSpeakerDetais();
		}else{
			Intent intent = new Intent(this,ActivityDetail.class);
			startActivity(intent);
		}
	}
	
	private void populateList(){
		FavoriteListItem fav;
		ArrayList<FavoriteListItem> temp = new ArrayList<FavoriteListItem>();
		records.clear();
		
		for(Speaker sp:Globals.speakers){
			if(sp.isFavorite()){
				fav = new FavoriteListItem();
				fav.setSpeaker(true);
				fav.setTag(sp);
				temp.add(fav);
			}
		}
		if(temp.size()>0){
			fav = new FavoriteListItem("Speakers");
			records.add(fav);
			records.addAll(temp);
		}
		temp.clear();
		
		for(Exhibitor ex:Globals.exlist){
			if(ex.isFavorite()){
				fav = new FavoriteListItem();
				fav.setSpeaker(false);
				fav.setTag(ex);
				temp.add(fav);
			}
		}
		
		if(temp.size()>0){
			fav = new FavoriteListItem("Exhibitors");
			records.add(fav);
			records.addAll(temp);
		}
		temp.clear();
		
		
		for(Speaker sp:Globals.speakers){
			if(sp.getNote().length()>0){
				fav = new FavoriteListItem();
				fav.setSpeaker(true);
				fav.setShowNote(true);
				fav.setTag(sp);
				temp.add(fav);
			}
		}
		
		for(Exhibitor ex:Globals.exlist){
			if(ex.getNote()!=null){
			if(ex.getNote().length()>0){
				fav = new FavoriteListItem();
				fav.setSpeaker(false);
				fav.setShowNote(true);
				fav.setTag(ex);
				temp.add(fav);
			}}
		}
		
		if(temp.size()>0){
			fav = new FavoriteListItem("Notes");
			records.add(fav);
			records.addAll(temp);
		}
		temp.clear();
		adapter.notifyDataSetChanged();
	}	
}
