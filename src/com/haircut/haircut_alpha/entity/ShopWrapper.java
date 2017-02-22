package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;



public class ShopWrapper {
	

	public  List<ShopInfo> datas;
	
	public  class ShopInfo implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String name;
		private String id;
		private String address;
		private String shop_intro;
		private String shop_img;
		private String shop_area;
		private String shop_distance;
		private String shop_level;
		private String shop_tel;
		private String shop_open_time;
		private String shop_fastcut;
		
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getShop_intro() {
			return shop_intro;
		}
		public void setShop_intro(String shop_intro) {
			this.shop_intro = shop_intro;
		}
		public String getShop_img() {
			return shop_img;
		}
		public void setShop_img(String shop_img) {
			this.shop_img = shop_img;
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
		
		public String getShop_level() {
			return shop_level;
		}
		public void setShop_level(String shop_level) {
			this.shop_level = shop_level;
		}
		public String getShop_tel() {
			return shop_tel;
		}
		public void setShop_tel(String shop_tel) {
			this.shop_tel = shop_tel;
		}
		public String getShop_open_time() {
			return shop_open_time;
		}
		public void setShop_open_time(String shop_open_time) {
			this.shop_open_time = shop_open_time;
		}
		public String getShop_fastcut() {
			return shop_fastcut;
		}
		public void setShop_fastcut(String shop_fastcut) {
			this.shop_fastcut = shop_fastcut;
		}
		@Override
		public String toString() {
			return "ShopInfo [name=" + name + ", id=" + id + ", address="
					+ address + ", shop_intro=" + shop_intro + ", shop_img="
					+ shop_img + ", shop_area=" + shop_area
					+ ", shop_distance=" + shop_distance + ", shop_level="
					+ shop_level + ", shop_tel=" + shop_tel
					+ ", shop_open_time=" + shop_open_time + ", shop_fastcut="
					+ shop_fastcut + "]";
		}
		
		
		
	}
}