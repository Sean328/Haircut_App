package com.haircut.haircut_alpha.activity;


import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.secondary.NetworkBolean;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class SplashActivity extends Activity{
	Boolean bisConnFlag = true;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {//重写oncreate方法
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);//关联一个页面布局
		
		bisConnFlag = MyUtils.isConn(getApplicationContext());
		
		
		new Handler(new Handler.Callback() {
			//处理接收到的消息的方法
			@Override
			public boolean handleMessage(Message msg) {
				//实现页面跳转
				//不是第一次启动
				if (SharedUtils.getWelcomeBoolean(getBaseContext())){
					if(bisConnFlag == true){
						startActivity(new Intent(getBaseContext(),MainActivity.class));
						finish();
					}else{
						startActivity(new Intent(getBaseContext(),NetworkBolean.class));
						finish();
						
						
					}

				}
//				else{
//					startActivity(new Intent(getApplicationContext(), GuideActivity.class));
//					//保存访问记录
//					SharedUtils.putWelcomeBoolean(getBaseContext(), true);
//				}
								
				return false;
			}
		}).sendEmptyMessageDelayed(0, 3000);
		
	}

}
