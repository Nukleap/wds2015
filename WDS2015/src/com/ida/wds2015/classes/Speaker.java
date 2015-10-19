package com.ida.wds2015.classes;

import com.google.gson.JsonObject;

public class Speaker {
	
	private int SubProgramId;
	private int SrNo;
	private int Speakerid;
	private String speakename;
	private String Qualification;
	private String Designation;
	private JsonObject data;
	private String programName;
	private boolean favorite = false;
	private String note="";
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if(Speakerid == ((Speaker)o).getSpeakerid()){
			return true;
		}else{
			return false;
		}
		//return super.equals(o);
	}
	
	public int getSubProgramId() {
		return SubProgramId;
	}
	public void setSubProgramId(int subProgramId) {
		SubProgramId = subProgramId;
	}
	public int getSrNo() {
		return SrNo;
	}
	public void setSrNo(int srNo) {
		SrNo = srNo;
	}
	public int getSpeakerid() {
		return Speakerid;
	}
	public void setSpeakerid(int speakerid) {
		Speakerid = speakerid;
	}
	public String getSpeakename() {
		return speakename;
	}
	public void setSpeakename(String speakename) {
		this.speakename = speakename;
	}
	public String getQualification() {
		return Qualification;
	}
	public void setQualification(String qualification) {
		Qualification = qualification;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public JsonObject getData() {
		return data;
	}
	public void setData(JsonObject data) {
		this.data = data;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

}
