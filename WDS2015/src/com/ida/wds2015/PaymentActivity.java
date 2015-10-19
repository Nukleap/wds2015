package com.ida.wds2015;

import org.apache.http.util.EncodingUtils;

import com.ida.manager.AppSyncManager;
import com.ida.wds2015.classes.Constants;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PaymentActivity extends MainActivityCommon {
	
	WebView webview;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		setMaterialDesign();
		setMaterialTitle("Payment");
		showBack();
		attachUI();
	}
	
	private void attachUI(){
		webview = (WebView)findViewById(R.id.common_web_view);
		try{
			WebSettings websettings = (WebSettings)webview.getSettings();
			websettings.setJavaScriptEnabled(true);
		}catch(Exception ex){
		}
		webview.setWebViewClient(new Callback());
		postData();
	}
	
	private void postData(){
		 byte[] post = EncodingUtils.getBytes(AppSyncManager.getInstance(getApplicationContext()).getPostData(), "BASE64");
		 webview.postUrl(Constants.URL_PAYMENT, post);
	}
	
	private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE. 

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return (false);
        }
	}
	
}
