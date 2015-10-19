package com.ida.adapter;

import java.util.ArrayList;

import com.ida.wds2015.ActivityPosterList;
import com.ida.wds2015.ActivityRegistrationWizard2;
import com.ida.wds2015.ActivityVoteFillDetails;
import com.ida.wds2015.Globals;
import com.ida.wds2015.MainActivityCommon;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.Poster;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.UserNavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.shapes.Shape;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class PosterArrayAdapter extends ArrayAdapter<Poster> {

		Context context;
		ArrayList<Poster> objects;
		View view;
		public PosterArrayAdapter(Context context, ArrayList<Poster> objects) {
				super(context,R.layout.list_item_poster,objects);
				this.objects = objects;
				this.context = context;	
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view =(View)inflater.inflate(R.layout.list_item_poster,parent,false);
			TextView tv1=(TextView)view.findViewById(R.id.textview_1);
			TextView tv2=(TextView)view.findViewById(R.id.textview_2);
			TextView tv3=(TextView)view.findViewById(R.id.textview_3);
			//TextView tv4=(TextView)view.findViewById(R.id.textview_4);
			Button button=(Button)view.findViewById(R.id.button_1);
			Poster p = objects.get(position);
			p.setPosition(position);
			String s=p.getAuthor();
			String c=""+s.charAt(0);
			tv1.setText(""+p.getAuthor());
			tv2.setText(""+p.getTitle());
			tv3.setText(""+c);
			button.setText(""+p.getCount()+"  Vote");
			button.setTag(p);
			button.setOnClickListener(clk);
			view.setOnClickListener(clkd);
			view.setTag(p);
			return view;
		}
		
		private OnClickListener clk= new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserNavigation.SELCTED_POSTER = ((Poster)v.getTag()).getPosition();
				Globals.clipobj = (Poster)v.getTag();
				
				gotoNext();
			}
		};
		
		private OnClickListener clkd = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Globals.clipobj = (Poster)v.getTag();
				UserNavigation.SELCTED_POSTER = ((Poster)v.getTag()).getPosition();
				Log.i("id....", "id..."+Globals.clipobj);
				((MainActivityCommon)context).gotoVoteDetails();	
			}
		};
		
		private void gotoNext(){
			Intent intent = new Intent(context,ActivityVoteFillDetails.class);
			context.startActivity(intent);
		}	
		
}
