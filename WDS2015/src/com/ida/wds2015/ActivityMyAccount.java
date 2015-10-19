package com.ida.wds2015;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.ida.adapter.SelectedFeeArrayAdapter;
import com.ida.manager.AppSyncManager;
import com.ida.utils.InternetUtils;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.SelectedFee;

public class ActivityMyAccount extends MainActivityCommon {
	ListView listview;
	TextView t1,t2,t3,t4;
	NetworkImageView barcodeimg;
	ArrayList<SelectedFee> list;
	SelectedFeeArrayAdapter adapter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account);
		setMaterialDesign();
		setMaterialTitle("My Account");
		showBack();
		attachUI();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterUI();
		super.onDestroy();
	}
	
	private void attachUI(){
		t1=(TextView)findViewById(R.id.textview_1);
		t2=(TextView)findViewById(R.id.textview_2);
		t3=(TextView)findViewById(R.id.textview_3);
		t4=(TextView)findViewById(R.id.textview_4);
		barcodeimg = (NetworkImageView)findViewById(R.id.network_image_1);
		t1.setText(""+Globals.user.getName()+" "+Globals.user.getLname());
		t2.setText(""+Globals.user.getCityname()+"\n"+Globals.user.getStatename()+","+Globals.user.getCountry());
		t3.setText(""+Globals.user.getEmailid());
		t4.setText(""+Globals.user.getMobile());
		listview = (ListView)findViewById(R.id.listview_1);
		list = new ArrayList<SelectedFee>();
		adapter = new SelectedFeeArrayAdapter(this,list);
		listview.setAdapter(adapter);
		registerUI();
		populateList();
		if(Globals.transactions!=null){
			try{
				String delid = Globals.transactions.get(0).getRoot().getSubroot().getDelegateid();
				Log.i("Delegate ID", ""+delid);
				AppSyncManager.getInstance(getApplicationContext()).getPaymentDetails(delid);
				barcodeimg.setImageUrl(Constants.getBarcode(delid), AppController.getInstance(getApplicationContext()).getImageLoader());
			}catch(Exception e){
				Log.i("Delegate ID", ""+e.toString());
			}
		}
	}
	private void populateList(){
		list.clear();
 		if(Globals.payments!=null){
 			HashMap<String, ArrayList<SelectedFee>> keys = new HashMap<String, ArrayList<SelectedFee>>();
 			for(SelectedFee fee:Globals.payments){
 				ArrayList<SelectedFee> allfee = keys.get(fee.getTran_status());
 				if(allfee==null){
 					allfee = new ArrayList<SelectedFee>();
 				}
 				allfee.add(fee);
 				keys.put(fee.getTran_status(), allfee);
 			}
 			Set<String> allkeys = keys.keySet();
 			for(String k:allkeys){
 				SelectedFee s = new SelectedFee(k);
 				list.add(s);
 				list.addAll(keys.get(k));
 			}
 		}
 		adapter.notifyDataSetChanged();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animate(t1, Techniques.SlideInRight);
			animate(t2, Techniques.SlideInRight);
			animate(t3, Techniques.SlideInRight);
			animate(t4, Techniques.SlideInRight);
			animate(listview,Techniques.SlideInUp);
		}
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
		if (item.getItemId() == R.id.action_buy) {
			if(InternetUtils.getInstance(getApplicationContext()).available()){
				showMsg("Please wait...");
				AppSyncManager.getInstance(getApplicationContext()).getFeeDetails();
			}else{
				showMsg("No Internet Connection");
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void registerUI(){
		registerReceiver(recfee, new IntentFilter(Constants.BROADCAST_USER_FEE));
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_PAYMENT_DONE));
		registerReceiver(rectrans, new IntentFilter(Constants.BROADCAST_TRANS_DETAIL));
	}
	
	private void unregisterUI(){
		unregisterReceiver(recfee);
		unregisterReceiver(rectrans);
		unregisterReceiver(rec);
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//populateList();
		}
	};
	
	private BroadcastReceiver recfee = new BroadcastReceiver() {
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
	
	private BroadcastReceiver rectrans = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if(Globals.payments!=null){
				populateList();
			}else{
				showMsg("No Internet Connection");
				return;
			}
		}
	};
	
}
