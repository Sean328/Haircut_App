package com.haircut.haircut_alpha.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.adapter.BannerAdapter;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.CitySelectActivity;
import com.haircut.haircut_alpha.activity.Home_huli;
import com.haircut.haircut_alpha.activity.Home_jianfa;
import com.haircut.haircut_alpha.activity.Home_ranfa;
import com.haircut.haircut_alpha.activity.Home_tangfa;
import com.haircut.haircut_alpha.activity.ShopDetial;
import com.haircut.haircut_alpha.entity.BannerWrapper;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.http.ShopHttpDownload;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.haircut.haircut_alpha.view.RefreshableView;
import com.haircut.haircut_alpha.view.RefreshableView.PullToRefreshListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.LinePageIndicator;

public class Fragment_home 	extends Fragment implements OnPageChangeListener{
	
     
	
	@ViewInject(R.id.index_top_city)
	private TextView topCity;
	
	
	@ViewInject(R.id.listView_recommend)
	private ListView mListView;
	
	@ViewInject(R.id.viewpager)
	private static ViewPager mViewpager;
	
	@ViewInject(R.id.indicator)
	public static LinePageIndicator mIndicator;
	
	private RadioButton jianfa;
	private RadioButton ranfa;
	private RadioButton tangfa;
	private RadioButton huli;
	
	

	/** 记录是否停止循环播放 */
	private boolean isStop = false;	
	private Handler mHandler;
   
	private ShopInfo mDatalist;
	
	private Context context;
	private SharedUtils she;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_fragment_home, container,false);
		View headView = View.inflate(getActivity(), R.layout.list_header_home, null);
		
		context = getActivity().getApplicationContext();
		she = new SharedUtils(context);
		
		ViewUtils.inject(this,view);
		ViewUtils.inject(this,headView);
		mListView.addHeaderView(headView);
		
		jianfa = (RadioButton) headView.findViewById(R.id.rb_jianfa);
		ranfa = (RadioButton) headView.findViewById(R.id.rb_ranfa);
		huli = (RadioButton) headView.findViewById(R.id.rb_huli);
		tangfa = (RadioButton) headView.findViewById(R.id.rb_tangfa);
		
		
		initView();
		
		// 开启子线程，让广告条以3秒的频率循环播放
				new Thread(new Runnable() {

					@Override
					public void run() {

						while (!isStop) {
							SystemClock.sleep(5000);
							if(getActivity()!=null){ //解决了空指针报错的问题
								getActivity().runOnUiThread(new Runnable() {
									public void run() {
										mViewpager.setCurrentItem(mViewpager.getCurrentItem() + 1);
									}
								});
							}
						}
					}
				}).start();
		
		
		return view;
	}
	

	
	
	private void initView() {
		
		mViewpager.setOnPageChangeListener(this);
		
		Map<String,String> data = she.read();
		
		String cityName = data.get("cityName");
		if(cityName != ""){
			topCity.setText(cityName);
		}

	}




	@OnClick({R.id.index_top_city})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.index_top_city://城市
			//带有返回值的跳转
			startActivityForResult(new Intent(getActivity(),CitySelectActivity.class), MyUtils.RequestCityCode);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		BannerHttpDownloader bannerHttpDownloader = new BannerHttpDownloader();
		bannerHttpDownloader.execute();
		
		ShopHttpDownload shopHttpDownload = new ShopHttpDownload(mListView, getActivity());
		shopHttpDownload.execute();
		
	
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent();
				intent.setClass(getActivity(), ShopDetial.class);
				
				System.out.println("被点击："+position);
				mDatalist = ShopInfoAdapter.infos.get(position-1);
				System.out.println("mDatalist = ShopInfoAdapter.infos.get(position)="+mDatalist);
				intent.putExtra("datas", mDatalist);
				
				startActivity(intent);	
			}
			
		});
		
		
		jianfa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), Home_jianfa.class);
				startActivity(intent);		
			}
		});
		
		ranfa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), Home_ranfa.class);
				startActivity(intent);		
			}
		});
		
		tangfa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), Home_tangfa.class);
				startActivity(intent);		
			}
		});
		
		huli.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(getActivity(), Home_huli.class);
				startActivity(intent);		
			}
		});
		
		
		
	}

	
	class BannerHttpDownloader extends AsyncTask<String, Void, String>{

		private URL url;
		private HttpURLConnection conn;
		
		@Override
		protected String doInBackground(String... arg0) {
			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			
			try {
				url = new URL("http://119.29.183.12/haircut/banner.json");
				conn = (HttpURLConnection) url.openConnection();
				
				buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while ((line = buffer.readLine())!=null) {
					sb.append(line);
				}
				
			} catch (Exception e) {
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
			
			Log.d("banner", ""+result);
			
			Gson gson = new Gson();
			BannerWrapper wrapper = gson.fromJson(result, BannerWrapper.class);
			
			if(wrapper !=null){
				BannerAdapter adapter = new BannerAdapter(context,wrapper.banner);
				mViewpager.setAdapter(adapter);
				
				Fragment_home.mIndicator.setViewPager(mViewpager);
				Fragment_home.mIndicator.onPageSelected(0);
			}

		}
		
	}
	
	public static boolean isConn(Context context){
        boolean bisConnFlag=false;
        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if(network!=null){
            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }



	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	

	
	@Override
	public void onDestroy() {
		// activity销毁时候，关闭循环播放
		isStop = true;
		super.onDestroy();
	}
	
}



