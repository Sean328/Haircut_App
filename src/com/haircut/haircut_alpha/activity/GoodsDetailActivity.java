package com.haircut.haircut_alpha.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.smart.SmartImageView;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.secondary.LoginNull;
import com.haircut.haircut_alpha.adapter.ShopInfoAdapter;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Cart;
import com.haircut.haircut_alpha.entity.Collect;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;
import com.haircut.haircut_alpha.http.CommentHttpDownloader;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsDetailActivity extends Activity implements OnClickListener{
	
	private TextView goodsName;
	private TextView shopName;
	private TextView goodsPrice;
	private SmartImageView topBanner;
	private TextView shopOpenTime;
	private TextView btn_collect;
	private TextView gouwuche;
	private TextView goumai;
	private ImageView back;
	
	private TextView shopName1;
	private TextView shop_area;
	private TextView shop_rank;
	private TextView shop_distance;
	private TextView commentShuliang;
	private SmartImageView shopImg;
	private ListView comment_listView;
	private RatingBar rb_score1;
	private Button btnShop;
	private TextView write_comment;
	
	private GoodsInfo goodsDetail_mDatalist = null;
	private Context context;
	private SharedUtils sh;
	private String user_id1 = null;
	
	private ArrayList<ShopInfo> shopInfo;
	public static List<ShopInfo> mDatalist_comment = new ArrayList<ShopInfo>();
	
	public static  String goods_id = null;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_goodsdetailactivity);
		
		mDatalist_comment.clear();
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			goodsDetail_mDatalist = (GoodsInfo) bundle.get("datas");
			System.out.println("goodsDetail_mDatalist---------------"+goodsDetail_mDatalist);
		}
		
		goods_id = goodsDetail_mDatalist.getGood_id();

		initView();
		
		initData();
		
		System.out.println("user_id1=="+user_id1);
	}



	private void initView() {
		context = this.getApplicationContext();
		sh = new SharedUtils(context);
		Map<String,String> data = sh.read();
		if(data != null){
			 user_id1 = data.get("username").toString().trim();
		}
		
		goodsName = (TextView) findViewById(R.id.goods_name);
		shopName = (TextView) findViewById(R.id.goods_shop_name);
		goodsPrice = (TextView) findViewById(R.id.goods_price);
		shopOpenTime = (TextView) findViewById(R.id.shop_open_time);
		btn_collect = (TextView) findViewById(R.id.tv_shoucang);
		gouwuche = (TextView) findViewById(R.id.tv_gouwuche);
		goumai = (TextView) findViewById(R.id.tv_goumai);
		topBanner = (SmartImageView) findViewById(R.id.goods_smartImageView_goodsdetail);
		back = (ImageView) findViewById(R.id.goods_img_back);
		
		
		//后来添加
		shopName1 = (TextView) findViewById(R.id.shop_name1);
		shop_area = (TextView) findViewById(R.id.tv_address);
		shop_rank = (TextView) findViewById(R.id.tv_score1);
		shop_distance = (TextView) findViewById(R.id.tv_distance1);
//		commentShuliang = (TextView) findViewById(R.id.tv_comment_shuliang);
		shopImg = (SmartImageView) findViewById(R.id.goods_img);
		comment_listView = (ListView) findViewById(R.id.listView_comment);
		rb_score1 = (RatingBar)findViewById(R.id.rb_score1);
		btnShop = (Button) findViewById(R.id.btn_shop);
		
	}
	
	
	private void initData() {
		shopInfo = (ArrayList<ShopInfo>) ShopInfoAdapter.infos;
		System.out.println("shopInfo==="+shopInfo);
		for(ShopInfo data:shopInfo){
			if(data.getId().equals(goodsDetail_mDatalist.getShop_id())){
				mDatalist_comment.add(data);
				System.out.println("mDatalist_comment===="+mDatalist_comment);
			}
		}
		
		
		ShopInfo info =  mDatalist_comment.get(0);

		System.out.println("info-------"+mDatalist_comment);
		
		
		
		//第一批数据
		goodsName.setText(goodsDetail_mDatalist.getGname());
		shopName.setText(goodsDetail_mDatalist.getShop_name());
		goodsPrice.setText("￥"+goodsDetail_mDatalist.getGprice());
		topBanner.setImageUrl(goodsDetail_mDatalist.getGimage(),R.drawable.tongyong,R.drawable.tongyong);	
		
		//第二批数据
		float Score = Float.parseFloat(info.getShop_level());
		shopName1.setText(info.getName());
		shop_area.setText(info.getShop_area());
		shop_distance.setText(info.getShop_distance()+"km");
		shop_rank.setText(info.getShop_level());
		shopImg.setImageUrl(info.getShop_img(),R.drawable.tongyong,R.drawable.tongyong);
		rb_score1.setRating(Score);
		
		
		CommentHttpDownloader commentHttpDownloader = new CommentHttpDownloader(comment_listView, this);
		commentHttpDownloader.execute();
	
		
		back.setOnClickListener(this);
		goumai.setOnClickListener(this);
		btn_collect.setOnClickListener(this);
		gouwuche.setOnClickListener(this);
		btnShop.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goods_img_back:
			finish();
			break;

		case R.id.tv_shoucang:
			
			if(user_id1==""){
				Intent intent = new Intent(GoodsDetailActivity.this,LoginNull.class);
				startActivity(intent);
			}else{
				collectGoods();
			}

			break;
			
		case R.id.tv_gouwuche:
			if(user_id1==""){
				Intent intent = new Intent(GoodsDetailActivity.this,LoginNull.class);
				startActivity(intent);
			}else{
			cartGoods();
			}
			break;
			
		case R.id.tv_goumai:
			if(user_id1==""){
				Intent intent = new Intent(GoodsDetailActivity.this,LoginNull.class);
				startActivity(intent);
			}else{
			
				Intent intent = new Intent(GoodsDetailActivity.this,OrderSubmitActivity.class);
				intent.putExtra("datas", goodsDetail_mDatalist);
				System.out.println("在跳转页面前goodsDetail_mDatalist="+goodsDetail_mDatalist);
				
				startActivity(intent);
			}
			break;
			
		case R.id.btn_shop:
			
			if(user_id1==""){
				Intent intent = new Intent(GoodsDetailActivity.this,LoginNull.class);
				startActivity(intent);
			}else{
			
				Intent intent1 = new Intent(GoodsDetailActivity.this,ShopDetial.class);
				ShopInfo info1 =  mDatalist_comment.get(0);
				intent1.putExtra("datas", info1);
				startActivity(intent1);
				break;
			}
			
			
		}
		
	}


	//添加收藏
	private void collectGoods() {

		final String goods_id = goodsDetail_mDatalist.getGood_sort().toString().trim();
		final String user_tel = user_id1.toString().trim();
		
		System.out.println("goods_id=="+goods_id+"   user_id=="+user_tel);
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.Collect_URL+"&goods_id="+goods_id+"&user_tel="+user_tel,new RequestCallBack<String>() {
				
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(GoodsDetailActivity.this, msg, Toast.LENGTH_SHORT ).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ResponseObject<Collect> object = new GsonBuilder().create().fromJson(responseInfo.result,
								new TypeToken<ResponseObject<Collect>>(){}.getType());
						System.out.println("object.getState()---"+object.getState());
						if(object.getState() == 1){
							Toast.makeText(GoodsDetailActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
							
							
							
						}else{
							Toast.makeText(GoodsDetailActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
						}
						
					}
				});
		
	}
	
	//添加购物车
	private void cartGoods() {
		final String goods_id = goodsDetail_mDatalist.getGood_id().toString().trim();
		final String user_tel = user_id1.toString().trim();
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.Cart_URL+"&goods_id="+goods_id+"&user_tel="+user_tel,new RequestCallBack<String>() {
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(GoodsDetailActivity.this, msg, Toast.LENGTH_SHORT ).show();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ResponseObject<Cart> object = new GsonBuilder().create().fromJson(responseInfo.result,
						new TypeToken<ResponseObject<Cart>>(){}.getType());
				System.out.println("object.getState()---"+object.getState());
				System.out.println("object.getData()---"+object.getDatas());
				if(object.getState() == 1){
					Toast.makeText(GoodsDetailActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
					
					
				}else{
					Toast.makeText(GoodsDetailActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
				}
				
			}
		});
		
	}
	
	
}
