package com.ida.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DateSorter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.utils.DateUtils;
import com.ida.wds2015.MainActivityCommon;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.SelectedFee;
import com.ida.wds2015.classes.SelectedFeeRoot;
import com.ida.wds2015.classes.Transaction;

public class TransactionArrayAdapter extends ArrayAdapter<Transaction> {

	Context context;
	ArrayList<Transaction> objects;
	
	public TransactionArrayAdapter(Context context,ArrayList<Transaction> list ) {
		super(context,R.layout.list_item_transaction,list);
		this.objects = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =(View)inflate.inflate(R.layout.list_item_transaction,parent,false);
		TextView tv1=(TextView)view.findViewById(R.id.textview_1);
		TextView tv2=(TextView)view.findViewById(R.id.textview_2);
		TextView tv3=(TextView)view.findViewById(R.id.textview_3);
		Transaction tr=objects.get(position);
		tv1.setText(""+tr.getOrder_Id());
		tv2.setText(""+"INR "+tr.getAmount());
		if(tr.getJsondata().length()>0){
			try{
				Type type = new TypeToken<SelectedFeeRoot>(){}.getType();
				SelectedFeeRoot root = new Gson().fromJson(tr.getJsondata(),type);
				ArrayList<SelectedFee> list = root.getRoot().getSubroot();
				StringBuilder builder = new StringBuilder();
				for(SelectedFee s:list){
					builder.append("<br>"+s.getFee_name()+"<br>INR <b>"+s.getFee_amount()+"</b>");
				}
				tv3.setText(Html.fromHtml(builder.toString()));
				Log.i("Programm...", "Programm Detals"+ tr.getJsondata());
			}catch(Exception e){
				tv3.setText("");
			}
		}
		return view;
	}
}