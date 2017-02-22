package com.haircut.haircut_alpha.activity.secondary;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class LoginNull extends Activity{
	
	private TextView Note;
	private Button btn_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_null);
		
		Note = (TextView) findViewById(R.id.textView_note);
		btn_login = (Button) findViewById(R.id.btn_loginnn);
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginNull.this,LoginActivity.class);
				finish();
				startActivity(intent);
				
			}
		});
	}
	
}
