package com.haircut.haircut_alpha.activity.secondary;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.MainActivity;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class OrderFinish extends Activity implements OnClickListener{
	
	private TextView imgBack;
	private TextView returnHome;
	private TextView write_comments;
	
	private GoodsInfo goods_Datalist = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_orderfinish);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			goods_Datalist = (GoodsInfo) bundle.get("datas");
		}
		
		
		initView();
	}

	private void initView() {
		imgBack = (TextView) findViewById(R.id.imgback_finish);
		returnHome = (TextView) findViewById(R.id.back_home);
		write_comments = (TextView) findViewById(R.id.write_comments);
		
		imgBack.setOnClickListener(this);
		returnHome.setOnClickListener(this);
		write_comments.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_finish:
			finish();
			break;
			
		case R.id.back_home:
			Intent intent1 = new Intent(OrderFinish.this,MainActivity.class);
			startActivity(intent1);
			break;
			
		case R.id.write_comments:
			Intent intent2 = new Intent (OrderFinish.this,RatingActivity.class);
			
			intent2.putExtra("datas", goods_Datalist);
			startActivity(intent2);
			break;

	
		}
		
	}

}
