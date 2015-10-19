package com.ida.wds2015;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Feedback;
import com.ida.wds2015.database.DatabaseHelper;
import com.ida.wds2015.database.DatabaseRow;

public class ActivityFeedbackForm extends MainActivityCommon {

	EditText t1,t2,t3;
	Spinner spiner;
	RatingBar rBar;
	Button button;
	Feedback f;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_form);
		setMaterialDesign();
		setMaterialTitle("Suggestion");
		showBack();
		f = new Feedback();
		attachUI();
	}
	private void attachUI(){
		t1=(EditText)findViewById(R.id.edittext_1);
		t2=(EditText)findViewById(R.id.edittext_2);
		t3=(EditText)findViewById(R.id.edittext_3);
		spiner=(Spinner)findViewById(R.id.category_list);
		spiner.setOnItemSelectedListener(itemclk);
		rBar=(RatingBar)findViewById(R.id.ratingbar);
		button=(Button)findViewById(R.id.button_1);
		button.setOnClickListener(clk);
	}
	
	private String getData(int pos){
		String[] arrays = getResources().getStringArray(R.array.category_arrays);
		return arrays[pos];
	}
	
	private OnItemSelectedListener itemclk = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long id) {
			// TODO Auto-generated method stub
			f.setCategory(getData(position));
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	};
	private boolean checkValid(){
		String str = t1.getText().toString();
		if(str.length()<=0){
			t1.requestFocus();
			return false;
		}
		f.setFeedback(str);
		str = t2.getText().toString();
		if(!isValidMobile(str)){
			t2.requestFocus();
			return false;
		}
		f.setMobile(str);
		str = t3.getText().toString();
		if(!isValidMail(str)){
			t3.requestFocus();
			return false;
		}
		f.setEmail(str);
		return true;
	}
	private void saveFeedback(){
		if(checkValid()){
			double rate = (double)rBar.getRating();
			String r =""+rate;
			f.setRating(r);
			if(Globals.feedbacklist==null){
				Globals.feedbacklist = new ArrayList<Feedback>();
			}
			Globals.feedbacklist.add(f);
			Type type = new TypeToken<ArrayList<Feedback>>(){}.getType();
			String json = new Gson().toJson(Globals.feedbacklist, type);
			DatabaseHelper.getInstance(getApplicationContext()).insertData(DatabaseHelper.TABLE_FEEDBACK, new DatabaseRow(json));
			AppSyncManager.getInstance(getApplicationContext()).postFeedback();
			showMsg("Feedback has been posted");
			finish();
		}else{
			showMsg("Enter currect email & mobile");
		}
	}
	private OnClickListener clk = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			saveFeedback();
		}
	};	
}
