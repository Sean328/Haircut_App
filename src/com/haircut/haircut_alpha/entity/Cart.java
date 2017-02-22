package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;


public class Cart {
	
	public  List<CartInfo> datas;
	
	public  class CartInfo implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String cart_id;
		private String user_tel;
		private String goods_id;
		
		
		private String gname;
		private String gprice;
		private String shop_name;
		private String gimage;
		
		
		public String getCart_id() {
			return cart_id;
		}
		public void setCart_id(String cart_id) {
			this.cart_id = cart_id;
		}
		public String getGoods_id() {
			return goods_id;
		}
		public void setGoods_id(String goods_id) {
			this.goods_id = goods_id;
		}
		public String getUser_tel() {
			return user_tel;
		}
		public void setUser_tel(String user_tel) {
			this.user_tel = user_tel;
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
			return "CartInfo [cart_id=" + cart_id + ", user_tel=" + user_tel
					+ ", goods_id=" + goods_id + ", gname=" + gname
					+ ", gprice=" + gprice + ", shop_name=" + shop_name
					+ ", gimage=" + gimage + "]";
		}
		
		
		
	}
	
	
	

}
