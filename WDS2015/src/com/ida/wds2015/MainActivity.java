package com.ida.wds2015;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.ida.adapter.HomeGridAdapter;
import com.ida.manager.AppSyncManager;
import com.ida.manager.IDANotificationManager;
import com.ida.utils.DateUtils;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.News;
import com.ida.wds2015.classes.UserNavigation;
import com.ida.wds2015.database.DatabaseHelper;
import com.ida.wds2015.scan.BarcodeScaneList;

public class MainActivity extends MainActivityCommon {
	GridView gridview;
	ArrayList<String> list;
	Button b1;
	TextView t1,t2,t3;
	LinearLayout lini;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setMaterialDesign();
        setMaterialTitle(getResources().getString(R.string.app_name));
        attachUI();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterUI();
		super.onDestroy();
	}
	
	private void attachUI(){
    	gridview=(GridView)findViewById(R.id.gridview_1);
    	b1 =(Button)findViewById(R.id.button_1);
    	//b1.setVisibility(View.GONE);
    	b1.setOnClickListener(clk);
    	b1.setText("Register 5% Off");
    	b1.setVisibility(View.GONE);
    	t1=(TextView)findViewById(R.id.textview_1);
		t2=(TextView)findViewById(R.id.textview_2);
		t3=(TextView)findViewById(R.id.textview_3);
		t3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				gotoNews();
			}
		});
		lini = (LinearLayout)findViewById(R.id.lini_1);
    	ArrayList<String> list = new ArrayList<String>();
    	for(String str:Constants.menus){
    		list.add(str);
    	}
    	gridview.setAdapter(new HomeGridAdapter(this, list));
    	gridview.setOnItemClickListener(listclk);
    	registerUI();
    	setNewsInformation();
		AppSyncManager.getInstance(getApplicationContext()).makeStartNewsData();
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(Globals.user.isRegistered()){
			if(b1!=null){
				b1.setVisibility(View.GONE);
			}
    	}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animate(lini, Techniques.SlideInLeft);
			animate(gridview,Techniques.SlideInUp);
			if(!Globals.user.isRegistered()){
				if(b1!=null){
					animate(b1, Techniques.SlideInRight);
				}
			}
		}
	}
	
	private OnClickListener clk = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			gotoRegister();
		}
	};
	
    private OnItemClickListener listclk=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			action(position);
		}
	};
	
	public void action(int position)
	{
		UserNavigation.SELECTED_GRID = position;
		
		switch(UserNavigation.SELECTED_GRID){
		case 4:
			UserNavigation.SELECTED_PROGRAMME = -1;
			Intent intent=new Intent(this,ActivitySpeakerList.class);
			startActivity(intent);
			break;
		case 5:
			Intent in=new Intent(this,ActivityFavoriteList.class);
			startActivity(in);
			break;
		case 6:
			Intent i=new Intent(this,ActivityPosterList.class);
			startActivity(i);
			break;
		case 0:
			if(Globals.user.isRegistered()){
				Intent inten=new Intent(this,ActivityMyAccount.class);
				startActivity(inten);
			}else{
				Intent intentReg = new Intent(this,ActivityRegistrationWizard1.class);
				startActivity(intentReg);
			}
			break;
		case 7:
			String url = "http://wds.org.in/Development/ExhPlan.aspx?ProgramId=1&pass=Exhibitor+Registration";
			Intent inw = new Intent(Intent.ACTION_VIEW);
			inw.setData(Uri.parse(url));
			startActivity(inw);
			break;
		
		case 9:
			
			Intent fd =new Intent(this,ActivityFeedbackForm.class);
			startActivity(fd);
			break;
		
		case 8:
			Intent scane=new Intent(this,ActivityScanLogin.class);
			startActivity(scane);
			break;
			default:
				Intent de=new Intent(this,ActivityListView.class);
				startActivity(de);
				break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_sync, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_sync) {
			showMsg("Refresh Started...");
			AppSyncManager.getInstance(getApplicationContext()).requestExhibitor();
			AppSyncManager.getInstance(getApplicationContext()).startOtherReq();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void registerUI(){
		registerReceiver(rec, new IntentFilter(Constants.BROADCAST_REQUEST_ALL));
		registerReceiver(recnews, new IntentFilter(Constants.BROADCAST_NEWS));
	}
	
	private BroadcastReceiver rec = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			showMsg("Refresh Completed...");
		}
	};
	
	private BroadcastReceiver recnews = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			setNewsInformation();
		}
	};
	
	private void setNewsInformation(){
		if(Globals.news != null){
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for(News n:Globals.news){
				Date d = DateUtils.parseDate(""+n.getFromDate(), "yyyy-MM-dd");
				Calendar fromdate = Calendar.getInstance();
				Calendar todate = (Calendar)fromdate.clone();
				fromdate.setTime(d);
				d = DateUtils.parseDate(""+n.getTo_date(), "yyyy-MM-dd");
				todate.setTime(d);
				Calendar c = Calendar.getInstance();
				if(c.after(fromdate)&&c.before(todate)){
					count+=1;
					sb.append(""+n.getNews());
					sb.append("\n");
				}
			}
			if(count>0){
				t1.setText("Latest News");
				t2.setText(""+count);
				t3.setText(makeMarquee(sb.toString().trim()));
				setMarquee(t3);
			}
		}
	}
	
	private void unregisterUI(){
		unregisterReceiver(rec);
		unregisterReceiver(recnews);
	}
}
