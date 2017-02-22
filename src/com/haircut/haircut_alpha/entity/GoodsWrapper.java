package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;


public class GoodsWrapper {
	
	public  List<GoodsInfo> datas;
	
	public  class GoodsInfo implements Serializable{
	
		private static final long serialVersionUID = 1L;
		
		
		private String goods_id;
		private String shop_id;
		private String goods_sort;
		private String gname;
		private String gprice;
		private String shop_name;
		private String gimage;
		
		public String getGood_id() {
			return goods_id;
		}
		public void setGood_id(String good_id) {
			this.goods_id = good_id;
		}
		public String getShop_id() {
			return shop_id;
		}
		public void setShop_id(String shop_id) {
			this.shop_id = shop_id;
		}
		public String getGood_sort() {
			return goods_sort;
		}
		public void setGood_sort(String good_sort) {
			this.goods_sort = good_sort;
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
			return "GoodsInfo [goods_id=" + goods_id + ", shop_id=" + shop_id
					+ ", goods_sort=" + goods_sort + ", gname=" + gname
					+ ", gprice=" + gprice + ", shop_name=" + shop_name
					+ ", gimage=" + gimage + "]";
		}
		
		
		
		
		
	
	}
	
}