package com.ida.wds2015.classes;

public class OrderContract {
	
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
    
    public OrderContract(){
    	
    }
    
    public OrderContract(User user){
    	this.countryid = user.getCountryid();
    	this.country = user.getCountry();
    	this.state = user.getState();
    	this.statename = user.getStatename();
    	this.city = user.getCity();
    	this.cityname = user.getCityname();
    	this.Password = user.getPassword();
    	this.mobile = user.getMobile();
    	this.add1 = user.getAdd1();
    	this.emailid = user.getEmailid();
    	this.Delegate_type = user.getDelegate_type();
    	this.name = user.getName();
    	this.fname = user.getFname();
    	this.Lname = user.getLname();
    	this.Title = user.getTitle();
    	this.jsondata = user.getJsondata();
    	this.ZipCode = user.getZipCode();
    	this.deleagteid = user.getDeleagteid();
    }
    
	public String getCountryid() {
		return countryid;
	}
	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getDelegate_type() {
		return Delegate_type;
	}
	public void setDelegate_type(String delegate_type) {
		Delegate_type = delegate_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public String getZipCode() {
		return ZipCode;
	}
	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}
	
	
            
            

}
