package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;


public class ShopCollect {
	
	public  List<ShopCollectInfo> datas;
	
	public  class ShopCollectInfo implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String shopcollect_id;
		private String user_tel;
		private String shop_id;
		
		private String shop_name;
		private String shop_area;
		private String shop_distance;
		private String shop_img;
		private String shop_level;
		
		public String getShopcollect_id() {
			return shopcollect_id;
		}
		public void setShopcollect_id(String shopcollect_id) {
			this.shopcollect_id = shopcollect_id;
		}
		public String getUser_tel() {
			return user_tel;
		}
		public void setUser_tel(String user_tel) {
			this.user_tel = user_tel;
		}
		public String getShop_id() {
			return shop_id;
		}
		public void setShop_id(String shop_id) {
			this.shop_id = shop_id;
		}
		public String getShopname() {
			return shop_name;
		}
		public void setShopname(String name) {
			this.shop_name = name;
		}
		public String getShop_area() {
			return shop_area;
		}
		public void setShop_area(String shop_area) {
			this.shop_area = shop_area;
		}
		public String getShop_distance() {
			return shop_distance;
		}
		public void setShop_distance(String shop_distance) {
			this.shop_distance = shop_distance;
		}
		public String getShop_img() {
			return shop_img;
		}
		public void setShop_img(String shop_img) {
			this.shop_img = shop_img;
		}
		public String getShop_level() {
			return shop_level;
		}
		public void setShop_level(String shop_level) {
			this.shop_level = shop_level;
		}
		@Override
		public String toString() {
			return "ShopCollectInfo [shopcollect_id=" + shopcollect_id
					+ ", user_tel=" + user_tel + ", shop_id=" + shop_id
					+ ", shop_name=" + shop_name + ", shop_area=" + shop_area
					+ ", shop_distance=" + shop_distance + ", shop_img="
					+ shop_img + ", shop_level=" + shop_level + "]";
		}
		
		
	
	}
	
	
}
