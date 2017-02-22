package com.haircut.haircut_alpha.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.LoginActivity;
import com.haircut.haircut_alpha.activity.secondary.LoginNull;
import com.haircut.haircut_alpha.activity.secondary.MyCart;
import com.haircut.haircut_alpha.activity.secondary.MyGoodsCollect;
import com.haircut.haircut_alpha.activity.secondary.MyOrders;
import com.haircut.haircut_alpha.activity.secondary.MySettings;
import com.haircut.haircut_alpha.activity.secondary.MyShopCollect;
import com.haircut.haircut_alpha.activity.secondary.NoKaquanActivity;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.User;
import com.haircut.haircut_alpha.entity.User.UserInfo;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.haircut.haircut_alpha.view.CircleTransform;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Fragment_user extends Fragment implements OnClickListener{
	
	private RelativeLayout mLogin;
	private TextView loginText;
	private ImageView touxiang;
	private Context context;
	private SharedUtils sh;
	
	private TextView userOrder;
	private TextView userCart;
	private TextView userKaquan;
	private TextView userSetting;
	private TextView userComment;
	private TextView user_goodsCollect;
	private TextView user_shopCollect;
	
	private TextView user_Location;
	
	private static String user_Number;
	
	private MyTask mTask;
	private String user_tel;
    private User wrapper;
    private List<UserInfo> UserInfos;
    public static UserInfo userInfo;
    private String imgURL;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_fragment_users, container,false);
		
		initView(view);
		
		return view;
	}
	
	private void initView(View view) {
		context = getActivity().getApplicationContext();
		sh = new SharedUtils(context);
		mLogin = (RelativeLayout) view.findViewById(R.id.login);
		loginText = (TextView) view.findViewById(R.id.user_index_login_text);
		touxiang = (ImageView) view.findViewById(R.id.user_index_login_image);
		
		userOrder = (TextView) view.findViewById(R.id.user_order);
		userCart = (TextView) view.findViewById(R.id.user_cart);
		userKaquan = (TextView) view.findViewById(R.id.user_kaquanbao);
		userSetting = (TextView) view.findViewById(R.id.user_setting);
		userComment = (TextView) view.findViewById(R.id.user_comment);
		user_goodsCollect = (TextView) view.findViewById(R.id.collect_goods);
		user_shopCollect = (TextView) view.findViewById(R.id.collect_shop);
		user_Location = (TextView) view.findViewById(R.id.user_location);
		
		mLogin.setOnClickListener(this);
		userOrder.setOnClickListener(this);
		userCart.setOnClickListener(this);
		user_shopCollect.setOnClickListener(this);
		user_goodsCollect.setOnClickListener(this);
		userSetting.setOnClickListener(this);
		userKaquan.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
				if(user_Number==""){
					Intent intentt = new Intent(getActivity(),LoginActivity.class);
					startActivityForResult(intentt, MyUtils.RequestLoginCode);
				}else{		
					Intent intent1 = new Intent(getActivity(),MySettings.class);
					startActivity(intent1);
				}
				
			break;
		case R.id.user_order:
			if(user_Number==""){
				Intent intent = new Intent(getActivity(),LoginNull.class);
				startActivity(intent);
			}else{
				handleOrder();
				Intent intent1 = new Intent(getActivity(),MyOrders.class);
				startActivity(intent1);
			}
			break;
		case R.id.user_cart:
			if(user_Number==""){
				Intent intent = new Intent(getActivity(),LoginNull.class);
				startActivity(intent);
			}else{
				handleCart();
				Intent intent2 = new Intent(getActivity(),MyCart.class);
				startActivity(intent2);
			}
			break;
			
		case R.id.collect_shop:
			if(user_Number==""){
				Intent intent = new Intent(getActivity(),LoginNull.class);
				startActivity(intent);
			}else{
				handleShopCollect();
				Intent intent3 = new Intent(getActivity(),MyShopCollect.class);
				startActivity(intent3);
			}
			break;
			
		case R.id.collect_goods:
			if(user_Number==""){
				Intent intent = new Intent(getActivity(),LoginNull.class);
				startActivity(intent);
			}else{
				handleCollect();
				Intent intent4 = new Intent(getActivity(),MyGoodsCollect.class);
				startActivity(intent4);
			}
			break;
			
		case R.id.user_setting:
			Intent intent = new Intent(getActivity(),MySettings.class);
			startActivity(intent);
			break;
		case R.id.user_kaquanbao:
			Intent intent110 = new Intent(getActivity(),NoKaquanActivity.class);
			startActivity(intent110);
		}
		
	}
	
	private void handleCollect() {
		final String mUser_tel = user_Number.toString();
		System.out.println("mUser_tel=="+mUser_tel);
		
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.Collect_Send_URL+"&mUser_tel="+mUser_tel, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
					}
				});
		
	}

	private void handleShopCollect() {
		final String mUser_tel = user_Number.toString();
		System.out.println("mUser_tel=="+mUser_tel);
		
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.ShopCollect_Send_URL+"&mUser_tel="+mUser_tel, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
					}
				});
		
	}

	private void handleOrder() {
		final String mUser_tel = user_Number.toString();
		System.out.println("mUser_tel=="+mUser_tel);
		
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.order_Send_URL+"&mUser_tel="+mUser_tel, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
					}
				});
		
	}

	private void handleCart() {
		final String mUser_tel = user_Number.toString();
		System.out.println("mUser_tel=="+mUser_tel);
		
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.Cart_Send_URL+"&mUser_tel="+mUser_tel, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
//						Toast.makeText(Fragment_user.this, "登陆失败", 0).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
					}
				});
		
	}

	
	
	@Override
	public void onStart() {
		super.onStart();
		Map<String,String> data = sh.read();
		user_Number = data.get("username");
		String user_city;
		Bitmap bm;
		user_city = data.get("address");
		bm = sh.getBitmapFromSharedPreferences();
		
		if(data.get("username") != ""){
			 loginText.setText(data.get("username")); 
		}
		if(user_city!=""){
			user_Location.setText("城市所在地： "+user_city);
		}
		if(bm!=null){
			touxiang.setImageBitmap(bm);
		}
		
		if(bm==null && user_Number!=""){
				//注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常 
			
				user_tel = data.get("username");
				mTask = new MyTask();  
				mTask.execute(); 
				
			}
		}
	
	private class MyTask extends AsyncTask<String, Void, String> {
		
		private URL url;
		private HttpURLConnection conn;

		@Override
		protected String doInBackground(String... arg0) {
			
			

			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			try {
				System.out.println("user_tel="+user_tel);
				url = new URL(CONSTS.User_Info_URL+"&user_tel="+user_tel);
				conn = (HttpURLConnection) url.openConnection();
				
				buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while ((line = buffer.readLine())!=null) {
					sb.append(line);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(buffer != null)
						buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			Gson gson = new Gson();
			wrapper = new User();
			wrapper= gson.fromJson(result, User.class);
			
			UserInfos = wrapper.datas;
			
			System.out.println("能否在登陆界面拿到数据？"+wrapper.datas);
			userInfo = UserInfos.get(0);
			
			imgURL = userInfo.getUser_img();
			
			Picasso.with(context).load(userInfo.getUser_img()).transform(new CircleTransform()).into(touxiang);
			
		}
		
	}

		
} 


	
	
