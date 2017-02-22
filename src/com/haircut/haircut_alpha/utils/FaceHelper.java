package com.haircut.haircut_alpha.utils;

import java.io.ByteArrayOutputStream;

import org.json.JSONObject;

import android.graphics.Bitmap;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.haircut.haircut_alpha.consts.CONSTS;
/**
14  * Face++ �����ִ࣬�����������ʱ����
15  * 
17  * 
18  */
 public class FaceHelper {
	 private static final String TAG = FaceHelper.class.getName();
/**
24      * ��������
25      * 
26      * @param bitmap
27      * @param callBack
28      */
     public static void uploadFaces(final Bitmap bitmap, final CallBack callBack) {
         new Thread(new Runnable() {
 
             @Override
             public void run() {
                try {
                     // ��Bitmap����ת����byte����
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                     byte[] data = stream.toByteArray();
 
                     // ������������
                     HttpRequests requests = new HttpRequests(CONSTS.APIKey, CONSTS.APISecret, true, true);
                     // ��װ����
                    PostParameters params = new PostParameters();
                     params.setImg(data);
                     // �ύ��������
                     JSONObject jsonObject = requests.detectionDetect(params);
                     // ���ûص�����
                     callBack.success(jsonObject);
                } catch (FaceppParseException e) {
                     // ���ûص�����
                     callBack.error(e);
                     e.printStackTrace();
                 }
 
             }
         }).start();
 
     }
 
     /**
      * ���ݻص��ӿڣ����������ȡ����
      * 
      * @author Rabbit_Lee
      * 
      */
     public interface CallBack {
 
         void success(JSONObject jsonObject);
 
         void error(FaceppParseException exception);
     }
 }