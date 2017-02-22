package com.haircut.haircut_alpha.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;






import com.haircut.haircut_alpha.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.Toast;

//实现标记的写入与读取
public class SharedUtils {
	private static final String FILE_NAME="lifa";
	private static final String MODE_NAME="welcome";
	private Context mContext;
	
	public SharedUtils(){
		
	}
	
	public SharedUtils(Context mContext){
		this.mContext = mContext;
	}
	
    //获取boolean类型的值
    public static boolean getWelcomeBoolean(Context context)  {
    	return true;
    }
    //写入boolean类型的值
    public static void putWelcomeBoolean(Context context,boolean isFirst){
    	Editor editor = context.getSharedPreferences(FILE_NAME, context.MODE_APPEND).edit();
    	editor.putBoolean(MODE_NAME, isFirst);
    	editor.commit();
    }
    
    //写入登陆的名称
    public static void putUserName(Context context,String username,String password){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("username",username);
    	editor.putString("password", password);
    	editor.commit();
    	System.out.println("存入后数据="+username+ " "+password);
    	Toast.makeText(context, "账号密码保存成功", Toast.LENGTH_SHORT).show();
    }
    
    //写入完善信息的资料
    public static void putNickName(Context context,String nickName){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("nickName",nickName);
    	editor.commit();
    	System.out.println("存入后数据="+nickName+ " "+nickName);
    }
    
  //写入完善信息的资料
    public static void putAddress(Context context,String address){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("address",address);
    	editor.commit();
    	System.out.println("存入后数据="+address+ " "+address);
    }
    
  //写入完善信息的资料
    public static void putGender(Context context,String gender){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("gender",gender);
    	editor.commit();
    	System.out.println("存入后数据="+gender+ " "+gender);
    }
    
    // 写入一个String类型的数据
 	public static void putCityName(Context context, String cityName) {

 		Editor editor = context.getSharedPreferences("Acc&Pwd",
 				Context.MODE_APPEND).edit();
 		editor.putString("cityName", cityName);
 		editor.commit();
 	}
    
    //读取账号信息
    public Map<String, String> read(){
    	
    	Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("Acc&Pwd", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("password", sp.getString("password", ""));
        data.put("nickname", sp.getString("nickName", ""));
        data.put("address", sp.getString("address", ""));
        data.put("gender", sp.getString("gender", ""));
        data.put("cityName",sp.getString("cityName", ""));
        return data;
    	
    }
    
    public void clearData() {
    	SharedPreferences sp = mContext.getSharedPreferences("Acc&Pwd", Context.MODE_PRIVATE);
    	SharedPreferences sp1 = mContext.getSharedPreferences("testSP", Context.MODE_PRIVATE);
    	sp.edit().clear().commit();
    	sp1.edit().clear().commit();
    }
    
    
    public static void saveBitmapToSharedPreferences(Context context,Bitmap bm){   
        Bitmap bitmap=bm;
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream   
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();   
        bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);   
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String   
        byte[] byteArray=byteArrayOutputStream.toByteArray();   
        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));   
        //第三步:将String保持至SharedPreferences   
        SharedPreferences sharedPreferences=context.getSharedPreferences("testSP", Context.MODE_PRIVATE);   
        Editor editor=sharedPreferences.edit();   
        editor.putString("image", imageString);   
        editor.commit();
        
        System.out.println("图片已保存"+imageString);
    }   
    
    public Bitmap getBitmapFromSharedPreferences(){   
        SharedPreferences sharedPreferences=mContext.getSharedPreferences("testSP", Context.MODE_PRIVATE);   
        //第一步:取出字符串形式的Bitmap   
        String imageString=sharedPreferences.getString("image", "");   
        //第二步:利用Base64将字符串转换为ByteArrayInputStream   
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);   
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);   
        //第三步:利用ByteArrayInputStream生成Bitmap   
        Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);   
        return bitmap;
    }   

}
