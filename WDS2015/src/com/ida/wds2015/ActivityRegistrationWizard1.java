package com.ida.wds2015;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ida.manager.AppSyncManager;
import com.ida.utils.InternetUtils;
import com.ida.wds2015.classes.Constants;
import com.nukleap.classes.City;
import com.nukleap.classes.Country;
import com.nukleap.classes.State;

public class ActivityRegistrationWizard1 extends MainActivityCommon {

	EditText e1,e2,e3,e4,e5,e6;
	AutoCompleteTextView city,state,country;
	Button b1;
	LinearLayout lini;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_wizard_1);
		setMaterialDesign();
		setMaterialTitle("Register");
		showBack();
		attachUI();
	}
	
	private void attachUI(){
		b1 = (Button)findViewById(R.id.button_1);
		b1.setOnClickListener(clk);
		b1.setText("Next");
		lini = (LinearLayout)findViewById(R.id.lini_1);
		e1 = (EditText)findViewById(R.id.edittext_1);
		e2 = (EditText)findViewById(R.id.edittext_2);
		e3 = (EditText)findViewById(R.id.edittext_3);
		e4 = (EditText)findViewById(R.id.edittext_4);
		e5 = (EditText)findViewById(R.id.edittext_5);
		e6 = (EditText)findViewById(R.id.edittext_6);
		bindStates();
		registerUI();
	}
	
	private OnClickListener clk = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		if(InternetUtils.getInstance(getApplicationContext()).available()){
			if(checkForValid()){
					showMsg("Please wait...");
					AppSyncManager.getInstance(getApplicationContext()).getFeeDetails();
				}else{
						showMsg("Please Enter Currect Information");
					 }
		}else{
				showMsg("No Internet Found");
			 }	
			
		}
	};
	
	private boolean checkForValid(){
		EditText edit = e1;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		Globals.user.setName(edit.getText().toString());
		
		edit = e2;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		if(!isValidMail(edit.getText().toString())){
			edit.requestFocus();
			return false;
		}
		Globals.user.setEmailid(edit.getText().toString());
		
		edit = e3;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		if(!isValidMobile(edit.getText().toString())){
			edit.requestFocus();
			return false;
		}
		Globals.user.setMobile(edit.getText().toString());
		
		edit = e4;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		Globals.user.setPassword(edit.getText().toString());
		
		edit = e5;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		Globals.user.setAdd1(edit.getText().toString());
		
		if(country.getText().toString().length()<=0){
			country.requestFocus();
			return false;
		}
		Globals.user.setCountry(country.getText().toString());
		if(state.getText().toString().length()<=0){
			state.requestFocus();
			return false;
		}
		Globals.user.setStatename(state.getText().toString());
		if(city.getText().toString().length()<=0){
			city.requestFocus();
			return false;
		}
		Globals.user.setCityname(city.getText().toString());
		
		edit = e6;
		if(edit.getText().toString().length()<=0){
			edit.requestFocus();
			return false;
		}
		Globals.user.setZipCode(edit.getText().toString());
		
		return true;
	}
	
	private void bindStates(){
		city = (AutoCompleteTextView)findViewById(R.id.address_autocompletetextview_city);
		state = (AutoCompleteTextView)findViewById(R.id.address_autocompletetextview_state);
		country = (AutoCompleteTextView)findViewById(R.id.address_autocompletetextview_country);
		country.setAdapter(Country.getAdapter(this));
		country.setOnItemClickListener(iclk);
		country.setText(Country.DEF_COUNTRY);
		attachState(Country.DEF_COUNTRY);
		state.setOnItemClickListener(iclk);
		city.setOnItemClickListener(iclk);
	}
	
	private View getCurrentId(){
		return this.getCurrentFocus();
	}
	
	public OnItemClickListener iclk = new OnItemClickListener(){
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			TextView textview = (TextView)v;
			String text = textview.getText().toString();
			switch(getCurrentId().getId()){
			case R.id.address_autocompletetextview_country:
				attachState(text);
				state.setText("");
				city.setText("");
				break;
			case R.id.address_autocompletetextview_state:
				attachCity(text);
				city.setText("");
				break;
			case R.id.address_autocompletetextview_city:
				city.setTag(City.getid(text, getApplicationContext()));
				Globals.user.setCity(""+city.getTag().toString());
				break;
			}
		}
	};
	private void attachState(String text){
		String countryid = Country.getid(text, this.getApplicationContext());
		Globals.user.setCountryid(countryid);
		country.setTag(countryid);
		state.setAdapter(State.getAdapter(countryid,this));
	}

	private void attachCity(String text){
		String stateid = State.getid(text, this.getApplicationContext());
		Globals.user.setState(stateid);
		state.setTag(stateid);
		city.setAdapter(City.getAdapter(stateid, this));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterUI();
		super.onDestroy();
	}
	
	private void registerUI(){
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_USER_FEE));
		registerReceiver(recfin, new IntentFilter(Constants.BROADCAST_PAYMENT_DONE));
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if(Globals.userfees!=null){
				gotoFeeSelection();
			}else{
				showMsg("No Internet Connection");
				return;
			}
		}
	};
	
	private BroadcastReceiver recfin = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	private void unregisterUI(){
		unregisterReceiver(recfin);
		unregisterReceiver(rec);
	}	
}

/*private OnClickListener clk = new OnClickListener() {
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(checkForValid()&&InternetUtils.getInstance(getApplicationContext()).available()){
			showMsg("Please wait...");
			AppSyncManager.getInstance(getApplicationContext()).getFeeDetails();
		}else{
			showMsg("No Internet Found");
		}
	}
};*/