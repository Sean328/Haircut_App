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
	protected void onCreate(Bundle savedInstanceState) {//��дoncreate����
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);//����һ��ҳ�沼��
		
		bisConnFlag = MyUtils.isConn(getApplicationContext());
		
		
		new Handler(new Handler.Callback() {
			//������յ�����Ϣ�ķ���
			@Override
			public boolean handleMessage(Message msg) {
				//ʵ��ҳ����ת
				//���ǵ�һ������
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
//					//������ʼ�¼
//					SharedUtils.putWelcomeBoolean(getBaseContext(), true);
//				}
								
				return false;
			}
		}).sendEmptyMessageDelayed(0, 3000);
		
	}

}
