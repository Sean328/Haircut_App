package com.haircut.haircut_alpha.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.ShopDetial;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.ShopWrapper;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.http.ShopHttpDownload;
import com.haircut.haircut_alpha.view.RefreshableView;
import com.haircut.haircut_alpha.view.UIHelper;
import com.haircut.haircut_alpha.view.RefreshableView.PullToRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CustomListFragment extends BaseFragment {

	private static final String FRAGMENT_INDEX = "fragment_index";
	private final int FIRST_FRAGMENT = 0;
	private final int SECOND_FRAGMENT = 1;
	private final int THIRD_FRAGMENT = 2;
	
	private URL url;
	private HttpURLConnection conn;
	private  ShopWrapper wrapper;
	private ListView listView;
	private Context context;
	RefreshableView refreshableView;
	private ShopInfo mDatalist;
	
	View mFragmentView;

	private int mCurIndex = -1;
	/** 标志位，标志已经初始化完*/
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;

	/**
	 * 创建新实例
	 * 
	 * @param index
	 * @return
	 */
	public static CustomListFragment newInstance(int index) {
		Bundle bundle = new Bundle();
		bundle.putInt(FRAGMENT_INDEX, index);
		CustomListFragment fragment = new CustomListFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(mFragmentView == null) {
			mFragmentView = inflater.inflate(R.layout.activity_fragment_barber_01, container, false);
			listView = (ListView) mFragmentView.findViewById(R.id.listView_shop);
			refreshableView = (RefreshableView) mFragmentView.findViewById(R.id.refreshable_view);
			
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
			
			//获得索引
			Bundle bundle = getArguments();
			if (bundle != null) {
				mCurIndex = bundle.getInt(FRAGMENT_INDEX);
				System.out.println("mCurIndex==="+mCurIndex);
			}
			isPrepared = true;
			lazyLoad();
		}
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent();
				intent.setClass(getActivity(), ShopDetial.class);
				
				System.out.println("被点击："+position);
				mDatalist = ShopInfoAdapter.infos.get(position);
				System.out.println("mDatalist = ShopInfoAdapter.infos.get(position)="+mDatalist);
				intent.putExtra("datas", mDatalist);
				
				startActivity(intent);	
				
			}
			
		});
		
		
		//因为共用一个Fragment视图，所以当前这个视图已被加载到Activity中，必须先清除后再加入Activity
		ViewGroup parent = (ViewGroup)mFragmentView.getParent();
		if(parent != null) {
			parent.removeView(mFragmentView);
		}
		return mFragmentView;
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		
		HttpDownload httpDownload = new HttpDownload(context);
		httpDownload.execute();
	}

//	private void setView() {
//		// 根据索引加载不同视图
//		switch (mCurIndex) {
//		case FIRST_FRAGMENT:
////			mFragmentView.setText("第一�?");
//			break;
//
//		case SECOND_FRAGMENT:
////			mFragmentView.setText("第二�?");
//			break;
//
//		case THIRD_FRAGMENT:
////			mFragmentView.setText("第三�?");
//			break;
//		}
//	}
	
	class HttpDownload extends AsyncTask<String, Void, String>{
		
		Context context1;
		public HttpDownload(Context context) {
			this.context1 = context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//显示加载进度对话
			UIHelper.showDialogForLoading(getActivity(), "正在加载...", true);
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			
			
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			
			try {
				
				switch (mCurIndex) {
				case FIRST_FRAGMENT:
					url = new URL(CONSTS.Shop_Data_URL);
					break;
					
				case SECOND_FRAGMENT:
					url = new URL(CONSTS.Shop2_Data_URL);
					break;
					
				case THIRD_FRAGMENT:
					url = new URL(CONSTS.Shop3_Data_URL);
					break;
				}
				
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
			//关闭对话�?
			UIHelper.hideDialogForLoading();
			
			try {
				Log.d("main1", ""+result);

				Gson gson = new Gson();
				
				wrapper = new ShopWrapper();
				wrapper= gson.fromJson(result, ShopWrapper.class);

				if(wrapper!=null){
					ShopInfoAdapter adapter = new ShopInfoAdapter(getActivity(), wrapper.datas);
					listView.setAdapter(adapter);
				}
				
			} catch (Exception e) {
				
			}		
		}
		
		
	}
	
}
