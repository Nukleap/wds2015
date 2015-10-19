package com.ida.wds2015;

import com.daimajia.androidanimations.library.Techniques;
import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Poster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityVoteFillDetails extends MainActivityCommon {
	EditText e1,e2;
	Poster poster;
	String email,mobile;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_fill_details);
		setMaterialDesign();
		setBackAsClose();
		attachUI();
		registerUI();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(rec);
		super.onDestroy();
	}
	
	private void registerUI(){
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_POST_VOTE));
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Bundle b = intent.getExtras();
			if(b!=null){
				int i = b.getInt("status");
				if(i>=1){
					poster.setCount(poster.getCount()+i);
					sendBroadcast(new Intent(Constants.BROADCAST_POST_VOTE_DONE));
					Globals.posterList.set(poster.getPosition(),poster);
					finish();
				}else{
					
				}
			}
		}
	};
	
	private void attachUI(){
		e1=(EditText)findViewById(R.id.edittext_1);
		e2=(EditText)findViewById(R.id.edittext_2);
		poster=(Poster) Globals.clipobj;
		setMaterialTitle("Vote for "+poster.getAuthor());
		if(Globals.user.isRegistered()){
			e1.setText(""+Globals.user.getEmailid());
			e2.setText(""+Globals.user.getMobile());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_add_note, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()== R.id.action_save_note){
			saveDetails();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void saveDetails(){
		email=""+e1.getText().toString();
		mobile=""+e2.getText().toString();
		if(email.length()> 0 && mobile.length()>=10){
			Log.i("Poster Article ID", ""+poster.getArticleid());
			poster.setEmail(email);
			poster.setMobile(mobile);
			AppSyncManager.getInstance(getApplicationContext()).postPosterVoteDatails(poster);
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animate(toolbar, Techniques.SlideInUp);
		}
	}
}
