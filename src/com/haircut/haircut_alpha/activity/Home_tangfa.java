package com.haircut.haircut_alpha.activity;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.adapter.GoodsAdapter;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.http.GoodsHttpDownload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Home_tangfa extends Activity{
	
	private TextView imgback;
	private ListView mListView;
	private GoodsInfo mDatalist1;
	private String JianfaPage = "2";
	private TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jianfa);
		
		initView();
		
		GoodsHttpDownload goodHttpDownload = new GoodsHttpDownload(mListView, getApplicationContext(), JianfaPage);
		goodHttpDownload.execute();
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent();
				intent.setClass(Home_tangfa.this,GoodsDetailActivity .class);
				
				System.out.println("±»µã»÷£º"+position);
				mDatalist1 = GoodsAdapter.mDatalist.get(position);
				System.out.println("mDatalist = ShopInfoAdapter.infos.get(position)="+mDatalist1);
				intent.putExtra("datas", mDatalist1);
				
				startActivity(intent);	
			}
		});
		
	}
	
	private void initView() {
		imgback = (TextView) findViewById(R.id.imgback_1);
		mListView = (ListView) findViewById(R.id.listView_goods);
		title = (TextView) findViewById(R.id.tv_title);
		
		title.setText("ÌÌ·¢");
		
		imgback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

}
