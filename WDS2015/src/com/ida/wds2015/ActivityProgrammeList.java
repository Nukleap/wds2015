package com.ida.wds2015;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ida.adapter.ProgramArrayAdapter;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.UserNavigation;

public class ActivityProgrammeList extends MainActivityCommon {
	
	ListView listview;
	ProgramArrayAdapter adapter;
	ArrayList<Programme> records;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		setMaterialDesign();
		setMaterialTitle("");
		showBack();
		attachUI();
	}
	
	private void attachUIExtras(){
		Bundle b = getIntent().getExtras();
		if(b!=null){
			setMaterialTitle(b.getString("title"));
		}
	}
	
	private void attachUI(){
		listview = (ListView)findViewById(R.id.listview_1);
		records = new ArrayList<Programme>();
		adapter = new ProgramArrayAdapter(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		attachUIExtras();
		populateList();
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			// TODO Auto-generated method stub
			UserNavigation.SELECTED_PROGRAMME = records.get(position).getSub_Program();
			String text = records.get(position).getProgramName();
			gotoSpeaker(text);
		}
	};

	private void populateList(){
		records.clear();
		for(Programme p :Globals.programmes){
			if(p.getProgramid()==UserNavigation.SELECTED_PROGRAM_CATEGORY){
				records.add(p);
			}
		}
		adapter.notifyDataSetChanged();
	}

}
