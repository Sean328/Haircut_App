package com.haircut.haircut_alpha.activity.secondary;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.LoginActivity;
import com.haircut.haircut_alpha.activity.MainActivity;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NetworkBolean extends Activity{
	/**
	 * 没有网络是显示的界面
	 * @author Matrix
	 */
	
	private TextView Note;
	private Button btn_login;
	private Context context;
	Boolean bisConnFlag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_null);
		
		bisConnFlag = MyUtils.isConn(getApplicationContext());
		
		Note = (TextView) findViewById(R.id.textView_note);
		btn_login = (Button) findViewById(R.id.btn_loginnn);
		
		Note.setText("网络不可用，请打开网络！");
		btn_login.setText("设置网络");
		
//		btn_login.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent;
//				
//				 if(android.os.Build.VERSION.SDK_INT>10){
//	                  intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//	              }else{
//	                  intent = new Intent();
//	                  ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
//	                  intent.setComponent(component);
//	                  intent.setAction("android.intent.action.VIEW");
//	              }
//	              startActivity(intent);
//				
//			}
//			
//			
//				
//			});
		
		
		
	}
	
}
