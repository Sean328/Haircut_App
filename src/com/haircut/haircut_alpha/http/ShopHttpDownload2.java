package com.haircut.haircut_alpha.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.ShopWrapper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class ShopHttpDownload2 extends AsyncTask<String, Void, String>{
		
	private URL url;
	private HttpURLConnection conn;
	private ListView listView;
	private Context context;
	
	public ShopHttpDownload2(ListView listView, Context context){
		this.listView = listView;
		this.context= context;	
		
	}
	
		
		@Override
		protected String doInBackground(String... params) {
			
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			
			try {
				url = new URL(CONSTS.Shop2_Data_URL);
//				url = new URL("http://10.0.2.2:8080/MyIM/shopinfo.json");
				
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
				Log.d("main", ""+result);

				Gson gson = new Gson();

				ShopWrapper wrapper= gson.fromJson(result, ShopWrapper.class);
				System.out.println("wrapper=" +wrapper);
				

				ShopInfoAdapter adapter = new ShopInfoAdapter(context, wrapper.datas);
				System.out.println("adapter:"+adapter);
//				setAdapter
				listView.setAdapter(adapter);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	
}
