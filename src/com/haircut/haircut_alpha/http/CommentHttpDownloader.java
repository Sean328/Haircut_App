package com.haircut.haircut_alpha.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.CommentAdapter;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Comment;
import com.haircut.haircut_alpha.entity.ShopWrapper;

public class CommentHttpDownloader extends AsyncTask<String, Void, String>{
	private URL url;
	private HttpURLConnection conn;
	private ListView listView;
	private Context context;
	private  Comment wrapper;
	
	public CommentHttpDownloader(ListView listView, Context context){
		this.listView = listView;
		this.context= context;	
	}

	@Override
	protected String doInBackground(String... arg0) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		
		try {
			url = new URL(CONSTS.Comments_URL);
			
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
		super.onPostExecute(result);
		
		Log.d("main", ""+result);

		Gson gson = new Gson();
		
		wrapper = new Comment();
		wrapper= gson.fromJson(result, Comment.class);
		
		if(wrapper!=null){
			CommentAdapter adapter = new CommentAdapter(context, wrapper.datas);
			listView.setAdapter(adapter);
			setListViewHeightBasedOnChildren(listView);
		}
		
		
	}
	
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		  ListAdapter listAdapter = listView.getAdapter();
		  if (listAdapter == null) {
		   // pre-condition
		   return;
		  }
		  int totalHeight = 0;
		  for (int i = 0; i < listAdapter.getCount(); i++) {
		   View listItem = listAdapter.getView(i, null, listView);
		   listItem.measure(0, 0);
		   totalHeight += listItem.getMeasuredHeight();
		  }
		  ViewGroup.LayoutParams params = listView.getLayoutParams();
		  params.height = totalHeight
		    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		  listView.setLayoutParams(params);
		 }
	
}
