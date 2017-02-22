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

//ʵ�ֱ�ǵ�д�����ȡ
public class SharedUtils {
	private static final String FILE_NAME="lifa";
	private static final String MODE_NAME="welcome";
	private Context mContext;
	
	public SharedUtils(){
		
	}
	
	public SharedUtils(Context mContext){
		this.mContext = mContext;
	}
	
    //��ȡboolean���͵�ֵ
    public static boolean getWelcomeBoolean(Context context)  {
    	return true;
    }
    //д��boolean���͵�ֵ
    public static void putWelcomeBoolean(Context context,boolean isFirst){
    	Editor editor = context.getSharedPreferences(FILE_NAME, context.MODE_APPEND).edit();
    	editor.putBoolean(MODE_NAME, isFirst);
    	editor.commit();
    }
    
    //д���½������
    public static void putUserName(Context context,String username,String password){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("username",username);
    	editor.putString("password", password);
    	editor.commit();
    	System.out.println("���������="+username+ " "+password);
    	Toast.makeText(context, "�˺����뱣��ɹ�", Toast.LENGTH_SHORT).show();
    }
    
    //д��������Ϣ������
    public static void putNickName(Context context,String nickName){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("nickName",nickName);
    	editor.commit();
    	System.out.println("���������="+nickName+ " "+nickName);
    }
    
  //д��������Ϣ������
    public static void putAddress(Context context,String address){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("address",address);
    	editor.commit();
    	System.out.println("���������="+address+ " "+address);
    }
    
  //д��������Ϣ������
    public static void putGender(Context context,String gender){
    	Editor editor = context.getSharedPreferences("Acc&Pwd", Context.MODE_APPEND).edit();
    	editor.putString("gender",gender);
    	editor.commit();
    	System.out.println("���������="+gender+ " "+gender);
    }
    
    // д��һ��String���͵�����
 	public static void putCityName(Context context, String cityName) {

 		Editor editor = context.getSharedPreferences("Acc&Pwd",
 				Context.MODE_APPEND).edit();
 		editor.putString("cityName", cityName);
 		editor.commit();
 	}
    
    //��ȡ�˺���Ϣ
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
        //��һ��:��Bitmapѹ�����ֽ����������ByteArrayOutputStream   
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();   
        bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);   
        //�ڶ���:����Base64���ֽ�����������е�����ת�����ַ���String   
        byte[] byteArray=byteArrayOutputStream.toByteArray();   
        String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));   
        //������:��String������SharedPreferences   
        SharedPreferences sharedPreferences=context.getSharedPreferences("testSP", Context.MODE_PRIVATE);   
        Editor editor=sharedPreferences.edit();   
        editor.putString("image", imageString);   
        editor.commit();
        
        System.out.println("ͼƬ�ѱ���"+imageString);
    }   
    
    public Bitmap getBitmapFromSharedPreferences(){   
        SharedPreferences sharedPreferences=mContext.getSharedPreferences("testSP", Context.MODE_PRIVATE);   
        //��һ��:ȡ���ַ�����ʽ��Bitmap   
        String imageString=sharedPreferences.getString("image", "");   
        //�ڶ���:����Base64���ַ���ת��ΪByteArrayInputStream   
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);   
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);   
        //������:����ByteArrayInputStream����Bitmap   
        Bitmap bitmap=BitmapFactory.decodeStream(byteArrayInputStream);   
        return bitmap;
    }   

}
