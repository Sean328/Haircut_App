package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;


public class User {
	
	public  List<UserInfo> datas;
	
	public  class UserInfo implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String user_id;
		private String user_name;
		private String user_pwd;
		private String user_tel;
		private String user_payword;
		private String user_gender;
		private String user_address;
		private String user_img;
		private String user_signature;
		
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public String getUser_name() {
			return user_name;
		}
		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}
		public String getUser_pwd() {
			return user_pwd;
		}
		public void setUser_pwd(String user_pwd) {
			this.user_pwd = user_pwd;
		}
		public String getUser_tel() {
			return user_tel;
		}
		public void setUser_tel(String user_tel) {
			this.user_tel = user_tel;
		}
		public String getUser_payword() {
			return user_payword;
		}
		public void setUser_payword(String user_payword) {
			this.user_payword = user_payword;
		}
		
		
		public String getUser_gender() {
			return user_gender;
		}
		public void setUser_gender(String user_gender) {
			this.user_gender = user_gender;
		}
		public String getUser_address() {
			return user_address;
		}
		public void setUser_address(String user_address) {
			this.user_address = user_address;
		}
		public String getUser_img() {
			return user_img;
		}
		public void setUser_img(String user_img) {
			this.user_img = user_img;
		}
		public String getUser_signature() {
			return user_signature;
		}
		public void setUser_signature(String user_signature) {
			this.user_signature = user_signature;
		}
		
		@Override
		public String toString() {
			return "User [user_id=" + user_id + ", user_name=" + user_name
					+ ", user_pwd=" + user_pwd + ", user_tel=" + user_tel
					+ ", user_payword=" + user_payword + ", user_gender="
					+ user_gender + ", user_address=" + user_address
					+ ", user_img=" + user_img + ", user_signature="
					+ user_signature + "]";
		}
		
	}
	

}
