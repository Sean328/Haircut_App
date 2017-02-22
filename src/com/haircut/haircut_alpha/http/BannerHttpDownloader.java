//package com.haircut.haircut_alpha.http;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import com.google.gson.Gson;
//import com.haircut.haircut_alpha.adapter.BannerAdapter;
//import com.haircut.haircut_alpha.entity.BannerWrapper;
//import com.haircut.haircut_alpha.entity.BannerWrapper.BannerInfo;
//import com.haircut.haircut_alpha.fragment.Fragment_home;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//public class BannerHttpDownloader extends AsyncTask<String, Void, String>{
//	
//	private URL url;
//	private HttpURLConnection conn;
//	private Context context;
//	private ViewPager viewPager;
//	private Handler mHandler;
//	
//	public  BannerHttpDownloader(ViewPager viewPager, Context context) {
//		this.viewPager = viewPager;
//		this.context = context;
//	}
//	
//
//	@Override
//	protected String doInBackground(String... params) {
//		StringBuffer sb = new StringBuffer();
//		String line = null;
//		BufferedReader buffer = null;
//		
//		try {
//			url = new URL("http://119.29.183.12/haircut/banner.json");
//			conn = (HttpURLConnection) url.openConnection();
//			
//			buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//			
//			while ((line = buffer.readLine())!=null) {
//				sb.append(line);
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			try {
//				if(buffer != null)
//					buffer.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return sb.toString();
//	}
//	
//	
//	@Override
//	protected void onPostExecute(String result) {
//		
//		Log.d("banner", ""+result);
//		
//		Gson gson = new Gson();
//		BannerWrapper wrapper = gson.fromJson(result, BannerWrapper.class);
//		
//		BannerAdapter adapter = new BannerAdapter(context,wrapper.banner);
//		
//
//		viewPager.setAdapter(adapter);
//		Fragment_home.mIndicator.setViewPager(Fragment_home.mViewpager);
//		Fragment_home.mIndicator.onPageSelected(0);
//		
//		
////		if(mHandler==null){
////			mHandler = new Handler(){
////				@Override
////				public void handleMessage(android.os.Message msg) {
////					System.out.println("handler ......");
////					
////					int viewpageItem = viewPager.getCurrentItem();
////					viewpageItem++;
////					if(viewpageItem < BannerWrapper.banner.size()-1){
////							viewpageItem++;	
////					}else {
////						viewpageItem = 0;
////					}
////					viewPager.setCurrentItem(viewpageItem);
////					mHandler.sendEmptyMessageDelayed(0, 3000);
////				};
////			};
////			
////			mHandler.sendEmptyMessageDelayed(0, 3000);
////		}
//		
//	}
//	
//	
//	 public static boolean isConn(Context context){
//	        boolean bisConnFlag=false;
//	        ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//	        NetworkInfo network = conManager.getActiveNetworkInfo();
//	        if(network!=null){
//	            bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
//	        }
//	        return bisConnFlag;
//	    }
//
//}
