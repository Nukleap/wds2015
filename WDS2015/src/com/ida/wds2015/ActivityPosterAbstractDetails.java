package com.ida.wds2015;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.daimajia.androidanimations.library.Techniques;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Poster;

public class ActivityPosterAbstractDetails extends MainActivityCommon {
	Poster poster;
	TextView t1,t2,t3,t4;
	Button b1;
	ArrayList<String> refers;
	NetworkImageView image;
	LinearLayout lini;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postal_abstract_detail);
		setMaterialDesign();
		showBack();
		attachUI();
	}
	
	private void attachUI(){
		t1=(TextView)findViewById(R.id.textview_1);
		t2=(TextView)findViewById(R.id.textview_2);
		t3=(TextView)findViewById(R.id.textview_3);
		t4=(TextView)findViewById(R.id.textview_4);
		b1 = (Button)findViewById(R.id.button_1);
		lini = (LinearLayout)findViewById(R.id.linearlayout_1);
		b1.setOnClickListener(clk);
		poster=(Poster)Globals.clipobj;
		refers = new ArrayList<String>();
		setMaterialTitle(""+poster.getAuthor());
		t1.setText(Html.fromHtml("<b>Aim and Purpose</b><br><small>"+poster.getAim()+"</small>"));
		t2.setText(Html.fromHtml("<b>Materials and Method</b><br><small>"+poster.getMaterials()+"</small>"));
		t3.setText(Html.fromHtml("<b>Result</b><br><small>"+poster.getResult()+"</small>"));
		t4.setText(Html.fromHtml("<b>Summary and Conclusion</b><br><small>"+poster.getSummary()+"</small>"));
		showImages(this);
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
			animate(b1, Techniques.SlideInUp);
		}
	}
	
	private OnClickListener clk = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			gotoMakeVote();
		}
	};
	
	private void showImages(final Context context){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=1;i<=poster.getImage_count();i++){
					Button b = new Button(context);
					b.setText("Poster "+i);
					b.setTextColor(context.getResources().getColor(R.color.Teal));
					b.setPadding(5, 5, 5, 5);
					b.setTag(""+Constants.URL_POSTER_PROFILE_IMG+poster.getArticleid()+"_"+i+".png");
					b.setOnClickListener(imageClk);
					lini.addView(b);
					((MainActivityCommon)context).animate(b, Techniques.SlideInRight);
				}
			}
		});
	}
	
	private OnClickListener imageClk = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			final String url = (String)v.getTag();
			Intent inw = new Intent(Intent.ACTION_VIEW);
			inw.setData(Uri.parse(url));
			startActivity(inw);
		}
	};
	
	
}
