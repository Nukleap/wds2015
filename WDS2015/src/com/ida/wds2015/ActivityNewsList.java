package com.ida.wds2015;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ida.adapter.NewsArrayAdpater;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.News;
import com.ida.wds2015.classes.UserNavigation;

public class ActivityNewsList extends MainActivityCommon {
	ListView listview;
	NewsArrayAdpater adapter;
	ArrayList<News> records;
	String query = "";
	@Override
	protected void onCreate( Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		setMaterialDesign();
		setMaterialTitle("News");
		showBack();
		attachUI();
	}
	
	private void attachUI(){
		listview = (ListView)findViewById(R.id.listview_1);
		records = new ArrayList<News>();
		adapter = new NewsArrayAdpater(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		populateList();
	}
	
	private void populateList(){
		if(Globals.news!=null){
			if(Globals.news.size()>0){
				for(News n:Globals.news){
					records.add(n);
				}
			}
		}
		adapter.notifyDataSetChanged();	
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Globals.clipobj = records.get(position);
		}
	};
}
