package com.ida.wds2015.classes;

public class SelectedFee {
	
	//{"root":{"subroot":{"CategoryId":"7","fee_id":"2","fee_name":"162","fee_amount":"2","total_amount":"14"},
		//{"CategoryId":"7","fee_id":"2","fee_name":"162","fee_amount":"2","total_amount":"14"}}}

	private String CategoryId;
	private String fee_id;
	private String fee_name;
	private String fee_amount;
	private String total_amount;
	private String tran_status;
	private String Through;
	private boolean group = false;
	
	public SelectedFee(){}
	
	public SelectedFee(String name){
		this.group = true;
		this.fee_name = name;
	}
	
	public String getCategoryId() {
		return CategoryId;
	}
	public void setCategoryId(String categoryId) {
		CategoryId = categoryId;
	}
	public String getFee_id() {
		return fee_id;
	}
	public void setFee_id(String fee_id) {
		this.fee_id = fee_id;
	}
	public String getFee_name() {
		return fee_name;
	}
	public void setFee_name(String fee_name) {
		this.fee_name = fee_name;
	}
	public String getFee_amount() {
		return fee_amount;
	}
	public void setFee_amount(String fee_amount) {
		this.fee_amount = fee_amount;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getTran_status() {
		return tran_status;
	}
	public void setTran_status(String tran_status) {
		this.tran_status = tran_status;
	}
	public String getThrough() {
		return Through;
	}
	public void setThrough(String through) {
		Through = through;
	}
	
	public boolean isPaid(){
		if(tran_status.contains("Pending")){
			return false;
		}else{
			return true;
		}
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean group) {
		this.group = group;
	}
	
}
