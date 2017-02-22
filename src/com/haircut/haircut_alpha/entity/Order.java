package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;

import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;

public class Order {
	
	public  List<OrderInfo> datas;
	
	public  class OrderInfo implements Serializable{
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String orders_id;
		private String user_tel;
		private String goods_id;
		private String orders_time;
		private String orders_price;
		
		private String gname;
		private String gprice;
		private String shop_name;
		private String gimage;
		
		
		public String getOrders_id() {
			return orders_id;
		}
		public void setOrders_id(String orders_id) {
			this.orders_id = orders_id;
		}
		public String getUser_tel() {
			return user_tel;
		}
		public void setUser_tel(String user_tel) {
			this.user_tel = user_tel;
		}
		public String getGoods_id() {
			return goods_id;
		}
		public void setGoods_id(String goods_id) {
			this.goods_id = goods_id;
		}
		public String getOrders_time() {
			return orders_time;
		}
		public void setOrders_time(String orders_time) {
			this.orders_time = orders_time;
		}
		public String getOrders_price() {
			return orders_price;
		}
		public void setOrders_price(String orders_price) {
			this.orders_price = orders_price;
		}
		public String getGname() {
			return gname;
		}
		public void setGname(String gname) {
			this.gname = gname;
		}
		public String getGprice() {
			return gprice;
		}
		public void setGprice(String gprice) {
			this.gprice = gprice;
		}
		public String getShop_name() {
			return shop_name;
		}
		public void setShop_name(String shop_name) {
			this.shop_name = shop_name;
		}
		public String getGimage() {
			return gimage;
		}
		public void setGimage(String gimage) {
			this.gimage = gimage;
		}
		@Override
		public String toString() {
			return "OrderInfo [orders_id=" + orders_id + ", user_tel="
					+ user_tel + ", goods_id=" + goods_id + ", orders_time="
					+ orders_time + ", orders_price=" + orders_price
					+ ", gname=" + gname + ", gprice=" + gprice
					+ ", shop_name=" + shop_name + ", gimage=" + gimage + "]";
		}
		
		
	
	}
	
}
