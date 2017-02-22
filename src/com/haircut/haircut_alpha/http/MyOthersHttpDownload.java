package com.haircut.haircut_alpha.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.GoodsAdapter;
import com.haircut.haircut_alpha.adapter.MyOtherAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Cart;
import com.haircut.haircut_alpha.entity.Collect;
import com.haircut.haircut_alpha.entity.Order.OrderInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper;
import com.haircut.haircut_alpha.entity.Order;
import com.haircut.haircut_alpha.entity.ShopCollect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class MyOthersHttpDownload  extends AsyncTask<String, Void, String>{

	private URL url;
	private HttpURLConnection conn;
	private ListView listView;
	private Context context;
	private String MyURL;
	private Order wrapper;
	private Cart cart_wrapper;
	private ShopCollect shopCollect_wrap;
	private Collect collect_wrapper;
	private OrderInfo mDatalist;
	
	
	public MyOthersHttpDownload(ListView listView, Context context,String MyURL) {
		this.listView = listView;
		this.context = context;
		this.MyURL = MyURL;
	}
	
	
	@Override
	protected String doInBackground(String... arg0) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		
		try {
			url = new URL(MyURL);
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
		Log.d("main", ""+result);
		
		if(MyURL==CONSTS.Order_Return_URL){
			wrapper = new Order();
			wrapper= gson.fromJson(result, Order.class);
			if(wrapper!=null){
					MyOtherAdapter adapter = new MyOtherAdapter(context, wrapper.datas,MyURL,1);
					listView.setAdapter(adapter);
				}
		}else if(MyURL==CONSTS.Cart_Return_URL){
			
			cart_wrapper = new Cart();
			cart_wrapper= gson.fromJson(result, Cart.class);
			if(cart_wrapper!=null){
					MyOtherAdapter adapter = new MyOtherAdapter(context,MyURL,cart_wrapper.datas,2);
					listView.setAdapter(adapter);
				}
		}else if(MyURL==CONSTS.ShopCollect_Return_URL){
			shopCollect_wrap = new ShopCollect();
			shopCollect_wrap= gson.fromJson(result, ShopCollect.class);
			if(shopCollect_wrap!=null){
					MyOtherAdapter adapter = new MyOtherAdapter(MyURL,shopCollect_wrap.datas,context,3);
					listView.setAdapter(adapter);
				}
		}else if(MyURL == CONSTS.Collect_Return_URL){
			collect_wrapper = new Collect();
			collect_wrapper= gson.fromJson(result, Collect.class);
			if(collect_wrapper!=null){
					MyOtherAdapter adapter = new MyOtherAdapter(4,MyURL,collect_wrapper.datas,context);
					listView.setAdapter(adapter);
				}
		}

	}

}
