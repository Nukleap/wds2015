package com.ida.manager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ida.utils.InternetUtils;
import com.ida.wds2015.AppController;
import com.ida.wds2015.Globals;
import com.ida.wds2015.classes.BarcodePost;
import com.ida.wds2015.classes.BarcodePostRoot;
import com.ida.wds2015.classes.BarcodePostSubroot;
import com.ida.wds2015.classes.Constants;
import com.ida.wds2015.classes.Exhibitor;
import com.ida.wds2015.classes.ExhibitorRoot;
import com.ida.wds2015.classes.ExhibitorSubroot;
import com.ida.wds2015.classes.Feed;
import com.ida.wds2015.classes.Feedback;
import com.ida.wds2015.classes.FeedbackRoot;
import com.ida.wds2015.classes.FeedbackSubroot;
import com.ida.wds2015.classes.InsertBarcode;
import com.ida.wds2015.classes.News;
import com.ida.wds2015.classes.NewsRoot;
import com.ida.wds2015.classes.OrderContract;
import com.ida.wds2015.classes.Poster;
import com.ida.wds2015.classes.PosterRoot;
import com.ida.wds2015.classes.ProgramCategory;
import com.ida.wds2015.classes.ProgramCategoryRoot;
import com.ida.wds2015.classes.Programme;
import com.ida.wds2015.classes.ProgrammeRoot;
import com.ida.wds2015.classes.Scan;
import com.ida.wds2015.classes.ScanRoot;
import com.ida.wds2015.classes.SelectedFeeRoot;
import com.ida.wds2015.classes.Speaker;
import com.ida.wds2015.classes.SpeakerRoot;
import com.ida.wds2015.classes.SpeakerSubroot;
import com.ida.wds2015.classes.Transaction;
import com.ida.wds2015.classes.TransactionRoot;
import com.ida.wds2015.classes.User;
import com.ida.wds2015.classes.UserFee;
import com.ida.wds2015.classes.UserFeeRoot;
import com.ida.wds2015.classes.WdsNotification;
import com.ida.wds2015.database.DatabaseHelper;
import com.ida.wds2015.database.DatabaseRow;
import com.ida.wds2015.scan.BarcodeDatabaseHelper;
import com.nukleap.utils.PostUtils;

public class AppSyncManager {

	private static AppSyncManager mInstance = null;
	private Context context;
	private HashMap<String, String> hashmap = null;

	public AppSyncManager(Context context) {
		this.context = context;
	}

	public void notiWelcome() {
		IDANotificationManager.showNotification(context);
	}

	public static synchronized AppSyncManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AppSyncManager(context);
		}
		return mInstance;
	}

	public void saveSpeaker() {
		Type type = new TypeToken<SpeakerRoot>() {
		}.getType();
		SpeakerSubroot subroot = new SpeakerSubroot();
		subroot.setSubroot(Globals.speakers);
		SpeakerRoot root = new SpeakerRoot();
		root.setRoot(subroot);
		DatabaseRow row = new DatabaseRow();
		row.setJsondata(new Gson().toJson(root, type));
		DatabaseHelper.getInstance(context).insertData(
				DatabaseHelper.TABLE_SPEAKER, row);
	}

	public void saveExhibitor() {
		Type type = new TypeToken<ExhibitorRoot>() {
		}.getType();
		ExhibitorSubroot subroot = new ExhibitorSubroot();
		subroot.setSubroot(Globals.exlist);
		ExhibitorRoot root = new ExhibitorRoot();
		root.setRoot(subroot);
		DatabaseRow row = new DatabaseRow();
		row.setJsondata(new Gson().toJson(root, type));
		DatabaseHelper.getInstance(context).insertData(
				DatabaseHelper.TABLE_EXHIBITOR, row);
	}

	public void init() {
		DatabaseRow row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_EXHIBITOR);
		if (row != null) {
			Type type = new TypeToken<ExhibitorRoot>() {
			}.getType();
			ExhibitorRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.exlist = root.getRoot().getSubroot();
			Log.i("Database", "Data found...." + Globals.exlist.size());
		}
		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_CATEGORY);
		if (row != null) {
			Type type = new TypeToken<ProgramCategoryRoot>() {
			}.getType();
			ProgramCategoryRoot root = new Gson().fromJson(row.getJsondata(),
					type);
			Globals.prCatlist = root.getRoot().getSubroot();
			Log.i("Database", "Data found...." + Globals.prCatlist.size());
		}
		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_PROGRAM);
		if (row != null) {
			Type type = new TypeToken<ProgrammeRoot>() {
			}.getType();
			ProgrammeRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.programmes = root.getRoot().getSubroot();
			Log.i("Database", "Data found...." + Globals.programmes.size());
		}
		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_SPEAKER);
		if (row != null) {
			Type type = new TypeToken<SpeakerRoot>() {
			}.getType();
			SpeakerRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.speakers = root.getRoot().getSubroot();
			Log.i("Database", "Data found...." + Globals.speakers.size());
		}

		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_POSTER);
		if (row != null) {
			Type type = new TypeToken<PosterRoot>() {
			}.getType();
			PosterRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.posterList = root.getRoot().getSubroot();
			Log.i("Database", "Poster found...." + Globals.posterList.size());
		}

		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_USER_PROFILE);
		if (row != null) {
			Type type = new TypeToken<User>() {
			}.getType();
			User root = new Gson().fromJson(row.getJsondata(), type);
			Globals.user = root;
			Log.i("Database", "User found...." + root.getName());
		}

		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_NEWS);
		if (row != null) {
			Type type = new TypeToken<NewsRoot>() {
			}.getType();
			NewsRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.news = root.getRoot().getSubroot();
			Log.i("News", "New found...." + Globals.news.size());
		}

		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_SCAN);
		if (row != null) {
			Type type = new TypeToken<ScanRoot>() {
			}.getType();
			ScanRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.scanList = root.getRoot().getSubroot();
			Log.i("Scan", "Data found...." + Globals.scanList.size());
		}
		
		row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_FEEDBACK);
		if (row != null) {
			Type type = new TypeToken<ArrayList<Feedback>>() {
			}.getType();
			Globals.feedbacklist = new Gson().fromJson(row.getJsondata(), type);
			Log.i("Feedback", "Data found...."+Globals.feedbacklist.size());
		}else{
			Log.i("Feedback", "No Data found...");
		}

		Globals.transactions = DatabaseHelper.getInstance(context)
				.getTransactionList();

		if (Globals.exlist != null && Globals.prCatlist != null
				&& Globals.programmes != null && Globals.speakers != null) {
			Globals.downloaded = true;
		}
	}
	
	private void checkNewsInDB(){
		DatabaseRow row = DatabaseHelper.getInstance(context).getData(
				DatabaseHelper.TABLE_NEWS);
		if (row != null) {
			Type type = new TypeToken<NewsRoot>() {
			}.getType();
			NewsRoot root = new Gson().fromJson(row.getJsondata(), type);
			Globals.news = root.getRoot().getSubroot();
			Log.i("checkNewsInDB", "Available News: " + Globals.news.size());
		}else{
			Globals.news = null;
		}
	}

	private String filterJson(String json) {
		json = json.trim();
		json = json.substring(1, json.length() - 1);
		json = json.replace("\\", "");
		return json;
	}

	private String filterJson(String json, boolean escalation) {
		json = json.trim();
		json = json.substring(1, json.length() - 1);
		if (escalation) {
			json = json.replace("\\", "");
		}
		return json;
	}

	public void startOtherReq() {
		getPoster();
	}

	public void requestExhibitor() {
		if (InternetUtils.getInstance(context).available()) {
			AppController.getInstance(context).cancelPendingRequest(
					Constants.REQUEST_TAG_GETEXHIBITOR);
			String url = Constants.URL_GETEXHIBITOR;
			Log.i("REQUEST", "Sending..." + url);
			url = url.replace(" ", "%20");
			StringRequest req = new StringRequest(Request.Method.GET, url,
					new Response.Listener<String>() {
						public void onResponse(String response) {
							attachExhibitors(filterJson(response));
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError error) {
						}
					});
			AppController.getInstance(context).addToRequestQueue(req,
					Constants.REQUEST_TAG_GETEXHIBITOR);
		}
	}

	private void attachExhibitors(String json) {
		Type type = new TypeToken<ExhibitorRoot>() {
		}.getType();
		ExhibitorRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.exlist == null) {
				Globals.exlist = new ArrayList<Exhibitor>();
			}
			ArrayList<Exhibitor> newlist = reflectExhibitor(root.getRoot()
					.getSubroot());

			Globals.exlist.clear();
			Globals.exlist.addAll(newlist); // Globals.exlist.addAll(root.getRoot().getSubroot());

			root.getRoot().setSubroot(newlist);
			json = new Gson().toJson(root, type);

			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(
					DatabaseHelper.TABLE_EXHIBITOR, row);
			getProgramCategory();
		}
	}

	private ArrayList<Exhibitor> reflectExhibitor(ArrayList<Exhibitor> newlist) {
		for (int i = 0; i < newlist.size(); i++) {
			if (Globals.exlist.contains(newlist.get(i))) {
				int index = Globals.exlist.indexOf(newlist.get(i));
				if (index != -1) {
					Exhibitor oldex = Globals.exlist.get(index);
					if (oldex != null) {
						if (oldex.isFavorite()) {
							newlist.get(i).setFavorite(oldex.isFavorite());
						}
						if (oldex.getNote() != null) {
							if (oldex.getNote().length() > 0)
								newlist.get(i).setNote("" + oldex.getNote());
						}
					}
				}
			}
		}
		return newlist;
	}

	private void getProgramCategory() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_GETPROGRAMCATEGORY);
		String url = Constants.URL_GETPROGRAMCATEGORY;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						attachProgramCategory(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_GETPROGRAMCATEGORY);
	}

	private void attachProgramCategory(String json) {
		Type type = new TypeToken<ProgramCategoryRoot>() {
		}.getType();
		ProgramCategoryRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.prCatlist == null) {
				Globals.prCatlist = new ArrayList<ProgramCategory>();
			}
			Globals.prCatlist.clear();
			Globals.prCatlist.addAll(root.getRoot().getSubroot());
			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(
					DatabaseHelper.TABLE_CATEGORY, row);
			getProgramme();
		}
	}

	private void getProgramme() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_PROGRAMME);
		String url = Constants.URL_GETPROGRAM;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						attachProgramme(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});

		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_PROGRAMME);
	}

	private void attachProgramme(String json) {
		Type type = new TypeToken<ProgrammeRoot>() {
		}.getType();
		ProgrammeRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.programmes == null) {
				Globals.programmes = new ArrayList<Programme>();
			}
			Globals.programmes.clear();
			Globals.programmes.addAll(root.getRoot().getSubroot());
			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(
					DatabaseHelper.TABLE_PROGRAM, row);
			getSpeaker();
		}
	}

	private void getSpeaker() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_SPEAKER);
		String url = Constants.URL_GETALLSPEAKER;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						attachSpeaker(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_SPEAKER);
	}

	private void attachSpeaker(String json) {
		Type type = new TypeToken<SpeakerRoot>() {
		}.getType();
		SpeakerRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.speakers == null) {
				Globals.speakers = new ArrayList<Speaker>();
			}
			ArrayList<Speaker> newlist = reflectSpeaker(root.getRoot()
					.getSubroot());

			Globals.speakers.clear();
			Globals.speakers.addAll(newlist);

			root.getRoot().setSubroot(newlist);
			json = new Gson().toJson(root, type);

			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(
					DatabaseHelper.TABLE_SPEAKER, row);
			context.sendBroadcast(new Intent(Constants.BROADCAST_REQUEST_ALL));
		}
	}

	private ArrayList<Speaker> reflectSpeaker(ArrayList<Speaker> newlist) {
		for (int i = 0; i < newlist.size(); i++) {
			if (Globals.speakers.contains(newlist.get(i))) {
				int index = Globals.speakers.indexOf(newlist.get(i));
				if (index != -1) {
					Speaker oldex = Globals.speakers.get(index);
					if (oldex != null) {
						if (oldex.isFavorite()) {
							newlist.get(i).setFavorite(oldex.isFavorite());
						}
						if (oldex.getNote() != null) {
							if (oldex.getNote().length() > 0)
								newlist.get(i).setNote("" + oldex.getNote());
						}
					}
				}
			}
		}
		return newlist;
	}

	public void getFeeDetails() {
		if (InternetUtils.getInstance(context).available()) {
			AppController.getInstance(context).cancelPendingRequest(
					Constants.REQUEST_TAG_FEE_DETAILS);
			String url = Constants.URL_FEE_REGISTRATION;
			Log.i("REQUEST", "Sending..." + url);
			url = url.replace(" ", "%20");
			StringRequest req = new StringRequest(Request.Method.GET, url,
					new Response.Listener<String>() {
						public void onResponse(String response) {
							attachFeeDetails(filterJson(response));
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError error) {

						}
					});
			AppController.getInstance(context).addToRequestQueue(req,
					Constants.REQUEST_TAG_FEE_DETAILS);
		} else {
			context.sendBroadcast(new Intent(Constants.BROADCAST_USER_FEE));
		}
	}

	private void attachFeeDetails(String json) {
		Type type = new TypeToken<UserFeeRoot>() {
		}.getType();
		UserFeeRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.userfees == null) {
				Globals.userfees = new ArrayList<UserFee>();
			}
			Globals.userfees.clear();
			Globals.userfees.addAll(root.getRoot().getSubroot());
			context.sendBroadcast(new Intent(Constants.BROADCAST_USER_FEE));
		}
	}

	public void postUserFeeDatails() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_POST_USER_FEE);
		String url = Constants.URL_POST_FEE_USER_SAVE;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						try {
							Log.i("Response", "" + response);
							postedStatus(filterJson(response));
						} catch (Exception e) {
							Log.i("Getting Response", "" + e.toString());
						}

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						postedStatus("");
					}
				}) {
			public String getBodyContentType() {
				return "application/json; charset=" + getParamsEncoding();
			}

			public byte[] getBody() throws AuthFailureError {
				try {
					Type type = new TypeToken<OrderContract>() {
					}.getType();
					OrderContract order = new OrderContract(Globals.user);
					String data = new Gson().toJson(order, type);
					return data.getBytes(getParamsEncoding());

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_POST_USER_FEE);
	}

	public void getPoster() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_POSTER);
		String url = Constants.URL_GETALLPOSTER;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						attachPoster(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_POSTER);
	}

	private void attachPoster(String json) {
		try {
			Type type = new TypeToken<PosterRoot>() {
			}.getType();
			PosterRoot root = new Gson().fromJson(json, type);
			if (root.getRoot().getSubroot() != null) {
				if (Globals.posterList == null) {
					Globals.posterList = new ArrayList<Poster>();
				}
				Globals.posterList.clear();
				Globals.posterList.addAll(root.getRoot().getSubroot());
				DatabaseRow row = new DatabaseRow(json);
				DatabaseHelper.getInstance(context).insertData(
						DatabaseHelper.TABLE_POSTER, row);

				Log.i("Poster Data", "Poster Data found");
				context.sendBroadcast(new Intent(
						Constants.BROADCAST_REQUEST_POSTER));
			}
		} catch (Exception e) {
			Log.i("Error", "" + e.toString());
		}
	}

	private void postedStatus(final String json) {
		if (json.length() > 0) {

			try {
				Globals.user.setRegistered(true);
				hashmap = new HashMap<String, String>();
				JSONObject obj = new JSONObject(json).getJSONObject("root")
						.getJSONObject("subroot");
				Iterator<String> keys = obj.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					hashmap.put(key, obj.getString(key));
					Log.i("JSON DATA", "" + key + ":" + obj.getString(key));
				}
				Type type = new TypeToken<TransactionRoot>() {
				}.getType();
				TransactionRoot rt = new Gson().fromJson(json, type);
				rt.getRoot().getSubroot()
						.setJsondata(Globals.user.getJsondata());
				String newjson = new Gson().toJson(rt, type);
				DatabaseRow row = new DatabaseRow(newjson);
				DatabaseHelper.getInstance(context).insertTransaction(row);
				Globals.transactions = DatabaseHelper.getInstance(context)
						.getTransactionList();
				Globals.user.setDeleagteid(rt.getRoot().getSubroot()
						.getDelegateid());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		context.sendBroadcast(new Intent(Constants.BROADCAST_POST_USER_FEE));
	}

	public String getPostData() {
		if (hashmap != null) {
			return PostUtils.genGetData(hashmap);
		} else {
			return "";
		}
	}

	public void postPosterVoteDatails(final Poster poster) {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_POST_POSTER_VOTE);
		String url = Constants.URL_POST_VOTE_POSTER_SAVE;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						try {
							Log.i("Response", "" + response);
							voted(filterJson(response));
						} catch (Exception e) {
							Log.i("Getting Response", "" + e.toString());
						}
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						voted("");
					}
				}) {
			public String getBodyContentType() {
				return "application/json; charset=" + getParamsEncoding();
			}

			public byte[] getBody() throws AuthFailureError {
				try {
					Type type = new TypeToken<Poster>() {
					}.getType();
					String data = new Gson().toJson(poster, type);
					return data.getBytes(getParamsEncoding());

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_POST_POSTER_VOTE);
	}

	private void voted(final String json) {
		int status = 0;
		try {
			JSONObject root = new JSONObject(json);
			JSONObject statusobj = root.getJSONObject("root").getJSONObject("subroot");
			String str = statusobj.getString("status");
			if (!str.contains("Already Exist")) {
				status = 1;
			}
		} catch (Exception e) {

		}
		Intent intent = new Intent(Constants.BROADCAST_POST_VOTE);
		intent.putExtra("status", status);
		context.sendBroadcast(intent);
		Log.i("Vote", "" + json);
	}

	public void makeStartNewsData(){
		if(!ServiceTools.isServiceRunning(ServiceManager.class.getName(), context)){
			Intent intent = new Intent(context,ServiceManager.class);
			context.startService(intent);
		}
	}
	
	public void getNewsDetails() {
		if(InternetUtils.getInstance(context).available()){
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_NEWS_DETAILS);
		String url = Constants.URL_NEWS;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						Log.i("News data", "news data");
						attachNewsDetails(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {

					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_NEWS_DETAILS);
		}
	}

	private void attachNewsDetails(String json) {
		Log.i("Getting News data", "news data");
		Type type = new TypeToken<NewsRoot>() {
		}.getType();
		NewsRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			checkNewsInDB();
			if (Globals.news == null) {
				Globals.news = new ArrayList<News>();
			}
			if(root.getRoot().getSubroot().size()!=Globals.news.size()&&root.getRoot().getSubroot().size()>0){
				WdsNotification noti = new WdsNotification();
				News n = root.getRoot().getSubroot().get(0);
				noti.setTitle(""+n.getNews());
				noti.setDesc(""+n.getData());
				IDANotificationManager.showNotification(context, noti);
			}
			Globals.news.clear();
			Globals.news.addAll(root.getRoot().getSubroot());
			context.sendBroadcast(new Intent(Constants.BROADCAST_NEWS));
			Log.i("News data", "news data" + Globals.news.size());
			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(
					DatabaseHelper.TABLE_NEWS, row);
		}
	}

	public void getPaymentDetails(String delid) {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_GET_TRANS_DETAIL);
		String url = Constants.URL_GET_TRANS_DETAIL + delid;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						Log.i("Payment data", "Payment data");
						attachPaymentDetails(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {

					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_GET_TRANS_DETAIL);
	}

	private void attachPaymentDetails(String json) {
		Log.i("Payment data", "Payment data");
		Type type = new TypeToken<SelectedFeeRoot>() {
		}.getType();
		SelectedFeeRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			Globals.payments = root.getRoot().getSubroot();
			context.sendBroadcast(new Intent(Constants.BROADCAST_TRANS_DETAIL));
		}
	}

	public void getScan() {
		AppController.getInstance(context).cancelPendingRequest(
				Constants.REQUEST_TAG_SCAN);
		String url = Constants.URL_GETALLSCAN;
		Log.i("REQUEST", "Sending..." + url);
		url = url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					public void onResponse(String response) {
						attachScan(filterJson(response));
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});
		AppController.getInstance(context).addToRequestQueue(req,
				Constants.REQUEST_TAG_SCAN);
	}

	private void attachScan(String json) {
		Type type = new TypeToken<ScanRoot>() {
		}.getType();
		ScanRoot root = new Gson().fromJson(json, type);
		if (root.getRoot().getSubroot() != null) {
			if (Globals.scanList == null) {
				Globals.scanList = new ArrayList<Scan>();
			}

			Globals.scanList.clear();
			Globals.scanList.addAll(root.getRoot().getSubroot());
			Log.i("Scan Data", "scan data " + Globals.scanList.size());
			DatabaseRow row = new DatabaseRow(json);
			DatabaseHelper.getInstance(context).insertData(DatabaseHelper.TABLE_SCAN, row);
			context.sendBroadcast(new Intent(Constants.BROADCAST_SCAN));
		}
	}

	public void updateSyncList(ArrayList<BarcodePost> list, String json) {
		try {
			JSONObject root = new JSONObject(json);
			String status = root.getJSONObject("root").getJSONObject("subroot")
					.getString("status");
			if (status.contains("1")) {
				BarcodeDatabaseHelper.getInstance(context).updateScanList(list);
				Log.i("DB_STATUS", "Saved");
				context.sendBroadcast(new Intent(Constants.BROADCAST_POSTING_COMPLETE));
			}
		} catch (Exception e) {
			Log.i("DB_STATUS", "" + e.toString());
		}

	}

	public void postBarcodeDatails(String id, String byname) {
		final ArrayList<BarcodePost> list = BarcodeDatabaseHelper.getInstance(
				context).getSyncNeedBarcodes(id, byname);
		if (list.size() > 0) {
			AppController.getInstance(context).cancelPendingRequest(
					Constants.REQUEST_TAG_POST_SCAN_RESULT);
			String url = Constants.URL_POST_SCAN_RESULT;
			Log.i("REQUEST", "Sending..." + url);
			url = url.replace(" ", "%20");
			StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
						public void onResponse(String response) {
							try {
								Log.i("POST RESPONSE SUCCESS", "" + response);
								updateSyncList(list, filterJson(response));
							} catch (Exception e) {
								Log.i("POST RESPONSE ERROR", "" + e.toString());
							}
						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError error) {
							Log.i("POST RESPONSE ERROR", "" + error.toString());
						}
					}) {
				public String getBodyContentType() {
					return "application/json; charset=" + getParamsEncoding();
				}

				public byte[] getBody() throws AuthFailureError {
					try {
						BarcodePostSubroot subroot = new BarcodePostSubroot();
						subroot.setSubroot(list);

						BarcodePostRoot root = new BarcodePostRoot();
						root.setRoot(subroot);
						Type type = new TypeToken<BarcodePostRoot>() {
						}.getType();
						String jsondata = new Gson().toJson(root, type);
						InsertBarcode insertBarcode = new InsertBarcode();
						insertBarcode.setScandata(jsondata);

						Type intype = new TypeToken<InsertBarcode>() {
						}.getType();
						String indata = new Gson()
								.toJson(insertBarcode, intype);

						Log.i("POST RESPONSE DATA",
								"" + insertBarcode.getScandata());
						return indata.getBytes(getParamsEncoding());
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
			};
			AppController.getInstance(context).addToRequestQueue(req,Constants.REQUEST_TAG_POST_SCAN_RESULT);
		}
	}
	
	public void postFeedback(){
		
		AppController.getInstance(context).cancelPendingRequest(Constants.REQUEST_TAG_FEEDBACK_DETAILS);
		String url=Constants.URL_POST_FEEDBACK;
		url=url.replace(" ", "%20");
		StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				try{
					postedData(filterJson(response));
					Log.i("FEEDBACK RESPONSE", ""+filterJson(response));
				}catch(Exception e){	
				}
			}
		}, new Response.ErrorListener(){
			public void onErrorResponse(VolleyError error) {
				Log.i("POST RESPONSE ERROR", "" + error.toString());
			}
		}){
			public String getBodyContentType() {
				return "application/json; charset=" + getParamsEncoding();
			}
			@Override
			public byte[] getBody() throws AuthFailureError {
				// TODO Auto-generated method stub
				try{
					FeedbackSubroot subroot = new FeedbackSubroot();
					subroot.setSubroot(Globals.feedbacklist);
					FeedbackRoot root = new FeedbackRoot();
					root.setRoot(subroot);
					Type type = new TypeToken<FeedbackRoot>(){}.getType();
					String data =new Gson().toJson(root, type);
					Feed feed = new Feed();
					feed.setData(data);
					type = new TypeToken<Feed>(){}.getType();
					data =new Gson().toJson(feed, type);
					Log.i("FEEDBACK", "feedback"+data);
					return data.getBytes(getParamsEncoding());
				}catch(Exception e){
					e.printStackTrace();
				}
				return null;
			}
		};
		AppController.getInstance(context).addToRequestQueue(req, Constants.REQUEST_TAG_FEEDBACK_DETAILS);
	}	
	
	private void postedData(String json){
		try {
			JSONObject root = new JSONObject(json);
			String res = root.getJSONObject("root").getString("subroot");
			if(res.contains("1")){
				Log.i("FEEDBACK", "Response...."+res);
				DatabaseHelper.getInstance(context).deleteFeedbackData();
				Globals.feedbacklist.clear();
			}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
