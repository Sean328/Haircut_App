package com.haircut.haircut_alpha.activity;

import com.haircut.haircut_alpha.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Home_huodong extends Activity{
	
	private TextView imgback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_huodong);
		
		imgback = (TextView) findViewById(R.id.imgback_1);
		
		imgback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

}
