package com.ida.wds2015.classes;

public class Exhibitor {
	
	private String blockstall;
	private String p_id;
	private String Exh_id;
	private String Name;
	private String CountryName;
	private String stateName;
	private String CityName;
	private String add1;
	private String add2;
	private String add3;
	private String email1;
	private String website;
	private String briefinfo;
	private String mobile;
	private String note;
	private boolean favorite = false;
	public String getBlockstall() {
		return blockstall;
	}
	public void setBlockstall(String blockstall) {
		this.blockstall = blockstall;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getExh_id() {
		return Exh_id;
	}
	public void setExh_id(String exh_id) {
		Exh_id = exh_id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCountryName() {
		return CountryName;
	}
	public void setCountryName(String countryName) {
		CountryName = countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getAdd3() {
		return add3;
	}
	public void setAdd3(String add3) {
		this.add3 = add3;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getBriefinfo() {
		return briefinfo;
	}
	public void setBriefinfo(String briefinfo) {
		this.briefinfo = briefinfo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(this.Exh_id.equalsIgnoreCase(((Exhibitor)o).getExh_id())){
			return true;
		}else{
			return false;
		}
	}
	
}
