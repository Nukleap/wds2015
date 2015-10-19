package com.ida.wds2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.UserNavigation;
import com.ida.wds2015.database.DatabaseHelper;

public class ActivityDetail extends MainActivityCommon{

	TextView t1,t2,t3,t5;
	Exhibitor ex;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exhibitor_detail);
		setMaterialDesign();
		setMaterialTitle("");
		showBack();
		attachUI();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus){
			animate(t1, Techniques.SlideInRight);
			animate(t2, Techniques.SlideInRight);
			animate(t3, Techniques.SlideInRight);
			//animate(t4, Techniques.SlideInRight);
			animate(t5, Techniques.SlideInRight);
		}
	}
	
	private void attachUI(){
		t1 = (TextView)findViewById(R.id.textview_1);
		t1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addTofavorite();
			}
		});
		t2 = (TextView)findViewById(R.id.textview_2);
		t3 = (TextView)findViewById(R.id.textview_3);t3.setOnClickListener(clk);t3.setTag(0);
		//t4 = (TextView)findViewById(R.id.textview_4);t4.setOnClickListener(clk);t4.setTag(1);
		t5=(TextView)findViewById(R.id.textview_5);
		showData();
	}
	
	public OnClickListener clk = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int index=(Integer)v.getTag();
			switch(index)
			{
				case 0:
					gotoEmail(""+ex.getEmail1());
					break;
				/*case 1:
					gotoCall(""+ex.getMobile());
					break;*/
			}
		}
	};
		
	private void showData(){
		showExhibitor();
	}
	
	private void showExhibitor(){
		ex = (Exhibitor)Globals.clipobj;
		setFav();
		setMaterialTitle("Exhibitor");
		StringBuilder sb = new StringBuilder();
		sb.append("<h1><font color=\"#00979C\">"+ex.getName()+"</h1>");
		sb.append("<small>"+ex.getAdd1()+"");sb.append("<br>");
		sb.append(""+ex.getAdd2()+"");sb.append("<br>");
		sb.append(""+ex.getAdd3()+"</small>");
		t1.setText(Html.fromHtml(sb.toString()));
		
		sb = new StringBuilder();
		sb.append("<small><font color=\"#9e9e9e\">Place</small>");
		sb.append("<br>");
		sb.append("<strong><font color=\"#37474f\">"+ex.getBlockstall()+"</strong>");
		
		t2.setText(Html.fromHtml(sb.toString()));
		
		sb = new StringBuilder();
		
		sb.append("<small><font color=\"#9e9e9e\">Email</small>");
		sb.append("<br>");//ex.getEmail1()
		sb.append("<strong><font color=\"#37474f\">Click here to send email</strong>");
		
		t3.setText(Html.fromHtml(sb.toString()));
		
		sb= new StringBuilder();
		
		/*sb.append("<small><font color=\"#9e9e9e\">Mobile</small>");
		sb.append("<br>");
		sb.append("<strong><font color=\"#37474f\">Click here to Call</strong>");
		
		t4.setText(Html.fromHtml(sb.toString()));*/
		
		sb = new StringBuilder();
		sb.append("<p></small>"+isNull(ex.getBriefinfo())+"</small></p>");
		t5.setText(Html.fromHtml(sb.toString()));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_add_note:
			gotoAddNote();
			break;
		case R.id.action_favorite:
			addTofavorite();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void gotoAddNote(){
		Intent intent = new Intent(this,ActivityAddNote.class);
		intent.putExtra("note", ""+ex.getNote());
		startActivityForResult(intent,45);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		if(resultCode==RESULT_OK){
			Bundle b = intent.getExtras();
			if(b!=null){
				String note = ""+b.getString("note");
				ex.setNote(note);
				makeSave();
			}
		}
	}
	
	private void addTofavorite(){
		ex.setFavorite(!ex.isFavorite());
		setFav();
		makeSave();
		
	}
	
	private void makeSave(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AppSyncManager.getInstance(getApplicationContext()).saveExhibitor();
			}
		});
	}
	
	private void setFav(){
		if(ex.isFavorite()){
			t1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
		}else{
			t1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.star_book, 0, 0, 0);
		}
	}
	
}
