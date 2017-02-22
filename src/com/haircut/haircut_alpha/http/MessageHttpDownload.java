package com.haircut.haircut_alpha.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.MyMessageAdapter;
import com.haircut.haircut_alpha.entity.UserInfoWrapper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class MessageHttpDownload extends AsyncTask<String, Void, String>{

	private URL url;
	private HttpURLConnection conn;
	private ListView listView;
	private Context context;
	
	public MessageHttpDownload(ListView listView,Context context) {
		this.listView = listView;
		this.context= context;
	}
	
	@Override
	protected String doInBackground(String... params) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		
		try {
			url = new URL("http://119.29.183.12/MyIM/users.json");
			conn = (HttpURLConnection) url.openConnection();
			
			buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while ((line = buffer.readLine())!=null) {
				sb.append(line);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally{
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	
	//��ʱ���������꽫������ݽ���
		@Override
		protected void onPostExecute(String result) {
			Log.d("main", ""+result);
			//����һ��Gson����
			Gson gson = new Gson();
			//����һ��json����ת��Ϊһ��ʵ����
			UserInfoWrapper wrapper= new Gson().fromJson(result, UserInfoWrapper.class);
			//newһ���Զ����adapter��context�����list���ϴ���ȥ
			MyMessageAdapter adapter = new MyMessageAdapter(context, wrapper.userInfo);
			//setAdapter
			listView.setAdapter(adapter);
		}

}
