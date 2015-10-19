package com.ida.wds2015.scan;

import java.util.ArrayList;

import com.ida.wds2015.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class BarcodeArrayAdapter extends ArrayAdapter<Barcode> {
	
	Context context;
	ArrayList<Barcode> objects;
	
	public BarcodeArrayAdapter(Context context,ArrayList<Barcode> objects) {	
		super(context, R.layout.list_item_barcode, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = (View)inflater.inflate(R.layout.list_item_barcode, parent, false);
		TextView code = (TextView)view.findViewById(R.id.barcode_textview);
		TextView time = (TextView)view.findViewById(R.id.time_textview);
		code.setText(""+objects.get(position).getBarcode());
		time.setText(""+objects.get(position).getContent());
		return view;
	}
}
	