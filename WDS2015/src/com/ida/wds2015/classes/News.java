package com.ida.wds2015.classes;

public class News {

	private long id;
	private String fromDate;
	private String to_date;
	private String news;
	private String deleted;
	private String created_date;
	private String data;
	private boolean toggeled = true;
	private int pos;
	private boolean notified;
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(id==((News)o).getId()){
			return true;
		}else{
			return false;
		}
	}
	
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public boolean isToggeled() {
		return toggeled;
	}
	public void setToggeled(boolean toggeled) {
		this.toggeled = toggeled;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}
}
