package com.ida.wds2015;

import java.util.ArrayList;

import com.ida.adapter.ProgramArrayAdapter;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.UserNavigation;
import com.ida.wds2015.scan.BarcodeScaneList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ActivityScanLogin extends MainActivityCommon {

	EditText edit1, edit2;
	Button buttonLogin;
	RadioGroup radio_group;
	String password;
	int userid;
	int position = 0;
	ListView listview;
	ProgramArrayAdapter adapter;
	ArrayList<Programme> records;
	Context context;
	LinearLayout linear1, linear2;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_login);
		setMaterialDesign();
		setMaterialTitle("Login");
		Globals.currentuser = Integer.parseInt(getResources().getString(R.string.admin_userid));
		showBack();
		attachUI();
	}

	private void attachUI() {
		edit1 = (EditText) findViewById(R.id.edittext_1);
		edit2 = (EditText) findViewById(R.id.edittext_2);
		buttonLogin = (Button) findViewById(R.id.button_1);
		linear1=(LinearLayout)findViewById(R.id.lini_1);
		linear2=(LinearLayout)findViewById(R.id.lini_2);
		radio_group = (RadioGroup) findViewById(R.id.radiogroup_1);
		radio_group.setOnCheckedChangeListener(clk);
		buttonLogin.setOnClickListener(btnclk);
		listview = (ListView) findViewById(R.id.listview_1);
		records = new ArrayList<Programme>();
		adapter = new ProgramArrayAdapter(this, records);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(listclk);
		listview.setVisibility(View.GONE);
		setLoginUI();
		populateList();
	}

	private OnItemClickListener listclk = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Programme p = records.get(position);
			Globals.CURRENT_PROGRAM = ""+p.getSub_Program();
			gotoNext();
		}
	};

	private void populateList() {
		records.clear();
		if (Globals.programmes != null) {
			for (Programme p : Globals.programmes) {
				records.add(p);
			}
		}
		adapter.notifyDataSetChanged();
	}

	private OnCheckedChangeListener clk = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			edit1.setText("");
			edit2.setText("");
			switch (checkedId) {
			case R.id.radiobutton_1:
				edit1.setHint("User id");
				position = 0;
				break;
			case R.id.radiobutton_2:
				edit1.setHint("Exhibitor id");
				position = 1;
				break;
			}
			setLoginUI();
		}
	};
	
	private void setLoginUI(){
		if(position==1){
			listview.setVisibility(View.GONE);
		}
		edit1.requestFocus();
		edit2.setEnabled(position == 0);
	}

	private OnClickListener btnclk = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			listview.setVisibility(View.GONE);
			if (edit1.getText().toString().length() > 0) {
				userid = Integer.parseInt(edit1.getText().toString());
				password = edit2.getText().toString();
				switch (position) {
				case 0:
					if (edit1.getText().toString().length() > 0 && edit2.getText().toString().length() > 0) {
						int adminid = Integer.parseInt(getResources().getString(R.string.admin_userid));
						userid = Integer.parseInt(edit1.getText().toString());
						password = edit2.getText().toString();
						if (userid == adminid && password.equals(getResources().getString(R.string.admin_password))) {
							listview.setVisibility(View.VISIBLE);
							linear1.setVisibility(View.GONE);
							linear2.setVisibility(View.GONE);
							setMaterialTitle("Programme");
						} else {
							showMsg("Please check user id & password");
							return;
						}
					}
					break;
				case 1:
					for (Exhibitor ex : Globals.exlist) {
						if (userid == Integer.parseInt(ex.getExh_id())) {
							gotoNext();
							break;
						}
					}
					break;
				}
			}
		}
	};

	private void gotoNext() {
		Globals.currentuser = userid;
		Intent i = new Intent(this, BarcodeScaneList.class);
		startActivity(i);
	}
}
