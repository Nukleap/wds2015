package com.ida.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ida.utils.DateUtils;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.Subject;

public class SpeakarDetailAdapter extends ArrayAdapter<Subject> {
	
	Context context;
	ArrayList<Subject> objects;
	public SpeakarDetailAdapter(Context context,ArrayList<Subject> objects ) {
		super(context, R.layout.list_item_subject,objects);
		this.context=context;
		this.objects=objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		Subject sub = objects.get(position);
		String temp = "";
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if(sub.isGroup()){
			view=(View)inflater.inflate(R.layout.group_header_favorite,parent,false);
			TextView t1=(TextView)view.findViewById(R.id.textview_group_1);
			t1.setText(Html.fromHtml("<b>"+sub.getProgramName()+"</b>"));
			t1.setPadding(10, 10, 10, 10);
			t1.setBackgroundColor(context.getResources().getColor(R.color.Gray));
			t1.setTextColor(context.getResources().getColor(R.color.Black));
			
		}else{
			view=(View)inflater.inflate(R.layout.list_item_subject,parent,false);
			TextView t1=(TextView)view.findViewById(R.id.textview_1);
			TextView t2=(TextView)view.findViewById(R.id.textview_2);
			TextView t3=(TextView)view.findViewById(R.id.textview_3);
			TextView t4=(TextView)view.findViewById(R.id.textview_4);
			TextView t5=(TextView)view.findViewById(R.id.textview_5);
			TextView t6=(TextView)view.findViewById(R.id.textview_6);
		
		LinearLayout lini = (LinearLayout)view.findViewById(R.id.lini_1);
		temp = ""+sub.getSubjectname();
		if(temp.length()>0&&!temp.equals("-")){
			t1.setText(Html.fromHtml("<small>Subject</small><br><b>"+sub.getSubjectname()+"</b>"));
		}else{
			t1.setVisibility(View.GONE);
		}
		temp = ""+sub.getTitlename();
		if(temp.length()>0&&!temp.equals("-")){
			t2.setText(Html.fromHtml("<small>Title</small><br><b>"+sub.getTitlename()+"</b>"));
		}else{
			t2.setVisibility(View.GONE);
		}
		temp = ""+sub.getTopictitle();
		if(temp.length()>0&&!temp.equals("-")){
			t3.setText(Html.fromHtml("<small>Topic</small><br><b>"+sub.getTopictitle()+"</b>"));
		}else{
			t3.setVisibility(View.GONE);
		}
		
		if(sub.getSelected()!=null){
			String selected = ""+sub.getSelected();
			StringTokenizer token = new StringTokenizer(selected, ",");
			Date d = DateUtils.parseDate(token.nextToken(), "dd-MM-yyyy");
			t4.setText(Html.fromHtml("<small>"+DateUtils.formatDate(d, "dd MMM")+"</small>"));
			t5.setText(Html.fromHtml("<small>"+token.nextToken()+"<br>  To<br>"+token.nextToken()+"</small>"));
			t6.setText(Html.fromHtml("<small>"+token.nextToken()+"</small>"));
		}else{
			lini.setVisibility(View.GONE);
		}
		}
		return view;
	}
}
