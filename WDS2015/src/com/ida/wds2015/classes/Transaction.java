package com.ida.wds2015.classes;

public class Transaction {

	private String Order_Id;
	private String Amount;
	private String billing_cust_name;
	private String billing_cust_address;
	private String billing_cust_country;
	private String billing_cust_tel_No;
	private String billing_cust_email;
	private String billing_cust_city;
	private String billing_cust_state;
	private String billing_cust_zip;
	private String billing_cust_notes;
	private String txtpaymetpay;
	private String delegateid;
	private String transid;
	private String jsondata="";
	
	public String getOrder_Id() {
		return Order_Id;
	}
	public void setOrder_Id(String order_Id) {
		Order_Id = order_Id;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getBilling_cust_name() {
		return billing_cust_name;
	}
	public void setBilling_cust_name(String billing_cust_name) {
		this.billing_cust_name = billing_cust_name;
	}
	public String getBilling_cust_address() {
		return billing_cust_address;
	}
	public void setBilling_cust_address(String billing_cust_address) {
		this.billing_cust_address = billing_cust_address;
	}
	public String getBilling_cust_country() {
		return billing_cust_country;
	}
	public void setBilling_cust_country(String billing_cust_country) {
		this.billing_cust_country = billing_cust_country;
	}
	public String getBilling_cust_tel_No() {
		return billing_cust_tel_No;
	}
	public void setBilling_cust_tel_No(String billing_cust_tel_No) {
		this.billing_cust_tel_No = billing_cust_tel_No;
	}
	public String getBilling_cust_email() {
		return billing_cust_email;
	}
	public void setBilling_cust_email(String billing_cust_email) {
		this.billing_cust_email = billing_cust_email;
	}
	public String getBilling_cust_city() {
		return billing_cust_city;
	}
	public void setBilling_cust_city(String billing_cust_city) {
		this.billing_cust_city = billing_cust_city;
	}
	public String getBilling_cust_state() {
		return billing_cust_state;
	}
	public void setBilling_cust_state(String billing_cust_state) {
		this.billing_cust_state = billing_cust_state;
	}
	public String getBilling_cust_zip() {
		return billing_cust_zip;
	}
	public void setBilling_cust_zip(String billing_cust_zip) {
		this.billing_cust_zip = billing_cust_zip;
	}
	public String getBilling_cust_notes() {
		return billing_cust_notes;
	}
	public void setBilling_cust_notes(String billing_cust_notes) {
		this.billing_cust_notes = billing_cust_notes;
	}
	public String getTxtpaymetpay() {
		return txtpaymetpay;
	}
	public void setTxtpaymetpay(String txtpaymetpay) {
		this.txtpaymetpay = txtpaymetpay;
	}
	public String getDelegateid() {
		return delegateid;
	}
	public void setDelegateid(String delegateid) {
		this.delegateid = delegateid;
	}
	public String getTransid() {
		return transid;
	}
	public void setTransid(String transid) {
		this.transid = transid;
	}
	public String getJsondata() {
		return jsondata;
	}
	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
}
