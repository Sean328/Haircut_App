package com.haircut.haircut_alpha.entity;

import java.util.List;

public class BannerWrapper {
	
	public  List<BannerInfo> banner;
	
	public static class BannerInfo{
		private String Image;
		private String text;
		public String getImage() {
			return Image;
		}
		public void setImage(String image) {
			this.Image = image;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		
		@Override
		public String toString() {
			return "BannerInfo [image=" + Image + ", text=" + text + "]";
		}
		
	}

}
