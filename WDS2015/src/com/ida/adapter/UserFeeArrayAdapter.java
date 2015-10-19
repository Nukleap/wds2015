package com.ida.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ida.wds2015.ActivityRegistrationWizard2;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.UserFee;

public class UserFeeArrayAdapter extends ArrayAdapter<UserFee> {

	Context context;
	ArrayList<UserFee> objects;
	public UserFeeArrayAdapter(Context context,ArrayList<UserFee> objects){
		super(context, R.layout.list_item_fee_list,objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto generated method stub
		View view = null;
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		UserFee fee = (UserFee)objects.get(position);
		if(fee.isGroup()){
			view = (View)inflater.inflate(R.layout.header_view_speaker_detail, parent, false);
			TextView tv = (TextView)view.findViewById(R.id.header_textview_1);
			tv.setText(fee.getDeligate_Type());
			view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
		}else{
			view = (View)inflater.inflate(R.layout.list_item_fee_list, parent, false);
			CheckBox c = (CheckBox)view.findViewById(R.id.checkbox_1);
			c.setTag(position);
			c.setOnClickListener(clk);
			String str = ""+fee.getProgramName()+"<br><b>INR "+fee.getAmount()+"</b>";
			c.setText(Html.fromHtml(str));
			view.setBackgroundColor(context.getResources().getColor(R.color.CurrentFore));
		}
		return view;
	}
	
	public OnClickListener clk = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			CheckBox c = (CheckBox)v;
			int tag = Integer.parseInt(""+c.getTag());
			objects.get(tag).setSelected(c.isChecked());
			((ActivityRegistrationWizard2)context).refreshPayment();
		}
	};
	
}
