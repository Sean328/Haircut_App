package com.haircut.haircut_alpha.entity;

import java.io.Serializable;
import java.util.List;


public class Comment {
	
	public  List<CommentsInfo> datas;
	
	public  class CommentsInfo implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		
		private String comments_id;
		private String goods_id;
		private String user_tel;
		private String name;
		private String comments;
		private String clevel;
		private String cpermoney;
		private String time;
		
		public String getComments_id() {
			return comments_id;
		}
		public void setComments_id(String comments_id) {
			this.comments_id = comments_id;
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
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getClevel() {
			return clevel;
		}
		public void setClevel(String clevel) {
			this.clevel = clevel;
		}
		public String getCpermoney() {
			return cpermoney;
		}
		public void setCpermoney(String cpermoney) {
			this.cpermoney = cpermoney;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		
		
		@Override
		public String toString() {
			return "Comment [comments_id=" + comments_id + ", goods_id=" + goods_id
					+ ", user_tel=" + user_tel + ", name=" + name + ", comments="
					+ comments + ", clevel=" + clevel + ", cpermoney=" + cpermoney
					+ ", time=" + time + "]";
		}
		
	}
	
	
	
	
}
