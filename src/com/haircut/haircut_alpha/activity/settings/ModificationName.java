package com.haircut.haircut_alpha.activity.settings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.LoginActivity;
import com.haircut.haircut_alpha.activity.RegisterActivity;
import com.haircut.haircut_alpha.activity.secondary.MySettings;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.User;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModificationName extends Activity {
	private EditText etName;
	private Button btnName;
	
	private String userNumber;
	
	private Context context;
	private SharedUtils sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acticity_modificationname);
		
		initView();
	}

	private void initView() {
		etName = (EditText) findViewById(R.id.editText1);
		btnName = (Button) findViewById(R.id.user_save);
		
		context = this.getApplicationContext();
		sh = new SharedUtils(context);
		
		Map<String,String> data = sh.read();
		userNumber = data.get("username").toString();
		
		System.out.println("user_tel=="+userNumber);
		
		btnName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				handleSubmitName();
			}
		});
		
	}

	protected void handleSubmitName() {
		String str1 = null;
		
		final String userName = etName.getText().toString();
		
		if(userName==null){
			Toast.makeText(this, "用户名不能为空", 0).show();
		}else{
			
			try {
				str1 = URLEncoder.encode(userName, "utf-8").toString().trim();
				str1 = URLEncoder.encode(str1, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("str1 = "+str1);
			new HttpUtils().send(HttpMethod.GET, CONSTS.User_NickName_URL+"&user_tel="+userNumber+"&user_name="
					+str1,new RequestCallBack<String>() {
					
						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(ModificationName.this, msg, 0).show();
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							ResponseObject<User> object = new GsonBuilder().create().fromJson(responseInfo.result,
									new TypeToken<ResponseObject<User>>(){}.getType());
							System.out.println("object.getState()---"+object.getState());
							if(object.getState() == 1){
								Toast.makeText(ModificationName.this, object.getMsg(), 0).show();
								SharedUtils.putNickName(ModificationName.this,userName);
								Intent intent = new Intent(ModificationName.this,MySettings.class);
								startActivity(intent);
								
							}else{
								Toast.makeText(ModificationName.this, object.getMsg(), 0).show();
							}
							
						}
					});
		}
		
	}
	

}
