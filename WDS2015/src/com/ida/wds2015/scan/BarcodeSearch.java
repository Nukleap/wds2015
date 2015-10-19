package com.ida.wds2015.scan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.ida.wds2015.R;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class BarcodeSearch extends AppCompatActivity {
	Toolbar toolbar;
	Button button_from, button_to, button_date;
	Calendar dateAndTime = Calendar.getInstance();
	Calendar fromtime, totime;
	DateFormat formatTime = DateFormat.getTimeInstance();
	DateFormat formatDate = DateFormat.getDateInstance();
	String date_value, timeFrom_value, timeTo_value, startDate, endDate, date1;

	@Override
	public void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		setContentView(R.layout.barcode_search);
		fromtime = Calendar.getInstance();
		totime = (Calendar) fromtime.clone();
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Search List");
		toolbar.setNavigationIcon(R.drawable.ic_action_remove);
		attachUI();
		button_from.setOnClickListener(clk);
		button_from.setTag(1);
		button_to.setOnClickListener(clk);
		button_to.setTag(2);
		button_date.setOnClickListener(clk);
		button_date.setTag(3);
	}

	private void attachUI() {
		button_from = (Button) findViewById(R.id.barcode_time_buttonFrom);
		button_to = (Button) findViewById(R.id.barcode_time_buttonTo);
		button_date = (Button) findViewById(R.id.barcode_date_button);
	}

	TimePickerDialog.OnTimeSetListener time_from = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			fromtime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			fromtime.set(Calendar.MINUTE, minute);
			button_from.setText(formatTime.format(fromtime.getTime()));
		}
	};

	TimePickerDialog.OnTimeSetListener time_to = new TimePickerDialog.OnTimeSetListener() {

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			totime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			totime.set(Calendar.MINUTE, minute);
			button_to.setText(formatTime.format(totime.getTime()));
		}
	};

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			button_date.setText(formatDate.format(dateAndTime.getTime()));
			totime = (Calendar) fromtime.clone();
		}
	};

	private OnClickListener clk = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			if (position == 1) {
				new TimePickerDialog(BarcodeSearch.this, time_from,
						dateAndTime.get(Calendar.HOUR_OF_DAY),
						dateAndTime.get(Calendar.MINUTE), true).show();
			}
			if (position == 2) {
				new TimePickerDialog(BarcodeSearch.this, time_to,
						dateAndTime.get(Calendar.HOUR_OF_DAY),
						dateAndTime.get(Calendar.MINUTE), true).show();
			}
			if (position == 3) {
				new DatePickerDialog(BarcodeSearch.this, date,
						dateAndTime.get(Calendar.YEAR),
						dateAndTime.get(Calendar.DATE),
						dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
			}
		}

	};

	public void sendBroadcast(String startDate, String endDate) {
		Intent intent = new Intent("Intent_barcode");
		intent.putExtra("startTime", startDate);
		intent.putExtra("endTime", endDate);
		sendBroadcast(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_save_update_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) { // home is constant values which is used
										// by default for back button on
										// toolbar..
			finish();
		}
		if (id == R.id.action_save) {
			searchData();
		}
		return true;
	}

	private void searchData() {
		timeFrom_value = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
				.format(fromtime.getTime());
		timeTo_value = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
				.format(totime.getTime());
		date1 = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault())
				.format(dateAndTime.getTime());
		startDate = "" + date1 + timeFrom_value;
		endDate = "" + date1 + timeTo_value;

		if (button_from.getText().length() == 0
				|| button_to.getText().length() == 0
				|| button_date.getText().length() == 0) {
			Toast.makeText(getApplicationContext(),
					"Please Enter The Date And Time", Toast.LENGTH_LONG).show();
		} else {
			sendBroadcast(startDate, endDate);
			finish();
		}
	}
}
