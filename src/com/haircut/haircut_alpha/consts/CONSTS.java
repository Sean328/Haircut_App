package com.haircut.haircut_alpha.consts;

public class CONSTS {
	
	public static final String HOST = "http://119.29.183.12:8080/haircut-server";
	
	public static final String APIKey = "035dd8a46b01b51c7b995be1a2ac45bf";
	public static final String APISecret = "AQ1O-R6BskBeFTyBfHdr-RdUXudnjFvj";
	
	//��������
	public static final String City_Data_URL = HOST+"/api/city";
	
	//�����б�
	public static final String Shop_Data_URL = HOST+"/api/shop";
	public static final String Shop2_Data_URL = HOST+"/api/shop2";
	public static final String Shop3_Data_URL = HOST+"/api/shop3";
	
	
	//��ҳbannerͼ
	public static final String Home_Banner_URL = "http://119.29.183.12/haircut/banner.json";
	
	//�û�ע��
	public static final String User_Register_URL = HOST+"/api/user?flag=register";
	//�û���¼
	public static final String User_Login_URL = HOST+"/api/user?flag=login";
	//�����û���
	public static final String User_NickName_URL = HOST+"/api/user?flag=username";
	//����ͷ��
	public static final String User_Image_URL = HOST+"/api/user?flag=userimg";
	//�����Ա�
	public static final String User_Gender_URL = HOST+"/api/user?flag=usergender";
	//���µ�ַ
	public static final String User_Address_URL = HOST+"/api/user?flag=useraddress";
	//��ȡ�û���Ϣ
	public static final String User_Info_URL = HOST+"/api/user?flag=userInfo";
	
	//��Ʒչʾ����
	public static final String Goods_URL = HOST+"/api/goods?flag=read";
	//��Ʒ��ѯ����
	public static final String Goods_Send_URL = HOST+"/api/goods?flag=send";
	
	//��Ʒ�ղ�����
	public static final String Collect_URL = HOST+"/api/collect?flag=collect";
	//��Ʒ��������
	public static final String Collect_Return_URL = HOST+"/api/collect?flag=read";
	//����ղ���Ʒ����ҳ�洫ֵ����
	public static final String Collect_Send_URL = HOST+"/api/collect?flag=send";
	
	//�����ղ�����
	public  static final String ShopCollect_URL= HOST+"/api/shopcollect?flag=submit";
	//�ղص��̷�������
	public  static final String ShopCollect_Return_URL= HOST+"/api/shopcollect?flag=read";
	//������ﳵ����ҳ�洫ֵ����
	public static final String ShopCollect_Send_URL = HOST+"/api/shopcollect?flag=send";
	
	//��ӹ��ﳵ����
	public static final String Cart_URL = HOST+"/api/cart?flag=cart";
	//���ﳵ��������
	public static final String Cart_Return_URL = HOST+"/api/cart?flag=read";
	//������ﳵ����ҳ�洫ֵ����
	public static final String Cart_Send_URL = HOST+"/api/cart?flag=send";
	
	//�¶�������
	public static final String Order_Submit_URL= HOST+"/api/order?flag=order";
	//������������
	public static final String Order_Return_URL= HOST+"/api/order?flag=read";
	//�������������ҳ�洫ֵ����
	public static final String order_Send_URL = HOST+"/api/order?flag=send";
	
	
	//��������
	public static final String Comments_URL = HOST+"/api/comment?flag=read";
	public static final String Comments_Submit_URL = HOST+"/api/comment?flag=submit";

}
