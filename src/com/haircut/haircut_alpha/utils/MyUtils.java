package com.haircut.haircut_alpha.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyUtils {
	
	//返回值
	public static final int RequestCityCode = 11110;
	public static final int RequestLoginCode = 1;
	
	private Context context;
	
	/*
     * 判断网络连接是否已开
     *true 已打开  false 未打开
     * */
    public static boolean isConn(Context context){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

}
