package com.ida.wds2015.classes;

public class Order {
	private String Order_Id;
	private double Amount;
	private String billing_cust_name;
	private String billing_cust_address;
	private String billing_cust_country;
	private double billing_cust_tel_No;
	private String billing_cust_email;
	private String billing_cust_city;
	private String billing_cust_state;
	private double billing_cust_zip;
	private String billing_cust_notes;
	private double txtpaymetpay;
	private double delegateid;
	private double transid;
	
	public String getOrder_Id() {
		return Order_Id;
	}
	public void setOrder_Id(String order_Id) {
		Order_Id = order_Id;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
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
	public double getBilling_cust_tel_No() {
		return billing_cust_tel_No;
	}
	public void setBilling_cust_tel_No(double billing_cust_tel_No) {
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
	public double getBilling_cust_zip() {
		return billing_cust_zip;
	}
	public void setBilling_cust_zip(double billing_cust_zip) {
		this.billing_cust_zip = billing_cust_zip;
	}
	public String getBilling_cust_notes() {
		return billing_cust_notes;
	}
	public void setBilling_cust_notes(String billing_cust_notes) {
		this.billing_cust_notes = billing_cust_notes;
	}
	public double getTxtpaymetpay() {
		return txtpaymetpay;
	}
	public void setTxtpaymetpay(double txtpaymetpay) {
		this.txtpaymetpay = txtpaymetpay;
	}
	public double getDelegateid() {
		return delegateid;
	}
	public void setDelegateid(double delegateid) {
		this.delegateid = delegateid;
	}
	public double getTransid() {
		return transid;
	}
	public void setTransid(double transid) {
		this.transid = transid;
	}
}
