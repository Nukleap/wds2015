package com.ida.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ida.wds2015.R;
import com.ida.wds2015.classes.SelectedFee;

public class SelectedFeeArrayAdapter extends ArrayAdapter<SelectedFee> {
	
	private Context context;
	private ArrayList<SelectedFee> objects;
	public SelectedFeeArrayAdapter(Context context,ArrayList<SelectedFee> objects){
		super(context,R.layout.list_item_transaction,objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		SelectedFee fee = objects.get(position);
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(fee.isGroup()){
			view = (View)inflate.inflate(R.layout.group_header_favorite,parent,false);
			TextView textview = (TextView)view.findViewById(R.id.textview_group_1);
			textview.setText(""+fee.getFee_name());
		}else{
			view =(View)inflate.inflate(R.layout.list_item_transaction,parent,false);
			TextView tv1=(TextView)view.findViewById(R.id.textview_1);
			TextView tv2=(TextView)view.findViewById(R.id.textview_2);
			TextView tv3=(TextView)view.findViewById(R.id.textview_3);
			tv1.setText(""+fee.getFee_name());
			tv2.setText("INR "+fee.getFee_amount());
			tv3.setText(""+fee.getThrough());
		}
		return view;
	}

}
