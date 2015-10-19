package com.nukleap.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class StringUtils {
	public final static NumberFormat formatter = new DecimalFormat("#0.00");
	public static int MAX_INDEX; 
	public static int findString(String[] array,String str){
		int index = -1;
		for(int i=0;i<array.length;i++){
			if(array[i].toLowerCase(Locale.ENGLISH)==str.toLowerCase(Locale.ENGLISH)){
				index = i;
			}
		}
		return index;
	}
	public static String ellipsize(String data){
		if(data.length()>15){
			return data.substring(0, 12)+"...";
		}else{
			return data;
		}
	}
	public static String hiMsg(String data){
		String[] arr = data.split(" ",-1);
		if(arr.length>0){
			return "Hi, "+arr[0];
		}else{
			return "Hi, "+data;
		}
	}
	public static ArrayList<String> toArrayList(String[] array){
		ArrayList<String> arrayList = new ArrayList<String>();
		Collections.addAll(arrayList, array);
		return arrayList;
	}
	public static String[] toArray(ArrayList<String> arrayList){
		if(arrayList!=null){
		String[] array = new String[arrayList.size()];
		array = (String[])arrayList.toArray(array);
		return array;
		}else{
			return null;
		}
	}
	public static double toDouble(String data){
		if(data.length()>0){
			try{
				return Double.parseDouble(data);
			}catch(Exception e){
				return 0;
			}
		}else{
			return 0;
		}
	}
	public static double toDoubleAsNeg(String data){
		if(data.length()>0){
			try{
				return Double.parseDouble(data);
			}catch(Exception e){
				return -1;
			}
		}else{
			return -1;
		}
	}
	public static double[] toDoubleArray(String[] data){
		double[] doubleArray = new double[data.length];
		for(int i=0;i<data.length;i++){
			doubleArray[i] = toDouble(data[i]);
		}
		return doubleArray;
	}
	public static double[] toDoubleArrayAsNeg(ArrayList<String> data){
		double[] doubleArray = new double[data.size()];
		for(int i=0;i<data.size();i++){
			doubleArray[i] = toDoubleAsNeg(data.get(i));
		}
		return doubleArray;
	}
	public static double[] toDoubleArray(ArrayList<Double> data){
		double[] doubleArray = new double[data.size()];
		for(int i=0;i<data.size();i++){
			doubleArray[i] = data.get(i);
		}
		return doubleArray;
	}
	public static double getMax(double[] data){
		double max = 0.0;
		for(int i=0;i<data.length;i++){
			if(max<data[i]){
				MAX_INDEX = i;
				max = data[i];
			}
		}
		return max;
	}
	public static double getMax(ArrayList<Double> data){
		double max = 0.0;
		for(int i=0;i<data.size();i++){
			if(max<data.get(i)){
				max = data.get(i);
			}
		}
		return max;
	}
	public static String format(double value){
		return formatter.format(value);
	}
	public static double getMin(ArrayList<Double> data){
		double min = data.get(0);
		for(int i=1;i<data.size();i++){
			if(min>data.get(i)){
				min = data.get(i);
			}
		}
		return min;
	}
	public static double changeSign(double data){
		if(data<0){
			data = data*-1;
		}
		return data;
	}
	public static int getIndex(double[] data,double searchValue){
		int index = -1;
		for(int i=0;i<data.length;i++){
			if(searchValue<=data[i]){
				index = i;
				break;
			}
		}
		return index;
	}
	public static int getIndexNoBreak(double[] data,double searchValue){
		int index = -1;
		for(int i=0;i<data.length;i++){
			if(searchValue<=data[i]){
				index = i;
			}
		}
		return index;
	}
	public static int getExact(double[] data,double searchValue){
		int index = -1;
		for(int i=0;i<data.length;i++){
			if(searchValue==data[i]){
				index = i;
				break;
			}
		}
		return index;
	}
	public static double sumBetween(double[] data, ArrayList<Integer> indexes) {
        ArrayList<Double> sumData = new ArrayList<Double>();
        for (int i = indexes.get(0); i <= indexes.get(1); i++) {
            sumData.add(data[i]);
        }
        return sum(sumData);
    }
	public static double sumBetween(ArrayList<Double> data, ArrayList<Integer> indexes) {
        ArrayList<Double> sumData = new ArrayList<Double>();
        for (int i = indexes.get(0); i <= indexes.get(1); i++) {
            sumData.add(data.get(i));
        }
        return sum(sumData);
    }
	public static double sum(ArrayList<Double> data){
		double tot = 0;
		for(int i=0;i<data.size();i++){
			tot+=data.get(i);
		}
		return tot;
	}
	public static ArrayList<Double> makeSquList(double[] data)
    {
        ArrayList<Double> squareList = new ArrayList<Double>();
        for(double v : data)
        {
            squareList.add(v * v);
        }
        //data.ForEach(new Action<double>(delegate(double value) { return value * value; }));
        return squareList;
    }
	public static double[] makeProductList(double[] load, double[] disp)
    {
        double[] product = new double[load.length];
        for (int i = 0; i < load.length; i++)
        {
            product[i] = load[i] * disp[i];
        }
        return product;
    }
	public static String[] splitComma(String data,int option){
		return data.split(",",option);
	}
	public static String[] splitNewLine(String data,int option){
		return data.split("\n",option);
	}
	public static String join(String[] arr,String joiner){
		String data="";
		for(int i=0;i<arr.length;i++){
			if(i==arr.length-1){
				data+=arr[i];
			}else{
				data+=arr[i]+joiner;
			}
		}
		return data;
	}
	public static String urllink(String data){
		return data.replace(" ", "%20");
		
	}
	
}
