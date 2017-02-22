package com.haircut.haircut_alpha.fragment;

import java.util.List;
import java.util.Map;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.ChatActivity;
import com.haircut.haircut_alpha.activity.GoodsDetailActivity;
import com.haircut.haircut_alpha.activity.secondary.LoginNull;
import com.haircut.haircut_alpha.http.MessageHttpDownload;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.hyphenate.easeui.EaseConstant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_message extends Fragment{
		
	private static ListView listView;
	private Button button;
	List<Map<String, String>> moreList;
	
	private String user_id1 = null;
	private Context context;
	private SharedUtils sh;
	private String toChatName = "lisi";
	
	public static final String EXTRA_USER_ID = "userId";
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view=inflater.inflate(R.layout.activity_fragment_message, container,false);
			
			context = getActivity().getApplicationContext();
			sh = new SharedUtils(context);
			Map<String,String> data = sh.read();
			if(data != null){
				 user_id1 = data.get("username").toString().trim();
			}
			
			return view;
		}
		
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			listView = (ListView) getView().findViewById(R.id.listView);
			
			MessageHttpDownload httpDownloader = new MessageHttpDownload(listView, getActivity());
			httpDownloader.execute();
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if(user_id1==""){
						Intent intent = new Intent(getActivity(),LoginNull.class);
						startActivity(intent);
					}else{
						Intent intent=new Intent();
//						intent.setClass(getActivity(), ChatActivity.class);
						startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, toChatName));
//						startActivity(intent);	
					}
					
				}
			});
		}
		
}
