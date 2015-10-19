package com.ida.wds2015.classes;

public class FavoriteListItem {

	private boolean speaker;
	private Object tag;
	private boolean group = false;
	private String name = "";
	private boolean showNote = false;
	
	public FavoriteListItem(){
		
	}
	
	public FavoriteListItem(String name){
		this.group = true;
		this.name = name;
	}
	
	public boolean isSpeaker() {
		return speaker;
	}
	public void setSpeaker(boolean speaker) {
		this.speaker = speaker;
	}
	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isShowNote() {
		return showNote;
	}

	public void setShowNote(boolean showNote) {
		this.showNote = showNote;
	}
	
}
