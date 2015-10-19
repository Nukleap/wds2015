package com.ida.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ida.wds2015.AppController;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Speaker;

public class SpeakerArrayAdapter extends ArrayAdapter<Speaker> {
	
	ArrayList<Speaker> objects;
	Context context;
	
	public SpeakerArrayAdapter(Context context, ArrayList<Speaker> objects) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.speaker_list_item,objects);
		this.objects = objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Speaker p = objects.get(position);
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =(View)inflate.inflate(R.layout.speaker_list_item,parent,false);
		TextView tv=(TextView)view.findViewById(R.id.textview_1);
		NetworkImageView net = (NetworkImageView)view.findViewById(R.id.network_image_1);
		net.setImageUrl(""+Constants.URL_SPEAKER_PROFILE_IMG+p.getSpeakerid()+".jpg", AppController.getInstance(context.getApplicationContext()).getImageLoader());
		tv.setText(""+p.getSpeakename());
		if((position%2)==0){
			view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
		}else{
			view.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
		}
		return view;
		
	}
	
}
