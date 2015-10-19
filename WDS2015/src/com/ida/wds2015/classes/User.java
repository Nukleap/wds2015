package com.ida.wds2015.classes;

import java.util.StringTokenizer;

public class User {

	private String countryid = "1";
	private String country = "";
	private String state="0";
	private String statename = "";
	private String city="0";
	private String cityname="";
	private String Password;
    private String mobile;
    private String add1;
    private String emailid;
    private String Delegate_type;
    private String name;
    private String fname="";
    private String Lname="";
    private String Title="";
    private String jsondata;
    private String ZipCode;
    private String deleagteid="0"; 
    private boolean registered = false;
	
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		StringTokenizer token = new StringTokenizer(name.trim()," ");
		this.name = token.nextToken();
		if(token.hasMoreTokens()){
			this.fname = token.nextToken();
		}
		if(token.hasMoreTokens()){
			this.Lname = token.nextToken();
		}
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
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getDelegate_type() {
		return Delegate_type;
	}
	public void setDelegate_type(String delegate_type) {
		Delegate_type = delegate_type;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return Lname;
	}
	public void setLname(String lname) {
		Lname = lname;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	public String getDeleagteid() {
		return deleagteid;
	}
	public void setDeleagteid(String deleagteid) {
		this.deleagteid = deleagteid;
	}
	public boolean isRegistered() {
		return registered;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	   
		        
		     
		         
		        
	
}
