package com.haircut.haircut_alpha.activity.secondary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.GoodsDetailActivity;
import com.haircut.haircut_alpha.adapter.MyOtherAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Collect.CollectInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper;
import com.haircut.haircut_alpha.entity.Cart.CartInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.http.MyOthersHttpDownload;

public class MyGoodsCollect extends Activity implements OnClickListener{

	private TextView imgback;
	private ListView mListView;
	private TextView pageTitle;

	private CollectInfo mDatalist1;
	private String mGoods_id;
	private List<GoodsInfo> GoodInfos;
	private GoodsInfo goodsInfo;
	
	private MyTask mTask;
	private GoodsWrapper wrapper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorders);
		
		initView();
		
		MyOthersHttpDownload myOthersHttpDownload = new MyOthersHttpDownload(mListView,
				getApplicationContext(), CONSTS.Collect_Return_URL);
		myOthersHttpDownload.execute();
		
	}

	private void initView() {
		imgback= (TextView) findViewById(R.id.imgback_orders);
		mListView = (ListView) findViewById(R.id.listView_orders);
		pageTitle = (TextView) findViewById(R.id.tv_title);

		pageTitle.setText("商品收藏");
		
		imgback.setOnClickListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				mDatalist1 = MyOtherAdapter.collect_infos.get(position);
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
     		intent.setClass(MyGoodsCollect.this,GoodsDetailActivity.class);
 	
     		System.out.println("goodsInfo"+goodsInfo);
     		intent.putExtra("datas", goodsInfo);
     				
     		startActivity(intent);	
		}
		 
	 }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_orders:
			finish();
			break;

		default:
			break;
		}
		
	}
}
