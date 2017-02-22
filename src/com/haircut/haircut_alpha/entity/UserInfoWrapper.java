package com.haircut.haircut_alpha.entity;

import java.util.List;

public class UserInfoWrapper {
	
public List<UserInfo> userInfo;
	
	public static class UserInfo{
		//Í·Ïñ
		private String Head;
		//Ãû×Ö
		private String Name;
		//Ç©Ãû
		private String Signature;
		//µêÆÌ
		private String Shop;
		
		public String getHead(){
			return Head;
		}
		
		public void setHead(String head){
			this.Head = head;
		}
		
		public String getName(){
			return Name;
		}
		
		public void setName(String name){
			this.Name = name;
		}
		
		public String getSignature(){
			return Signature;
		}
		
		public void setSignature(String signature){
			this.Signature = signature;
		}
		
		
		
		public String getShop() {
			return Shop;
		}

		public void setShop(String shop) {
			Shop = shop;
		}

		
		@Override
		public String toString() {
			return "UserInfo [Head=" + Head + ", Name=" + Name + ", Signature="
					+ Signature + ", Shop=" + Shop + "]";
		}

		
	}

}
