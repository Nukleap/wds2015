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

public class ExhibitorArrayAdapter extends ArrayAdapter<Object>{
		
	Context context;
	ArrayList<Object> objects;
	public ExhibitorArrayAdapter(Context context, ArrayList<Object> objects) {
		super(context,R.layout.exhibitor_list_item,objects);
		this.objects = objects;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =(View)inflate.inflate(R.layout.exhibitor_list_item,parent,false);
		TextView tv=(TextView)view.findViewById(R.id.textview_1);
		PrintClass p = new PrintClass();
		switch(UserNavigation.SELECTED_GRID){
		case 1:
			Exhibitor ex = (Exhibitor)objects.get(position);
			p.setPrint1(""+ex.getName());
			p.setTag(ex);
			break;
		case 2:
			ProgramCategory pr = (ProgramCategory)objects.get(position);
			p.setPrint1(""+pr.getProgramecategoryname());
			p.setTag(pr);
			break;
		case 3:
			Programme pro = (Programme)objects.get(position);
			p.setPrint1(""+pro.getProgramName());
			p.setTag(pro);
			break;
		case 4:
			Speaker sp = (Speaker)objects.get(position);
			p.setPrint1(""+sp.getSpeakename());
			p.setTag(sp);
			break;
		}
		tv.setText(""+p.getPrint1());
		if((position%2)==0){
			view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
		}else{
			view.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
		}
		return view;
	}	
}
