package com.ida.adapter;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ida.utils.DateUtils;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.News;

public class NewsArrayAdpater extends ArrayAdapter<News> {

	ArrayList<News> objects;
	Context context;
	
	public NewsArrayAdpater(Context context, ArrayList<News> objects) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.exhibitor_list_item,objects);
		this.objects = objects;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		News p = objects.get(position);
		p.setPos(position);
		LayoutInflater inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =(View)inflate.inflate(R.layout.exhibitor_list_item,parent,false);
		TextView tv=(TextView)view.findViewById(R.id.textview_1);
		String text = "";
		if(p.isToggeled()){
			text = "<h1>"+p.getNews()+"</h1><br>"+p.getData();
		}else{
			text = "<h1>"+p.getNews()+"</h1>";
		}
		tv.setText(Html.fromHtml(text));
		if((position%2)==0){
			view.setBackgroundColor(context.getResources().getColor(R.color.Devexp));
		}else{
			view.setBackgroundColor(context.getResources().getColor(R.color.Whitesmoke));
		}
		view.setTag(p);
		view.setOnClickListener(clk);
		return view;	
	}
	
	private OnClickListener clk = new OnClickListener() {
		
		@Override
		public void onClick(View v){
			// TODO Auto-generated method stub
//			News n = (News)v.getTag();
//			String text = "";
//			if(n.isToggeled()){
//				text = "<h1>"+n.getNews()+"</h1><br>"+n.getData();
//			}else{
//				text = "<h1>"+n.getNews()+"</h1>";
//			}
//			((TextView)v.findViewById(R.id.textview_1)).setText(Html.fromHtml(text));
//			n.setToggeled(!n.isToggeled());
//			objects.get(n.getPos()).setToggeled(!objects.get(n.getPos()).isToggeled());
		}
	};	
}