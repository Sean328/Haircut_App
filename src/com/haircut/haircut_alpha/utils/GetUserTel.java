package com.haircut.haircut_alpha.utils;

import java.util.Map;

import android.content.Context;

public class GetUserTel {
	
	private Context context;
	private static SharedUtils sh;
	private String user_tel;
	
	public static String getUer_tel(Context context,String user_tel){
		
		sh = new SharedUtils(context);
		Map<String,String> data = sh.read();
		if(data != null){
			user_tel = data.get("username").toString().trim();
		}
		
		return user_tel;
		
	}
	
	

}
