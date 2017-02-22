package com.haircut.haircut_alpha.activity.secondary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.http.MyOthersHttpDownload;

public class MyShopCollect extends Activity implements OnClickListener{
	private TextView imgback;
	private ListView mListView;
	private TextView pageTitle;
	private GoodsInfo mDatalist1;
	private String MyOders = "1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myorders);
		
		initView();
		
		MyOthersHttpDownload myOthersHttpDownload = new MyOthersHttpDownload(mListView,
				getApplicationContext(), CONSTS.ShopCollect_Return_URL);
		myOthersHttpDownload.execute();
		
	}

	private void initView() {
		imgback= (TextView) findViewById(R.id.imgback_orders);
		mListView = (ListView) findViewById(R.id.listView_orders);
		pageTitle = (TextView) findViewById(R.id.tv_title);

		pageTitle.setText("µÍ∆Ã ’≤ÿ");
		
		imgback.setOnClickListener(this);
		
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
