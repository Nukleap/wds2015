package com.ida.wds2015;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class ActivityAddNote extends MainActivityCommon{

	EditText e1;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_note);
		setMaterialDesign();
		showBack();
		setBackAsClose();
		setMaterialTitle("Add Note");
		attachUI();
	}
	
	private void attachUIExtras(){
		Bundle b = getIntent().getExtras();
		if(b!=null){
			if(!b.getString("note").equalsIgnoreCase("null")){
				e1.setText(b.getString("note"));
				setMaterialTitle("Edit Note");
			}
		}
	}
	
	private void attachUI(){
		e1 = (EditText)findViewById(R.id.edittext_1);
		attachUIExtras();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_add_note, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == R.id.action_save_note){
			saveNote();
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void saveNote(){
		if(e1.getText().toString().length()>0){
			getIntent().putExtra("note", e1.getText().toString());
			setResult(RESULT_OK, getIntent());
			finish();
		}
	}
	
}
