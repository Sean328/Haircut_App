package com.haircut.haircut_alpha.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyUtils {
	
	//����ֵ
	public static final int RequestCityCode = 11110;
	public static final int RequestLoginCode = 1;
	
	private Context context;
	
	/*
     * �ж����������Ƿ��ѿ�
     *true �Ѵ�  false δ��
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
