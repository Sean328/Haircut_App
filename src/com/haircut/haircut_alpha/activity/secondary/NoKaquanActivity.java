package com.haircut.haircut_alpha.activity.secondary;

import com.haircut.haircut_alpha.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

public class NoKaquanActivity extends Activity implements OnClickListener{
	
	private TextView noCard;
	private TextView noBack;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nocaquan);
		
		initView();
	}


	private void initView() {
		noBack = (TextView) findViewById(R.id.imgback_110);
		noCard = (TextView) findViewById(R.id.tv_meiyouquan);
		
		noCard.setVisibility(View.VISIBLE);
		
		noBack.setOnClickListener(this);
		noCard.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_110:
			finish();
			break;
		
		}
		
	}

}
