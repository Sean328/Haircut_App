package com.haircut.haircut_alpha.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smart.SmartImageView;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShopInfoAdapter extends BaseAdapter {
	
	public static List<ShopInfo> infos = new ArrayList<ShopInfo>();
	private Context context;

	public ShopInfoAdapter(Context context,List<ShopInfo>shopinfo) {

		this.context = context;
		this.infos = shopinfo;
		
		System.out.println("info = shopInfo = " +infos);
	}
	
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	if (observer != null) {
	       super.unregisterDataSetObserver(observer);
	   }
	}
	
	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View mView;
		mView = convertView;
		mView = View.inflate(context, R.layout.shop_list_item, null);
		
		SmartImageView smartImageView = (SmartImageView) mView.findViewById(R.id.smartImageView);
		TextView name = (TextView) mView.findViewById(R.id.tv_title);
		TextView introduce = (TextView) mView.findViewById(R.id.tv_intro);
		TextView area = (TextView) mView.findViewById(R.id.tv_address);
		TextView distance = (TextView) mView.findViewById(R.id.tv_distance);
		RatingBar rb_score = (RatingBar) mView.findViewById(R.id.rb_score);
		
		ShopInfo shopInfo = infos.get(position);
		float Score = Float.parseFloat(shopInfo.getShop_level());
		//1、请求的URL地址  2、显示请求失败的图片  3、正在请求的图片
				smartImageView.setImageUrl(shopInfo.getShop_img(),R.drawable.tongyong,R.drawable.tongyong);
				name.setText(shopInfo.getName());
				introduce.setText(shopInfo.getShop_intro());
				area.setText(shopInfo.getAddress());
				distance.setText(shopInfo.getShop_distance()+" km");
				rb_score.setRating(Score);
				
		return mView;
	}
	

}
