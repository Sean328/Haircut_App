package com.haircut.haircut_alpha.activity.secondary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.GoodsDetailActivity;
import com.haircut.haircut_alpha.activity.Home_jianfa;
import com.haircut.haircut_alpha.activity.ShopDetial;
import com.haircut.haircut_alpha.adapter.GoodsAdapter;
import com.haircut.haircut_alpha.adapter.MyOtherAdapter;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Cart;
import com.haircut.haircut_alpha.entity.Cart.CartInfo;
import com.haircut.haircut_alpha.entity.Collect;
import com.haircut.haircut_alpha.entity.GoodsWrapper;
import com.haircut.haircut_alpha.entity.Order;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.ShopCollect;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.http.MyOthersHttpDownload;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCart extends Activity implements OnClickListener{
	
	private TextView imgback;
	private ListView mListView1;
	
	private CartInfo mDatalist1;
	private String mGoods_id;
	private List<GoodsInfo> GoodInfos;
	private GoodsInfo goodsInfo;
	
	private MyTask mTask;
	private GoodsWrapper wrapper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycart);
		
		initView();
		
		MyOthersHttpDownload myOthersHttpDownload = new MyOthersHttpDownload(mListView1,
				getApplicationContext(), CONSTS.Cart_Return_URL);
		myOthersHttpDownload.execute();
		
	}

	private void initView() {
		imgback= (TextView) findViewById(R.id.imgback_cart);
		mListView1 = (ListView) findViewById(R.id.listView_cart);

		
		imgback.setOnClickListener(this);
		
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mDatalist1 = MyOtherAdapter.cart_infos.get(position);
				mDatalist1.getGoods_id();
				
				mGoods_id = mDatalist1.getGoods_id();
				
				System.out.println("mGoods_id"+mGoods_id);
				
				//注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常 
				 mTask = new MyTask();  
	             mTask.execute(); 
  
			}
		});
		
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
				url = new URL(CONSTS.Goods_Send_URL+"&mGoods_id="+mGoods_id);
				
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
			wrapper = new GoodsWrapper();
			wrapper= gson.fromJson(result, GoodsWrapper.class);
			
			GoodInfos = wrapper.datas;
			
			System.out.println("能否在这拿到数据？"+wrapper.datas);
			System.out.println("GoodInfos"+GoodInfos);
			goodsInfo = GoodInfos.get(0);
			
			Intent intent=new Intent();
     		intent.setClass(MyCart.this,GoodsDetailActivity.class);
 	
     		System.out.println("goodsInfo"+goodsInfo);
     		intent.putExtra("datas", goodsInfo);
     				
     		startActivity(intent);	
		}
		 
	 }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_cart:
			finish();
			break;

		
		}
		
	}

}
