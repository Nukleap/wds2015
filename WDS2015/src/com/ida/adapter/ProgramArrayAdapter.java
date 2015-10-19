package com.ida.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ida.wds2015.R;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.PrintClass;
import com.ida.wds2015.classes.ProgramCategory;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.UserNavigation;

public class ProgramArrayAdapter extends ArrayAdapter<Programme> {

	ArrayList<Programme> objects;
	Context context; 
	public ProgramArrayAdapter(Context context, ArrayList<Programme> objects) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.exhibitor_list_item,objects);
		this.objects = objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =(View)inflate.inflate(R.layout.exhibitor_list_item,parent,false);
		TextView tv=(TextView)view.findViewById(R.id.textview_1);
		Programme p = objects.get(position);
		tv.setText(""+p.getProgramName());
		if((position%2)==0){
			view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
		}else{
			view.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
		}
		return view;
		
	}

}
