package com.ida.wds2015;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ida.adapter.ExhibitorArrayAdapter;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.Poster;
import com.ida.wds2015.classes.PrintClass;
import com.ida.wds2015.classes.ProgramCategory;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.UserNavigation;

public class ActivityListView extends MainActivityCommon{
	ListView listview;
	ExhibitorArrayAdapter adapter;
	ArrayList<Object> records;
	String query = "";
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
		records = new ArrayList<Object>();
		adapter = new ExhibitorArrayAdapter(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		populateList();
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Globals.clipobj = records.get(position);
			gotoNext();
		}
	};
	
	private void gotoNext(){
		switch(UserNavigation.SELECTED_GRID){
			case 1:
				Intent intent = new Intent(this,ActivityDetail.class);
				startActivity(intent);
				break;
			case 2:
				ProgramCategory cat = (ProgramCategory)Globals.clipobj;
				UserNavigation.SELECTED_PROGRAM_CATEGORY =  cat.getProgramid();
				gotoProgrammeList(cat.getProgramecategoryname());
				break;
			case 3:
				Programme p = (Programme)Globals.clipobj;
				UserNavigation.SELECTED_PROGRAMME = p.getSub_Program();
				gotoSpeaker(""+p.getProgramName());
				break;
		}
		
	}
	
	private void populateList(){
		records.clear();
		query = makeLower(query);
		switch(UserNavigation.SELECTED_GRID){
		case 1:
			for(Exhibitor ex:Globals.exlist){
				if(query.length()>0){
					if(makeLower(ex.getName()).contains(query)){
						records.add(ex);
					}
				}else{
					records.add(ex);
				}
			}
			break;
		case 2:
			for(ProgramCategory ex:Globals.prCatlist){
				if(query.length()>0){
					if(makeLower(ex.getProgramecategoryname()).contains(query)){
						records.add(ex);
					}
				}else{
					records.add(ex);
				}
			}
			break;
		case 3:
			for(Programme ex:Globals.programmes){
				if(query.length()>0){
					if(makeLower(ex.getProgramName()).contains(query)){
						records.add(ex);
					}
				}else{
					records.add(ex);
				}
			}
			break;
		case 4:
			for(Speaker ex:Globals.speakers){
				if(query.length()>0){
					if(makeLower(ex.getSpeakename()).contains(query)){
						records.add(ex);
					}
				}else{
					records.add(ex);
				}
			}
			break;
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
