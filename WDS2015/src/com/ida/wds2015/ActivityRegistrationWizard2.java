package com.ida.wds2015;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.adapter.UserFeeArrayAdapter;
import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.SelectedFee;
import com.ida.wds2015.classes.SelectedFeeRoot;
import com.ida.wds2015.classes.SelectedFeeSubroot;
import com.ida.wds2015.classes.User;
import com.ida.wds2015.classes.UserFee;
import com.ida.wds2015.database.DatabaseHelper;
import com.ida.wds2015.database.DatabaseRow;

public class ActivityRegistrationWizard2 extends MainActivityCommon {
	
	ListView listview;
	UserFeeArrayAdapter adapter;
	ArrayList<UserFee> records;
	ArrayList<UserFee> temp;
	TextView textview;
	Spinner spinner;
	ArrayAdapter<String> dataAdapter;
	ArrayList<String> dis;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_wizard2);
		Globals.selectedpackages = new ArrayList<UserFee>();
		setMaterialDesign();
		setMaterialTitle("");
		showBack();
		attachUI();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(rec);
		super.onDestroy();
	}
	
	private void attachUI(){
		attachUIWithSpinner();
		listview = (ListView)findViewById(R.id.listview_1);
		textview = (TextView)findViewById(R.id.textview_basic);
		textview.setText("Choose Programmes");
		records = new ArrayList<UserFee>();
		temp = new ArrayList<UserFee>();
		adapter = new UserFeeArrayAdapter(this,records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		registerUI();
	}
	
	protected void attachUIWithSpinner(){
		spinner = (Spinner)findViewById(R.id.spinner_1);
		dis = new ArrayList<String>();
		dataAdapter = new ArrayAdapter<String>
        (this, R.layout.material_spinner_item_custom,dis);
		//android.R.layout.simple_spinner_dropdown_item
		dataAdapter.setDropDownViewResource
        	(R.layout.material_spinner_item_dropdown_custom);
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(listenerSpinner);
		populateUIListSpinner();
	}
	
	private OnItemSelectedListener listenerSpinner = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// TODO Auto-generated method stub
			populateList(dis.get(pos));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void populateUIListSpinner(){
		dis.clear();
		for(UserFee u:Globals.userfees){
			if(!dis.contains(u.getDeligate_Type())){
				dis.add(u.getDeligate_Type());
			}
		}
		dataAdapter.notifyDataSetChanged();
	}
	
	private OnItemClickListener listclk = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void populateList(String s){
		records.clear();
		for(UserFee fee:Globals.userfees){
			if(fee.getDeligate_Type().equalsIgnoreCase(s)){
				if(Globals.user.getCountry().equalsIgnoreCase("india")){
					records.add(fee);
				}else{
					records.add(fee);
				}
			}
		}
		adapter.notifyDataSetChanged();
	}
	
	public void populateList(){
		records.clear();
		for(UserFee u:Globals.userfees){
			if(Globals.user.getCountry().equalsIgnoreCase("india")){
				temp.add(u);
			}else{
				temp.add(u);
			}
		}
		if(temp.size()>0){
			ArrayList<String> headers = new ArrayList<String>();
			for(UserFee u:temp){
				if(!headers.contains(u.getDeligate_Type())){
					headers.add(u.getDeligate_Type());
				}
			}
			if(headers.size()>0){
				for(String s:headers){
					UserFee grp = new UserFee(s);
					records.add(grp);
					for(UserFee fee:temp){
						if(fee.getDeligate_Type().equalsIgnoreCase(s)){
							records.add(fee);
						}
					}
				}
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_pay, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.action_buy){
			gotoPayment();
		}
		return super.onOptionsItemSelected(item);
	}

	private void gotoPayment(){
		if(Globals.selectedpackages!=null){
			if(Globals.selectedpackages.size()>0){
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
				alertDialogBuilder.setTitle("Confirm");
				alertDialogBuilder.setCancelable(false);
				// set dialog message
				alertDialogBuilder
					.setMessage("Do you want to make payment for selected programmes?")
					.setCancelable(false)
					.setPositiveButton("Now",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close
							// current activity
							makePayment();
						}
					  })
					.setNegativeButton("Later",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
			}
		}
	}
	
	private void makePayment(){
		Globals.user.setDelegate_type(""+Globals.selectedpackages.get(0).getDeligate_Type_Id());
		SelectedFeeSubroot subroot = new SelectedFeeSubroot();
		subroot.setSubroot(new ArrayList<SelectedFee>());
		SelectedFeeRoot root = new SelectedFeeRoot();
		root.setRoot(subroot);
		for(UserFee fee : Globals.selectedpackages){
			SelectedFee sel = new SelectedFee();
			sel.setCategoryId(""+fee.getCategoryId());
			sel.setFee_amount(""+fee.getAmount());
			sel.setFee_id(""+fee.getFeeid());
			sel.setFee_name(fee.getProgramName());
			sel.setTotal_amount(""+fee.getAmount());
			subroot.getSubroot().add(sel);
		}
		Type type = new TypeToken<SelectedFeeRoot>(){}.getType();
		Globals.user.setJsondata(""+new Gson().toJson(root, type));
		setMaterialTitle("Please wait...");
		AppSyncManager.getInstance(getApplicationContext()).postUserFeeDatails();
	}
	
	private void registerUI(){
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_POST_USER_FEE));
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			gotoPaymentConfirm();
		}
	};
	
	private void gotoPaymentConfirm(){
		refreshPayment();
		if(Globals.selectedpackages.size()>0 && AppSyncManager.getInstance(getApplicationContext()).getPostData().length()>0){
			Type type = new TypeToken<User>(){}.getType();
			String json = new Gson().toJson(Globals.user,type);
			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(getApplicationContext()).insertData(DatabaseHelper.TABLE_USER_PROFILE, row);
			setMaterialTitle("");
			Intent intent = new Intent(this,PaymentActivity.class);
			startActivity(intent);
			sendBroadcast(new Intent(Constants.BROADCAST_PAYMENT_DONE));
			finish();
		}
	}
	
	public void refreshPayment(){
		Globals.selectedpackages = new ArrayList<UserFee>();
		double totalfee = 0.0;
		for(UserFee f : records){
			if(f.isSelected()&&!f.isGroup()){
				totalfee+=f.getAmount();
				Globals.selectedpackages.add(f);
			}
		}
		int count = Globals.selectedpackages.size();
		if(count>0){
			textview.setText(""+count+"/"+records.size()+" Selected(Total INR "+totalfee+")");
		}else{
			textview.setText("Choose Programmes");
		}
	}
	
}
