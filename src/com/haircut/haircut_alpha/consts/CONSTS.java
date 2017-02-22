package com.haircut.haircut_alpha.consts;

public class CONSTS {
	
	public static final String HOST = "http://119.29.183.12:8080/haircut-server";
	
	public static final String APIKey = "035dd8a46b01b51c7b995be1a2ac45bf";
	public static final String APISecret = "AQ1O-R6BskBeFTyBfHdr-RdUXudnjFvj";
	
	//城市数据
	public static final String City_Data_URL = HOST+"/api/city";
	
	//店铺列表
	public static final String Shop_Data_URL = HOST+"/api/shop";
	public static final String Shop2_Data_URL = HOST+"/api/shop2";
	public static final String Shop3_Data_URL = HOST+"/api/shop3";
	
	
	//首页banner图
	public static final String Home_Banner_URL = "http://119.29.183.12/haircut/banner.json";
	
	//用户注册
	public static final String User_Register_URL = HOST+"/api/user?flag=register";
	//用户登录
	public static final String User_Login_URL = HOST+"/api/user?flag=login";
	//更新用户名
	public static final String User_NickName_URL = HOST+"/api/user?flag=username";
	//更新头像
	public static final String User_Image_URL = HOST+"/api/user?flag=userimg";
	//更新性别
	public static final String User_Gender_URL = HOST+"/api/user?flag=usergender";
	//更新地址
	public static final String User_Address_URL = HOST+"/api/user?flag=useraddress";
	//获取用户信息
	public static final String User_Info_URL = HOST+"/api/user?flag=userInfo";
	
	//商品展示链接
	public static final String Goods_URL = HOST+"/api/goods?flag=read";
	//商品查询链接
	public static final String Goods_Send_URL = HOST+"/api/goods?flag=send";
	
	//商品收藏链接
	public static final String Collect_URL = HOST+"/api/collect?flag=collect";
	//商品返回链接
	public static final String Collect_Return_URL = HOST+"/api/collect?flag=read";
	//点击收藏商品进入页面传值链接
	public static final String Collect_Send_URL = HOST+"/api/collect?flag=send";
	
	//店铺收藏链接
	public  static final String ShopCollect_URL= HOST+"/api/shopcollect?flag=submit";
	//收藏店铺返回链接
	public  static final String ShopCollect_Return_URL= HOST+"/api/shopcollect?flag=read";
	//点击购物车进入页面传值链接
	public static final String ShopCollect_Send_URL = HOST+"/api/shopcollect?flag=send";
	
	//添加购物车链接
	public static final String Cart_URL = HOST+"/api/cart?flag=cart";
	//购物车返回链接
	public static final String Cart_Return_URL = HOST+"/api/cart?flag=read";
	//点击购物车进入页面传值链接
	public static final String Cart_Send_URL = HOST+"/api/cart?flag=send";
	
	//下订单链接
	public static final String Order_Submit_URL= HOST+"/api/order?flag=order";
	//订单返回链接
	public static final String Order_Return_URL= HOST+"/api/order?flag=read";
	//点击订单车进入页面传值链接
	public static final String order_Send_URL = HOST+"/api/order?flag=send";
	
	
	//评论链接
	public static final String Comments_URL = HOST+"/api/comment?flag=read";
	public static final String Comments_Submit_URL = HOST+"/api/comment?flag=submit";

}
