package com.nukleap.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class PostUtils {
	
	public static String genGetData(HashMap<String, String> params){
		ArrayList<String> data = new ArrayList<String>();
		String[] arr = new String[params.size()];
		arr = params.keySet().toArray(arr);
		for(int i=0;i<params.size();i++){
			data.add(arr[i]+"="+params.get(arr[i]));
		}
		return StringUtils.urllink(StringUtils.join(StringUtils.toArray(data),"&"));
	}
	public static String getData(String data){
		return data.replace(" ", "%20");
	}
}
