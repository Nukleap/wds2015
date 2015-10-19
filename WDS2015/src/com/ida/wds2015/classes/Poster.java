package com.ida.wds2015.classes;

public class Poster {

		private int articleid;
		private String title;
		private String author;
		private String Subject;
		private int Count;
		private String Aim;
		private String Materials;
		private String Result;
		private String Summary;
		private String email;
		private String mobile;
		private int position;
		private int image_count = 0;
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getSubject() {
			return Subject;
		}
		public void setSubject(String subject) {
			Subject = subject;
		}
		
		public String getAim() {
			return Aim;
		}
		public void setAim(String aim) {
			Aim = aim;
		}
		public String getMaterials() {
			return Materials;
		}
		public void setMaterials(String materials) {
			Materials = materials;
		}
		public String getResult() {
			return Result;
		}
		public void setResult(String result) {
			Result = result;
		}
		public String getSummary() {
			return Summary;
		}
		public void setSummary(String summary) {
			Summary = summary;
		}
		public int getCount() {
			return Count;
		}
		public void setCount(int count) {
			Count = count;
		}
		public int getArticleid() {
			return articleid;
		}
		public void setArticleid(int articleid) {
			this.articleid = articleid;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public int getImage_count() {
			return image_count;
		}
		public void setImage_count(int image_count) {
			this.image_count = image_count;
		}
}
