package com.haircut.haircut_alpha.activity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.User;
import com.haircut.haircut_alpha.entity.User.UserInfo;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText username;
	private EditText password;
	private Button login;
	private Button register;
	private Context context;
	private SharedUtils sh;
	private CheckBox check;
	
	private TextView imgback;

	private MyTask mTask;
	private String user_tel;
    private User wrapper;
    private List<UserInfo> UserInfos;
    public static UserInfo userInfo;
    private String imgURL;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		context = this.getApplicationContext();
		sh = new SharedUtils(context);
		
		initView();

	}

	private void initView() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.btn_login);
		imgback = (TextView) findViewById(R.id.imgback_1);
		register = (Button) findViewById(R.id.btn_register);
		check = (CheckBox) findViewById(R.id.check_mima);
		
		imgback.setOnClickListener(this);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgback_1:
			finish();
			break;

		case R.id.btn_login:
			handleLogin();
			break;
			
		case R.id.btn_register:
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		}
		
	}

	private void handleLogin() {
		final String userName = username.getText().toString();
		final String passWord = password.getText().toString();
		System.out.println("userName==="+userName);
		
		//登录
        EMClient.getInstance().login(userName,passWord, new EMCallBack() {
            
            @Override
            public void onSuccess() {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                finish();
            	System.out.println("登陆聊天服务器成功------->>>>>>");
            }
            
            @Override
            public void onProgress(int progress, String status) {
                
            }
            
            @Override
            public void onError(int code, String error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "登录失败", 0).show();
                    }
                });
            }
        });
        
//		
//		//登陆聊天服务器
//		EMChatManager.getInstance().login(userName,passWord,new EMCallBack() {//回调
//		@Override
//		public void onSuccess() {
//			Log.d("main", "EMChatManager.getInstance()方法被调用！");
//			runOnUiThread(new Runnable() {
//				public void run() {
//					EMGroupManager.getInstance().loadAllGroups();
//					EMChatManager.getInstance().loadAllConversations();
//					Log.d("main", "登陆聊天服务器成功！");		
//				}
//			});
//			finish();
//		}
//	 
//		@Override
//		public void onProgress(int progress, String status) {
//	 
//		}
//	 
//		@Override
//		public void onError(int code, String message) {
//			Log.d("main", "登陆聊天服务器失败！");
//		}
//	});
	

		new HttpUtils().send(HttpMethod.GET, CONSTS.User_Login_URL+"&user_tel="+userName
				+"&password="+passWord, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(LoginActivity.this, "登陆失败", 0).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Gson gson = new GsonBuilder().create();
						ResponseObject<User> object = gson.fromJson(responseInfo.result,
								new TypeToken<ResponseObject<User>>(){}.getType());
						if(object.getState()==1){
							SharedUtils.putUserName(LoginActivity.this,userName,passWord);
							loginSuccess();//出来登陆信息
						}
						Toast.makeText(LoginActivity.this, object.getMsg(), 0).show();
					}
				});
		
	}
	
	//登陆成功执行的方法
	private void loginSuccess(){
		
		Map<String,String> data = sh.read();
		user_tel = data.get("username");
		
		mTask = new MyTask();  
		mTask.execute(); 
		
		int a = 5;
		Intent intent = new Intent(LoginActivity.this,MainActivity.class);
		intent.putExtra("page", a);
		this.finish();
		startActivity(intent);
	}
	
	private class MyTask extends AsyncTask<String, Void, String> {
		
		private URL url;
		private HttpURLConnection conn;

		@Override
		protected String doInBackground(String... arg0) {
			
			

			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			try {
				System.out.println("user_tel="+user_tel);
				url = new URL(CONSTS.User_Info_URL+"&user_tel="+user_tel);
				conn = (HttpURLConnection) url.openConnection();
				
				buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while ((line = buffer.readLine())!=null) {
					sb.append(line);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(buffer != null)
						buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			Gson gson = new Gson();
			wrapper = new User();
			wrapper= gson.fromJson(result, User.class);
			
			UserInfos = wrapper.datas;
			
			System.out.println("能否在登陆界面拿到数据？"+wrapper.datas);
			userInfo = UserInfos.get(0);
			String gender;
			System.out.println("userInfo.getUser_gender() = "+userInfo.getUser_gender());
			String genderId = userInfo.getUser_gender();
			int i = Integer.parseInt(genderId);
			System.out.println("i="+i);
			if(i == 0 ){
				gender = "男";
			}else{
				gender = "女";
			}
			
			imgURL = userInfo.getUser_img();
			String address = userInfo.getUser_address();
			String nickName = userInfo.getUser_name();
			
			SharedUtils.putGender(context, gender);
			SharedUtils.putAddress(context, address);
			SharedUtils.putNickName(context, nickName);
			
//			BitmapUtils bitmapUtils = new BitmapUtils(context);
			System.out.println("程序走到这一步 imgURL = "+imgURL);
//			bitmapUtils.display(touxiang, imgURL);

//			new LoadAsyncTask().execute(imgURL);
			
		}
		
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
		if(check.isChecked() == true){
			Map<String,String> data = sh.read();
			if(data != null){
				username.setText(data.get("username"));
				password.setText(data.get("password"));
			}
		}
		
	}
	
}