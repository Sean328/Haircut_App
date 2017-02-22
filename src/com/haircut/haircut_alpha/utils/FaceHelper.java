package com.haircut.haircut_alpha.utils;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.haircut.haircut_alpha.consts.CONSTS;
/**
14  * Face++ 帮助类，执行网络请求耗时操作
15  * 
17  * 
18  */
 public class FaceHelper {
	 private static final String TAG = FaceHelper.class.getName();
/**
24      * 创建网络
25      * 
26      * @param bitmap
27      * @param callBack
28      */
     public static void uploadFaces(final Bitmap bitmap, final CallBack callBack) {
         new Thread(new Runnable() {
 
             @Override
             public void run() {
                try {
                     // 将Bitmap对象转换成byte数组
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                     byte[] data = stream.toByteArray();
 
                     // 创建连接请求
                     HttpRequests requests = new HttpRequests(CONSTS.APIKey, CONSTS.APISecret, true, true);
                     // 封装参数
                    PostParameters params = new PostParameters();
                     params.setImg(data);
                     // 提交网络请求
                     JSONObject jsonObject = requests.detectionDetect(params);
                     // 设置回调数据
                     callBack.success(jsonObject);
                } catch (FaceppParseException e) {
                     // 设置回调数据
                     callBack.error(e);
                     e.printStackTrace();
                 }
 
             }
         }).start();
 
     }
 
     /**
      * 数据回调接口，方便主类获取数据
      * 
      * @author Rabbit_Lee
      * 
      */
     public interface CallBack {
 
         void success(JSONObject jsonObject);
 
         void error(FaceppParseException exception);
     }
 }