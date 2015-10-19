package com.ida.wds2015.classes;

import com.google.gson.JsonObject;


public class Scan {
	
	private int delegate_id;
	private String name;
	private String countryname;
	private String statename;
	private String cityname;
	private String mobile;
	private String emailid;
	private String total_amount;
	private String Through;
	private JsonObject data;
	
	public int getDelegate_id() {
		return delegate_id;
	}
	public void setDelegate_id(int delegate_id) {
		this.delegate_id = delegate_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getThrough() {
		return Through;
	}
	public void setThrough(String through) {
		Through = through;
	}
	public JsonObject getData() {
		return data;
	}
	public void setData(JsonObject data) {
		this.data = data;
	}
	
	
}
