package com.haircut.haircut_alpha.activity;

import java.util.ArrayList;






import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.secondary.LoginNull;
import com.haircut.haircut_alpha.adapter.GoodsAdapter;
import com.haircut.haircut_alpha.adapter.SalonDetailAdapter;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Collect;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopCollect;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.http.GoodsHttpDownload;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDetial extends Activity implements OnClickListener{
	
	private ViewPager viewpager;	
	//幻灯片图片资源
	private int [] imgArray={R.drawable.salon_img1,R.drawable.salon_img2,R.drawable.salon_img3};
	//图片数组
	private ArrayList<View> viewlist;
	private ShopInfo receiveData = null;
	private ImageView imageView;
	private ViewGroup pointgroup;//点
	private ImageView[] pointarr;
	private TextView imgback;
	private TextView mTitle;
	private TextView mLocation;
	private TextView shopName;
	private TextView shopTel;
	private TextView shopRank;
	private RatingBar shopRate;
	private TextView fastCut;
	private ListView mListView;
	private String shopId;
	private TextView shop_open_time;
	private ImageView shopCollect;
	
	private Context context;
	private SharedUtils sh;
	private String user_id1;
	
	private GoodsInfo mDatalist1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shopdetail);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			receiveData = (ShopInfo) bundle.get("datas");
			System.out.println("receiveData---------------"+receiveData);
		}
		
		initView();
		initData();
		
		//将图片装载到数组	
		for(int i=0;i<imgArray.length;i++){
			imageView =new ImageView(this);
			//设置图片
			imageView.setBackgroundResource(imgArray[i]);
			//设置图片id
			imageView.setId(imgArray[i]);
			viewlist.add(imageView);
			
		}
		

		Context context = ShopDetial.this;
		SalonDetailAdapter pageadapter2 =new SalonDetailAdapter(viewlist,context);
		viewpager.setAdapter(pageadapter2);		
		
		
		//点数组
		pointarr=new ImageView[imgArray.length];
		//把点加入布局，和设置点状态
		for(int i=0;i<imgArray.length;i++){
			ImageView point=new ImageView(this);		
			
			//设置点大小
			point.setLayoutParams(new LayoutParams(10,10)); 
			pointarr[i]=point;
			//状态处理
			if(i==0){
				pointarr[i].setBackgroundResource(R.drawable.viewpage_point_focused);
			}else{
				pointarr[i].setBackgroundResource(R.drawable.viewpage_point_unfocused);
			}
			//加入到容器
			pointgroup.addView(pointarr[i]);
		}

		//viewpager设置监听器
		pointChangeListener pointListener=new pointChangeListener();
		viewpager.setOnPageChangeListener(pointListener);
	
		
	}
		
	
	private void initData() {
		if(receiveData!=null){
			float Score = Float.parseFloat(receiveData.getShop_level());
			int isFastcutCode = Integer.parseInt(receiveData.getShop_fastcut());
			shopId = receiveData.getId();
			String isFastcut;
			if(isFastcutCode==0){
				isFastcut = "否";
			}else{
				isFastcut = "是";
			}
			
			context = this.getApplicationContext();
			sh = new SharedUtils(context);
			Map<String,String> data = sh.read();
			if(data != null){
				 user_id1 = data.get("username").toString().trim();
			}
			
			mTitle.setText(receiveData.getName());
			mLocation.setText(receiveData.getShop_area());
			shopName.setText(receiveData.getName());
			shopTel.setText("电话号码：   "+receiveData.getShop_tel());
			shopRate.setRating(Score);
			shopRank.setText(receiveData.getShop_level()+"分");
			fastCut.setText("是否支持快剪：    "+isFastcut);
			shop_open_time.setText("营业时间：   "+ receiveData.getShop_open_time());
			
			
			GoodsHttpDownload goodHttpDownload = new GoodsHttpDownload(getApplicationContext(),shopId,mListView);
			goodHttpDownload.execute();
			
			
			shopTel.setOnClickListener(this);
			imgback.setOnClickListener(this);
			shopCollect.setOnClickListener(this);
			
			
			mListView.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("setOnItemClickListener被调用");
					Intent intent=new Intent();
					intent.setClass(ShopDetial.this,GoodsDetailActivity .class);
					
					System.out.println("被点击："+position);
					mDatalist1 = GoodsAdapter.mDatalist.get(position);
					System.out.println("mDatalist = ShopInfoAdapter.infos.get(position)="+mDatalist1);
					intent.putExtra("datas", mDatalist1);
					
					startActivity(intent);	
					
				}
			});
			
		}
		
	}


	private void initView() {
		//初始化
			viewpager =(ViewPager) findViewById(R.id.viewpager);
			pointgroup =(ViewGroup) findViewById(R.id.pointgroup);
			imgback = (TextView) findViewById(R.id.imgback_1);
			mTitle = (TextView) findViewById(R.id.tv_title);
			mLocation = (TextView) findViewById(R.id.shoplocation);
			shopName = (TextView) findViewById(R.id.shop_name);
			shopTel = (TextView) findViewById(R.id.shop_tel);
			shopRank = (TextView) findViewById(R.id.tv_score);
			shopRate =  (RatingBar) findViewById(R.id.rb_score);
			fastCut = (TextView) findViewById(R.id.shop_fastcut);
			mListView = (ListView) findViewById(R.id.good_listView);
			shop_open_time = (TextView) findViewById(R.id.shop_open_time);
			shopCollect = (ImageView) findViewById(R.id.img_collect);
			
			viewlist = new ArrayList<View>();	
		
	}


	/*
	 * viewpage监听器
	 */
	
	public class pointChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		//比如切换到第二张图片，相应索引的点为选择状态，其他为未选中状态
		public void onPageSelected(int arg0) {
			
			// TODO Auto-generated method stub
			for(int i=0;i<pointarr.length;i++){
				pointarr[arg0].setBackgroundResource(R.drawable.viewpage_point_focused);	
				if(arg0 !=i){
				pointarr[i].setBackgroundResource(R.drawable.viewpage_point_unfocused);	
				}
			}
			
		}
		
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shop_tel:
			Uri uri = Uri.parse("tel:"+receiveData.getShop_tel());   
			Intent intent = new Intent(Intent.ACTION_DIAL, uri);     
			startActivity(intent); 
			break;
			
		case R.id.imgback_1:
			finish();
			
		case R.id.img_collect:
			
			if(user_id1==""){
				Intent intent1 = new Intent(ShopDetial.this,LoginNull.class);
				startActivity(intent1);
			}else{
				shopCollect();
			}
			
		}
		
	}


	private void shopCollect() {
		final String shop_id = receiveData.getId().toString().trim();
		final String user_tel = user_id1.toString().trim();
		System.out.println("shop_id="+shop_id+" "+"user_tel="+user_tel);
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.ShopCollect_URL+"&user_tel="+user_tel+"&shop_id="+shop_id,new RequestCallBack<String>() {
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(ShopDetial.this, msg, Toast.LENGTH_SHORT ).show();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ResponseObject<ShopCollect> object = new GsonBuilder().create().fromJson(responseInfo.result,
						new TypeToken<ResponseObject<ShopCollect>>(){}.getType());
				System.out.println("object.getState()---"+object.getState());
				if(object.getState() == 1){
					Toast.makeText(ShopDetial.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
					
		
				}else{
					Toast.makeText(ShopDetial.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
				}
				
			}
		});
		
	}

}
