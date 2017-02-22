package com.haircut.haircut_alpha.activity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.User;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener{
	
	private EditText phoneNumber;
	private EditText password;
	private EditText verificationCode;
	private Button register;
	private Button yanzhengma;
	private String currentUsername;
	private String currentPassword;
	private CountTimer countTimer;
	private int isSend;
	private int isRight;
	
	private TextView imgback;
	private EventHandler eh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		SMSSDK.initSDK(this, "121cd519ced91", "7be97537ccb1100cffb8914f000336f2");
//		EMChat.getInstance().init(getApplicationContext());
		
		initView();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterEventHandler(eh);//解除注册短信回调
	}
	
	private void initView() {
		phoneNumber = (EditText) findViewById(R.id.shoujihao);
		verificationCode = (EditText) findViewById(R.id.yanzhengma);
		password = (EditText) findViewById(R.id.mima);
		imgback = (TextView) findViewById(R.id.imgback_1);
		register = (Button) findViewById(R.id.zhuce);
		yanzhengma = (Button) findViewById(R.id.btn_yazhengma);
		countTimer = new CountTimer(60000, 1000);
		
		imgback.setOnClickListener(this);
		yanzhengma.setOnClickListener(this);
		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_1:
			finish();
			break;
			
		case R.id.btn_yazhengma:
			countTimer.start();
			sendSMSRandom();
			
			break;

		case R.id.zhuce:
			SMSSDK.submitVerificationCode("86", phoneNumber.getText().toString(), verificationCode.getText().toString());
			registerUser();
			break;
		}
		
	}
	
	//每隔一分钟可以点击获取验证码
	public class CountTimer extends CountDownTimer{
		/**
		 * 
		 * @param millisInFuture  时间间隔是多长时间
		 * @param countDownInterval回馈onTick方法，每隔多久执行一次
		 */

		public CountTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// 更新页面的组件
			yanzhengma.setText("获取验证码");
			yanzhengma.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			//更新页面的组件
			yanzhengma.setText(millisUntilFinished/1000+"秒后发送");
			yanzhengma.setClickable(false);
		}
		
	}
	
	public void sendSMSRandom(){
		SMSSDK.initSDK(this, "121cd519ced91", "7be97537ccb1100cffb8914f000336f2");
			eh = new EventHandler(){
				 
	            @Override
	            public void afterEvent(int event, int result, Object data) {
	 
	               if (result == SMSSDK.RESULT_COMPLETE) {
	                //回调完成
	                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
	                	isRight = event;
	                //提交验证码成功
	                	System.out.println("验证码校验成功！");
	                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
	                //获取验证码成功
	                	isSend = event;
	                	System.out.println("验证码发送成功！");
	                	System.out.println("isSend===="+isSend);
	                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
	                //返回支持发送验证码的国家列表
	                } 
	              }else{                                                                 
	                 ((Throwable)data).printStackTrace(); 
	          }
	      } 
	   }; 
	SMSSDK.registerEventHandler(eh); //注册短信回调
	
	String phoneName = phoneNumber.getText().toString();
	SMSSDK.getVerificationCode("86", phoneName.toString());
	}
		
	//注册提交
	public void registerUser(){
		
		System.out.println("isSend="+isSend);
		
		final String user_tel = phoneNumber.getText().toString().trim();
		final String psw = password.getText().toString().trim();
		
		if(user_tel.length()!=11){
			phoneNumber.setError(Html.fromHtml("<font color=red>请输入正确手机号！</font>"));
			return;
		}
		if(verificationCode.getText().toString().trim().length()<=0){
			verificationCode.setError(Html.fromHtml("<font color=red>验证码不能为空！</font>"));
			return;
		}
		if(psw.length()<=0){
			password.setError(Html.fromHtml("<font color=red>密码不能为空！</font>"));
			return;
		}
		
		if(isSend != 2){
			verificationCode.setError(Html.fromHtml("<font color=red>请先获取验证码！</font>"));
			return;
		}
		
		if(user_tel == psw){
			verificationCode.setError(Html.fromHtml("<font color=red>账号不能密码相同！！</font>"));
			return;
		}
		
		
		new Thread(new Runnable() {
		    public void run() {
		    	try {
					EMClient.getInstance().createAccount(user_tel, psw);
				} catch (HyphenateException e) {
					// TODO Auto-generated catch block
					System.out.println("注册失败！！！！");
					e.printStackTrace();
				}
		    	System.out.println("注册成功！！！！");
		      
		}}).start();
		
		
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.User_Register_URL+"&user_tel="+user_tel+"&password="
				+psw,new RequestCallBack<String>() {
				
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(RegisterActivity.this, msg, 0).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ResponseObject<User> object = new GsonBuilder().create().fromJson(responseInfo.result,
								new TypeToken<ResponseObject<User>>(){}.getType());
						System.out.println("object.getState()---"+object.getState());
						if(object.getState() == 1){
							Toast.makeText(RegisterActivity.this, object.getMsg(), 0).show();
							
							
						}else{
							Toast.makeText(RegisterActivity.this, object.getMsg(), 0).show();
						}
						
					}
				});
		
	}
	
}