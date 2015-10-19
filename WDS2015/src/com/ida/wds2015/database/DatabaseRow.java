package com.ida.wds2015.database;

public class DatabaseRow {

	private long id;
	private String jsondata;
	private int deleted = 0;
	private String savedat;
	
	public DatabaseRow(){
		
	}
	public DatabaseRow(String jsondata){
		this.deleted = 0;
		this.jsondata = jsondata;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getSavedat() {
		return savedat;
	}
	public void setSavedat(String savedat) {
		this.savedat = savedat;
	}
	
}
