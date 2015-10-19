package com.ida.wds2015.classes;

public class UserFee {
	
	private double amount;
	private int feeid;
	private String programName;
	private int CategoryId;
	private String categoryname;
	private int Deligate_Type_Id;
	private String Deligate_Type;
	private boolean group = false;
	private boolean selected = false;
	
	public UserFee(){
		
	}
	
	public UserFee(String Deligate_Type){
		this.group = true;
		this.Deligate_Type = Deligate_Type;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getFeeid() {
		return feeid;
	}
	public void setFeeid(int feeid) {
		this.feeid = feeid;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public int getCategoryId() {
		return CategoryId;
	}
	public void setCategoryId(int categoryId) {
		CategoryId = categoryId;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public int getDeligate_Type_Id() {
		return Deligate_Type_Id;
	}
	public void setDeligate_Type_Id(int deligate_Type_Id) {
		Deligate_Type_Id = deligate_Type_Id;
	}
	public String getDeligate_Type() {
		return Deligate_Type;
	}
	public void setDeligate_Type(String deligate_Type) {
		Deligate_Type = deligate_Type;
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean group) {
		this.group = group;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	

}
