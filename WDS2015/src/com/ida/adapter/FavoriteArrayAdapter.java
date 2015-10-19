package com.ida.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.ida.wds2015.AppController;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.FavoriteListItem;
import com.ida.wds2015.classes.Speaker;

public class FavoriteArrayAdapter extends ArrayAdapter<FavoriteListItem> {
	
	ArrayList<FavoriteListItem> FavoriteListItems;
	Context context; 
	public FavoriteArrayAdapter(Context context, ArrayList<FavoriteListItem> FavoriteListItems) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.speaker_list_item,FavoriteListItems);
		this.FavoriteListItems = FavoriteListItems;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		FavoriteListItem obj = FavoriteListItems.get(position);
		if(obj.isGroup()){
			view = (View)inflate.inflate(R.layout.group_header_favorite,parent,false);
			TextView textview = (TextView)view.findViewById(R.id.textview_group_1);
			textview.setText(""+obj.getName());
			Log.i("Group", "Found...");
		}else{
			view =(View)inflate.inflate(R.layout.speaker_list_item,parent,false);
			TextView tv=(TextView)view.findViewById(R.id.textview_1);
			NetworkImageView net = (NetworkImageView)view.findViewById(R.id.network_image_1);
			if(obj.isSpeaker()){
				net.setImageUrl(""+Constants.URL_SPEAKER_PROFILE_IMG+((Speaker)obj.getTag()).getSpeakerid()+".jpg", AppController.getInstance(context.getApplicationContext()).getImageLoader());
				String text = "";
				if(obj.isShowNote()){
					text = "\"<b>"+((Speaker)obj.getTag()).getNote()+"</b>\"<br>"+((Speaker)obj.getTag()).getSpeakename();
				}else{
					text = ""+((Speaker)obj.getTag()).getSpeakename();
				}
				tv.setText(Html.fromHtml(text));
				//view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
			}else{
				if(obj.isShowNote()){
					String str = "<b>"+((Exhibitor)obj.getTag()).getNote()+"</b><br>"+((Exhibitor)obj.getTag()).getName();
					tv.setText(Html.fromHtml(""+str));
					net.setVisibility(View.GONE);
				}else{
					tv.setText(""+((Exhibitor)obj.getTag()).getName());
					net.setVisibility(View.GONE);
				}
			}
			view.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
		}		
		return view;
	}
}
