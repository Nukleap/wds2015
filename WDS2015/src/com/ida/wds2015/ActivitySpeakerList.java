package com.ida.wds2015;

import java.util.ArrayList;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ida.adapter.SpeakerArrayAdapter;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.UserNavigation;

public class ActivitySpeakerList extends MainActivityCommon {
	
	ListView listview;
	SpeakerArrayAdapter adapter;
	ArrayList<Speaker> records;
	String query="";
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		setMaterialDesign();
		showBack();
		attachUI();
	}
	
	private void attachUIExtras(){
		Bundle b = getIntent().getExtras();
		if(b!=null){
			setMaterialTitle(b.getString("title"));
		}else{
			setMaterialTitle(""+Globals.speakers.size()+" Speaker ");
		}
	}
	
	private void attachUI(){
		listview = (ListView)findViewById(R.id.listview_1);
		records = new ArrayList<Speaker>();
		adapter = new SpeakerArrayAdapter(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		attachUIExtras();
		populateList();
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			Globals.clipobj = records.get(position);
			gotoSpeakerDetais();
		}
	};

	private void populateList(){
		records.clear();
		if(UserNavigation.SELECTED_PROGRAMME==-1){
			records.addAll(Globals.speakers);
		}else{
			for(Speaker p :Globals.speakers){
				if(p.getSubProgramId()== UserNavigation.SELECTED_PROGRAMME ){
					records.add(p);
				}
			}
		}
		if(query.length()>=0){
			ArrayList<Speaker> templist = new ArrayList<Speaker>();
			templist.addAll(records);
			records.clear();
			for(int i=0;i<templist.size();i++){
				Speaker s = templist.get(i);
				if(!records.contains(s)){
					if(makeLower(s.getSpeakename()).contains(query)){
						records.add(s);
					}
				}
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	    	// TODO Auto-generated method stub
	    	getMenuInflater().inflate(R.menu.menu_search, menu);
	    	SearchManager searchManager = (SearchManager) getSystemService(this.SEARCH_SERVICE);
			MenuItem searchItem = menu.findItem(R.id.action_search);
		    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		    searchView.setSearchableInfo(searchManager
	                .getSearchableInfo(getComponentName()));
		    
		    MenuItemCompat.setOnActionExpandListener(searchItem, new OnActionExpandListener() {
		        @Override
		        public boolean onMenuItemActionCollapse(MenuItem item) {
		            // Do something when collapsed
		            return true;  // Return true to collapse action view
		        }

		        @Override
		        public boolean onMenuItemActionExpand(MenuItem item) {
		            // Do something when expanded
		            return true;  // Return true to expand action view
		        }
		    });
		    	    
		    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
		        public boolean onQueryTextChange(String newText) {
		            // this is your adapter that will be filtered
		        	query = newText;
		        	searchData();
		            return true;
		        }

		        public boolean onQueryTextSubmit(String query) {
		            //Here u can get the value "query" which is entered in the search box.
		        	//searchData(query);
		        	return true;
		        }
		    };
		    searchView.setOnQueryTextListener(queryTextListener);
			return super.onCreateOptionsMenu(menu);
	    }
	 
	 private void searchData(){
		 populateList();
	 }
	
}
