package com.haircut.haircut_alpha.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.GoodsAdapter;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper;
import com.haircut.haircut_alpha.entity.ShopWrapper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class GoodsHttpDownload extends AsyncTask<String, Void, String>{
	
	private URL url;
	private HttpURLConnection conn;
	private ListView listView;
	private Context context;
	private String page;
	private String shopId;
	public static GoodsWrapper wrapper;

	public GoodsHttpDownload(ListView listView, Context context,String page) {
		this.listView = listView;
		this.context = context;
		this.page = page;
	}
	
	public GoodsHttpDownload(){
		
	}
	
	//请求店铺内的商品数据传输
	public GoodsHttpDownload(Context context,String shopId,ListView listView) {
		this.listView = listView;
		this.context = context;
		this.shopId = shopId;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			url = new URL(CONSTS.Goods_URL);
			
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
		try {

			Gson gson = new Gson();
			
			wrapper = new GoodsWrapper();
			wrapper= gson.fromJson(result, GoodsWrapper.class);
			
			System.out.println("能否在这拿到数据？"+wrapper.datas);
			
			if(wrapper!=null){
				if(page!=null){
					GoodsAdapter adapter = new GoodsAdapter(context, wrapper.datas,page);
					listView.setAdapter(adapter);
				}else{
					GoodsAdapter adapter = new GoodsAdapter(shopId,context,wrapper.datas);
					listView.setAdapter(adapter);
					setListViewHeightBasedOnChildren(listView);
				}
				
				
			}
			

			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		  ListAdapter listAdapter = listView.getAdapter();
		  if (listAdapter == null) {
		   // pre-condition
		   return;
		  }
		  int totalHeight = 0;
		  for (int i = 0; i < listAdapter.getCount(); i++) {
		   View listItem = listAdapter.getView(i, null, listView);
		   listItem.measure(0, 0);
		   totalHeight += listItem.getMeasuredHeight();
		  }
		  ViewGroup.LayoutParams params = listView.getLayoutParams();
		  params.height = totalHeight
		    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		  listView.setLayoutParams(params);
		 }

}
