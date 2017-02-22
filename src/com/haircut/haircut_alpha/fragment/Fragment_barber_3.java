package com.haircut.haircut_alpha.fragment;

import java.util.List;
import java.util.Map;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.ShopDetial;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.http.ShopHttpDownload;
import com.haircut.haircut_alpha.http.ShopHttpDownload3;
import com.haircut.haircut_alpha.view.RefreshableView;
import com.haircut.haircut_alpha.view.RefreshableView.PullToRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_barber_3 extends Fragment{
	
	private static ListView listView;
	List<Map<String, String>> moreList;
	private ShopInfo mDatalist;
	
	RefreshableView refreshableView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_fragment_barber_01, container,false);
		
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		refreshableView = (RefreshableView) getView().findViewById(R.id.refreshable_view);
		
		listView = (ListView) getView().findViewById(R.id.listView_shop);
		ShopHttpDownload3 shopHttpDownload = new ShopHttpDownload3(listView, getActivity());
		shopHttpDownload.execute();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
							
				Intent intent=new Intent();
				intent.setClass(getActivity(),ShopDetial .class);
				
				System.out.println("±»µã»÷£º"+position);
				mDatalist = ShopInfoAdapter.infos.get(position);
				System.out.println("mDatalist = ShopInfoAdapter.infos.get(position)="+mDatalist);
				intent.putExtra("datas", mDatalist);
				
				startActivity(intent);	
			}
		});
		
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);
		
	}
}
