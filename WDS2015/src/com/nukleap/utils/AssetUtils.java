package com.nukleap.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class AssetUtils {
	public static String readFile(Context context,String fileName){
		AssetManager manager = context.getAssets();
		try{
			InputStream input = manager.open(fileName);
			int size = input.available();
	          byte[] buffer = new byte[size];
	          input.read(buffer);
	          input.close();

	          // byte buffer into a string
	          String text = new String(buffer);
	          return text;
		}catch(Exception e){
			return "";
		}
	}
	public static Bitmap readBitmap(Context context,String strName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
}
