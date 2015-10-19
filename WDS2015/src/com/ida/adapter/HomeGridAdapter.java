package com.ida.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.Constants;

public class HomeGridAdapter extends ArrayAdapter<String> {

	Context context;
	ArrayList<String> objects;
	
	public HomeGridAdapter(Context context, ArrayList<String> objects) {
		super(context, R.layout.grid_item, objects);
		
		this.objects = objects;
		this.context = context;
	}

@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)inflater.inflate(R.layout.grid_item, parent, false);
		TextView textview = (TextView)view.findViewById(R.id.textview_1);
		textview.setText(""+Constants.menus[position]);
		textview.setCompoundDrawablesWithIntrinsicBounds(0, Constants.imageList[position], 0, 0);
		return view;
	}	
}
