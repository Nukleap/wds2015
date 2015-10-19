package com.ida.wds2015.scan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ida.manager.AppSyncManager;
import com.ida.utils.InternetUtils;
import com.ida.wds2015.Globals;
import com.ida.wds2015.MainActivityCommon;
import com.ida.wds2015.R;
import com.ida.wds2015.classes.BarcodePost;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Scan;

public class BarcodeScaneList extends MainActivityCommon {
	private static int RESULT_LOAD_IMAGE = 1;
	ListView listview;
	ArrayList<Barcode> list;
	BarcodeArrayAdapter adapter;
	Intent intent;
	String str;
	StringBuilder sb;
	int searchlistsize, scanelistsize;
	View view;
	Button button;
	MenuItem itemsearch, itemnew;
	TextView tv;
	BarcodePost barcode;

	public void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		setContentView(R.layout.barcode_display);
		setMaterialDesign();
		showBack();
		attachUI();
		list = new ArrayList<Barcode>();
		adapter = new BarcodeArrayAdapter(this, list);
		listview.setAdapter(adapter);
		registerReceiver(receiver, new IntentFilter("Intent_barcode"));
		registerReceiver(networkrec, new IntentFilter("Network_Receiver"));
		registerReceiver(receiverposted, new IntentFilter(
				Constants.BROADCAST_POSTING_COMPLETE));
		registerReceiver(receiverscan, new IntentFilter(
				Constants.BROADCAST_SCAN));
		scaneList();
		checkNetworkConnection();
	}

	private void attachUI() {
		listview = (ListView) findViewById(R.id.barcode_list);
		view = getLayoutInflater().inflate(R.layout.activity_header, listview,
				false);
		listview.addHeaderView(view);
		button = (Button) view.findViewById(R.id.button_sync);
		button.setVisibility(View.GONE);
		if (InternetUtils.getInstance(getApplicationContext()).available()) {
			AppSyncManager.getInstance(getApplicationContext()).getScan();
		}
	}

	private void checkNetworkConnection() {
		boolean connection = InternetUtils.getInstance(getApplicationContext())
				.available();
		if (connection == false) {
			button.setVisibility(View.GONE);
		}
		if (connection == true) {
			button.setVisibility(View.VISIBLE);
			button.setOnClickListener(clk);
		}
	}

	private BroadcastReceiver networkrec = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			checkNetworkConnection();
		}
	};

	private BroadcastReceiver receiverscan = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}
	};

	private BroadcastReceiver receiverposted = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			showMsg("Posted Successfully");
		}
	};

	public void scaneList() {
		list.clear();
		for (Barcode bar : BarcodeDatabaseHelper.getInstance(
				getApplicationContext()).getScaneDetails1()) {
			list.add(bar);
		}
		adapter.notifyDataSetChanged();
		setScaneResultOnTollbar();
	}

	private void setScaneResultOnTollbar() {
		scanelistsize = list.size();
		tv = (TextView) view.findViewById(R.id.header_textview);
		if (scanelistsize == 0) {
			tv.setText("No Results");
		} else {
			tv.setText("" + scanelistsize + " Results");
		}
	}

	private OnClickListener clk = new OnClickListener() {
		@Override
		public void onClick(View v) {
			postBarcodeData();
		}
	};

	private void postBarcodeData() {
		if (InternetUtils.getInstance(getApplicationContext()).available()) {
			Toast.makeText(getApplicationContext(), "Please wait...",
					Toast.LENGTH_SHORT).show();
			String byname = getResources().getString(R.string.by_admin);
			int adminid = Integer.parseInt(getResources().getString(
					R.string.admin_userid));
			if (Globals.currentuser != adminid) {
				byname = getResources().getString(R.string.by_exhibitor);
			}
			Log.i("BY TROUGH", "" + Globals.currentuser + " " + byname + "");
			AppSyncManager.getInstance(getApplicationContext())
					.postBarcodeDatails("" + Globals.currentuser, byname);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.search_activity, menu);
		getMenuInflater().inflate(R.menu.refresh_activity, menu);
		itemsearch = menu.findItem(R.id.action_search);
		itemnew = menu.findItem(R.id.action_new);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_new:

			action();
			break;
		case R.id.action_search:
			Intent searchIntent = new Intent(this, BarcodeSearch.class);
			startActivity(searchIntent);
			break;
		case R.id.action_refresh:
			scaneList();
			getSupportActionBar().setTitle("Scane List");
			itemsearch.setVisible(true);
			itemnew.setVisible(true);
			Toast.makeText(getApplicationContext(), "Data Refresh Successful",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_new1:

			showInputDailogBox();
		}
		return true;
	}

	private void showInputDailogBox() {
		Constants.position = 1;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Enter ID:");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);
		builder.setPositiveButton("Check",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						str = "" + input.getText().toString();
						if (str.length() > 0) {
							saveResult(str);
							scaneList();
							showInfo();
						} else {
							// Toast.makeText(getApplicationContext(),
							// "Please check the ID",
							// Toast.LENGTH_SHORT).show();
							showMsg("Please check the ID");
							return;
						}
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						return;
					}
				});
		builder.show();
	}

	private void action() {
		Constants.position = -1;
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();
	}

	private void showInfo() {
		boolean found = true;
		if (Globals.scanList != null) {
			for (Scan s : Globals.scanList) {
				if (s.getDelegate_id() == Integer.parseInt(str)) {
					JsonObject obj = s.getData();
					if (obj != null) {
						String data = "\r\n";
						try {
							JsonArray detailarr = obj.getAsJsonArray("detail");
							for (JsonElement item : detailarr) {
								JsonObject itemobj = item.getAsJsonObject();
								Log.i("FEE NAME", "" + itemobj.get("fee_name"));
								data += "" + itemobj.get("fee_name") + "\r\n";
							}
						} catch (Exception e) {
							JsonObject detail = obj.getAsJsonObject("detail");
							Log.i("FEE NAME", "" + detail.get("fee_name"));
							data += "" + detail.get("fee_name");
						}
						found = true;
						showScanDetail(s, data);
					}
					break;
				}
			}
		}
		if (!found) {
			showMsg("No data found");
			return;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		try {
			IntentResult scanningResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, intent);
			if (scanningResult != null) {
				str = "" + scanningResult.getContents();
				Log.i("Scanning Result", "" + str);
				if (str.length() > 0 && !str.equals("null")) {
					Log.i("Scanning Result", "" + str);
					saveResult(str);
					scaneList();
					showInfo();
				}
			} else {
				showMsg("Please Scan the Barcode");
				return;
			}
		} catch (Exception e) {
		}
	}

	private void showScanDetail(Scan s, String data) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setMessage("ID " + str + "\r\n" + s.getName() + "\r\n" + data
				+ "\r\n" + s.getCityname() + "\r\n" + s.getStatename() + "\r\n"
				+ s.getCountryname());
		builder1.setCancelable(true);
		builder1.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		builder1.setPositiveButton("Scan Next",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (Constants.position != -1) {
							showInputDailogBox();
						} else {
							action();
						}
					}
				});
		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String startTime = intent.getStringExtra("startTime");
			String endTime = intent.getStringExtra("endTime");
			searchList(startTime, endTime);
		}
	};

	public void searchList(String startTime, String endTime) {
		list.clear();
		for (Barcode bar : BarcodeDatabaseHelper.getInstance(
				getApplicationContext()).getSearchDetails(startTime, endTime)) {
			list.add(bar);
		}
		adapter.notifyDataSetChanged();
		searchlistsize = list.size();
		getSupportActionBar().setTitle("Search List");
		itemsearch.setVisible(false);
		itemnew.setVisible(false);
		// toolbar.setBackgroundColor(222222);
		setSearchResultAndTimeOnTollbar(startTime, endTime);
	}

	private void setSearchResultAndTimeOnTollbar(String startTime,
			String endTime) {
		String startDateAndTime = startTime;
		String endDateAndTime = endTime;
		tv = (TextView) view.findViewById(R.id.header_textview);

		Calendar calstartdateandtime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			calstartdateandtime.setTime(sdf.parse(startDateAndTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calenddateandtime = Calendar.getInstance();
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		try {
			calenddateandtime.setTime(sm.parse(endDateAndTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		startDateAndTime = new SimpleDateFormat("ddMMM hh:mm a ",
				Locale.getDefault()).format(calstartdateandtime.getTime());
		endDateAndTime = new SimpleDateFormat(" hh:mm a ", Locale.getDefault())
				.format(calenddateandtime.getTime());
		if (searchlistsize == 0) {
			tv.setText("No Results\n" + startDateAndTime + " To "
					+ endDateAndTime);
		} else {
			tv.setText("" + searchlistsize + "Results\n" + startDateAndTime
					+ " To " + endDateAndTime);
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		unregisterReceiver(networkrec);
		unregisterReceiver(receiverposted);
		unregisterReceiver(receiverscan);
		super.onDestroy();
	};

	private void saveResult(String str) {
		long id = BarcodeDatabaseHelper.getInstance(getApplicationContext())
				.saveScaneResult(str);
		int index = -1;
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getBarcode().equals(str)) {
					Log.i("SCAN RESULT", "Position : " + i);
					index = i;
				}
			}
		}
		if (index != -1) {
			list.get(index).setContent(
					BarcodeDatabaseHelper.getInstance(getApplicationContext())
							.getScaneDetails(str));
			adapter.notifyDataSetChanged();
		} else {
			Barcode br = new Barcode();
			br.setId(id);
			br.setBarcode(str);
			br.setDatetime(BarcodeDatabaseHelper.getInstance(
					getApplicationContext()).getSavedTime());
			br.setContent(BarcodeDatabaseHelper.getInstance(
					getApplicationContext()).getScaneDetails(str));
			list.add(0, br);
			adapter.notifyDataSetChanged();
		}
		// showMsg("Barcode value saved Successfully");
	}
}
