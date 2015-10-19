package com.ida.wds2015;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
	private static AppController mInstance;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private static Context mCtx;
	private static final String TAG = AppController.class.getName();
	public final static int RETRY_MILI = 10000;
	
	private AppController(Context context){
		mCtx = context;
		mRequestQueue = getRequestQueue();
		mImageLoader = new ImageLoader(mRequestQueue,
				new ImageLoader.ImageCache() {
					
				private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(20);
			
					@Override
					public void putBitmap(String url, Bitmap bitmap) {
						// TODO Auto-generated method stub
						Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 250, 250, false);
						cache.put(url, bitmap);
					}
					
					@Override
					public Bitmap getBitmap(String url) {
						// TODO Auto-generated method stub
						return cache.get(url);
					}
				});
	}
	
	public static synchronized AppController getInstance(Context context){
		if(mInstance==null){
			mInstance = new AppController(context);
		}
		return mInstance;
	}
	
	public RequestQueue getRequestQueue(){
		if(mRequestQueue==null){
			mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	} 
	
	public <T> void addToRequestQueue(Request<T> req){
		getRequestQueue().add(req);
	}
	
	public ImageLoader getImageLoader(){
		return mImageLoader;
	}
	
	public <T> void addToRequestQueue(Request<T>req,String tag){
		req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
		req.setRetryPolicy(new DefaultRetryPolicy(RETRY_MILI, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		getRequestQueue().add(req);
	}
	
	public void cancelPendingRequest(Object tag){
		if(tag!=null){
			getRequestQueue().cancelAll(tag);
		}
	}
}
