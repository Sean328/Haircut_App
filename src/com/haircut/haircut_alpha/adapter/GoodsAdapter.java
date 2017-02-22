package com.haircut.haircut_alpha.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smart.SmartImageView;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;

public class GoodsAdapter extends BaseAdapter{
	public static List<GoodsInfo> infos;
	private Context context;
	private String page;
	private String shopId;
	public static List<GoodsInfo> mDatalist = new ArrayList<GoodsInfo>();
	
	
	public GoodsAdapter(Context context,List<GoodsInfo> goodsInfo,String page) {
		this.context = context;
		this.infos = goodsInfo;
		this.page = page;
		mDatalist.clear();
		initData();
	}
	
	public GoodsAdapter(String shopId,Context context,List<GoodsInfo> goodsInfo) {
		this.context = context;
		this.infos = goodsInfo;
		this.shopId = shopId;
		mDatalist.clear();
		initData();
	}

	
	private void initData() {
		
		System.out.println("infos=="+infos);
		System.out.println("page=="+page);
		System.out.println("shopId=="+shopId);
		System.out.println("mDatalist=="+mDatalist);
		
		
		if(page!=null){
			System.err.println("程序走到了从分类页面接受数据");
			for(GoodsInfo data:infos){
				if(data.getGood_sort().equals(page)){
					mDatalist.add(data);
					System.out.println("mDatalist -------"+mDatalist);
				}
			}
		}else{
			System.err.println("程序走到了从店铺页面接受数据");
			for(GoodsInfo data:infos){
				if(data.getShop_id().equals(shopId)){
					mDatalist.add(data);
					System.out.println("mDatalist -------"+mDatalist);
				}
			}
		}
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatalist.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatalist.get(position);
	}
	@Override
	public long getItemId(int  position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		SmartImageView smartImageView = null;
		TextView shopName = null;
		TextView goodsPrice = null;
		TextView goodsName = null;
		Button btnBuy = null;
		
		System.out.println("position-----------"+position);
		
		GoodsInfo goodsInfo = mDatalist.get(position);
		
		View mView;
		mView = convertView;
		if(page!=null){
			System.out.println("goodsInfo------"+goodsInfo);
			mView = View.inflate(context, R.layout.goods_list_item, null);
			
			smartImageView = (SmartImageView) mView.findViewById(R.id.goods_smartImageView);
			goodsName = (TextView) mView.findViewById(R.id.tv_goods_title);
			shopName = (TextView) mView.findViewById(R.id.tv_dianpu);
			goodsPrice = (TextView) mView.findViewById(R.id.tv_jiage);
			
			
			smartImageView.setImageUrl(goodsInfo.getGimage(),R.drawable.tongyong,R.drawable.tongyong);
			goodsName.setText(goodsInfo.getGname());
			shopName.setText(goodsInfo.getShop_name());
			goodsPrice.setText("￥ "+goodsInfo.getGprice());
			
		}else{
			mView = View.inflate(context, R.layout.shop_goods_list_item, null);
			smartImageView = (SmartImageView) mView.findViewById(R.id.goods_smartImageView1);
			goodsName = (TextView) mView.findViewById(R.id.tv_goods_title1);
			goodsPrice = (TextView) mView.findViewById(R.id.tv_jiage1);
			btnBuy = (Button) mView.findViewById(R.id.btn_buy);
			
			
			smartImageView.setImageUrl(goodsInfo.getGimage(),R.drawable.tongyong,R.drawable.tongyong);
			goodsName.setText(goodsInfo.getGname());
			System.out.println("goodsInfo.getGname()"+goodsInfo.getGname());
			goodsPrice.setText("￥ "+goodsInfo.getGprice());
		}
		
		
		return mView;
	}

}
