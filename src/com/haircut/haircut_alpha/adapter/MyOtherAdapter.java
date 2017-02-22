package com.haircut.haircut_alpha.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.smart.SmartImageView;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Cart.CartInfo;
import com.haircut.haircut_alpha.entity.Collect;
import com.haircut.haircut_alpha.entity.Collect.CollectInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.Order.OrderInfo;
import com.haircut.haircut_alpha.entity.ShopCollect.ShopCollectInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyOtherAdapter extends BaseAdapter{
	
	public static List<OrderInfo> order_infos;
	public static List<CartInfo>  cart_infos;
	public static List<ShopCollectInfo>  shopCollect_infos;
	public static List<CollectInfo> collect_infos;
	private Context context;
	private String MyURL = null;
	public static List<GoodsInfo> mDatalist_order = new ArrayList<GoodsInfo>();
	public static List<CartInfo> mDatalist_cart = new ArrayList<CartInfo>();
	
	int i = 0;

	public MyOtherAdapter(Context context,List<OrderInfo> orderInfo,String MyURL,int i) {
		this.context = context;
		this.order_infos = orderInfo;
		this.MyURL = MyURL;
		this.i = i;
	}

	public MyOtherAdapter(Context context,String MyURL,List<CartInfo> cartInfo,int i) {
		this.context = context;
		MyOtherAdapter.cart_infos = cartInfo;
		this.MyURL = MyURL;
		this.i = i;
	}

	public MyOtherAdapter(String MyURL,List<ShopCollectInfo> shopCollectInfo,Context context,int i) {
		this.context = context;
		this.shopCollect_infos = shopCollectInfo;
		this.MyURL = MyURL;
		this.i = i;
	}



	public MyOtherAdapter(int i, String MyURL, List<CollectInfo> collectInfo,Context context) {
		this.context = context;
		this.collect_infos = collectInfo;
		this.MyURL = MyURL;
		this.i = i;
	}

	
	
	@Override
	public int getCount() {
		int a = 0;
		switch (i) {
		case 1:
			a = order_infos.size();
			break;
		case 2:
			a = cart_infos.size();
			break;
		case 3:
			a = shopCollect_infos.size();
			break;
			
		case 4:
			a = collect_infos.size();
			break;
		}
		
		System.out.println("a = "+a);
		
		return a;
	}

	@Override
	public Object getItem(int position) {
		Object b = null;
		switch (i) {
		case 1:
			b = order_infos.get(position);
			break;
		case 2:
			b = cart_infos.get(position);
			break;
		case 3:
			b = shopCollect_infos.get(position);
			break;
		case 4:
			b = collect_infos.get(position);
			break;
		}
		return b;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

//	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	   

		View mView = null;
		mView = convertView;
		
		if(MyURL==CONSTS.Order_Return_URL){
			
			System.out.println("程序进来了吗？？？？");
			mView = adapteOrder(mView,position);
		}else if(MyURL==CONSTS.Cart_Return_URL){
			mView = adapteCart(mView,position);
		}else if(MyURL==CONSTS.ShopCollect_Return_URL){
			mView = adapteShopCollect(mView,position);
		}else if(MyURL==CONSTS.Collect_Return_URL){
			mView = adapteCollect(mView,position);
		}
		
		
		
		return mView;
	}






	private View adapteCollect(View mView, int position) {
		
		mView = View.inflate(context, R.layout.mycart_list_item, null);
		
		SmartImageView collect_img = (SmartImageView) mView.findViewById(R.id.cart_image);
		TextView collect_name = (TextView) mView.findViewById(R.id.cart_name);
		TextView collect_price = (TextView) mView.findViewById(R.id.cart_jiage);
		TextView collect_shop_name = (TextView) mView.findViewById(R.id.cart_shopName);
		
		CollectInfo collectInfo = collect_infos.get(position);
		
		collect_img.setImageUrl(collectInfo.getGimage(),R.drawable.tongyong,R.drawable.tongyong);
		collect_name.setText(collectInfo.getGname());
		collect_price.setText("￥"+collectInfo.getGprice());
		collect_shop_name.setText(collectInfo.getShop_name());
		
		return mView;
	}

	private View adapteShopCollect(View mView, int position) {
		mView = View.inflate(context, R.layout.myshopcollect_list_item, null);
		
		SmartImageView shopcollect_img = (SmartImageView) mView.findViewById(R.id.shopcollect_smartImageView);
		TextView shopcollect_shopName = (TextView) mView.findViewById(R.id.shopcollect_name);
		TextView shopcollect_intro = (TextView) mView.findViewById(R.id.shopcollect_intro);
		TextView shopcollect_address = (TextView) mView.findViewById(R.id.shopcollect_address);
		TextView shopcollect_distance = (TextView) mView.findViewById(R.id.shopcollect_distance);
		RatingBar shopcollect_score = (RatingBar) mView.findViewById(R.id.shopcollect_score);
		
		ShopCollectInfo shopCollectInfo = shopCollect_infos.get(position);
		
		float Score = Float.parseFloat(shopCollectInfo.getShop_level());
		
		shopcollect_img.setImageUrl(shopCollectInfo.getShop_img(),R.drawable.tongyong,R.drawable.tongyong);
		shopcollect_shopName.setText(shopCollectInfo.getShopname());
		shopcollect_address.setText(shopCollectInfo.getShop_area());
		shopcollect_distance.setText(shopCollectInfo.getShop_distance()+"km");
		shopcollect_score.setRating(Score);
		
		return mView;
	}

	private View adapteCart(View mView, int position) {
	
		mView = View.inflate(context, R.layout.mycart_list_item, null);
		SmartImageView cart_img = (SmartImageView) mView.findViewById(R.id.cart_image);
		TextView cart_name = (TextView) mView.findViewById(R.id.cart_name);
		TextView cart_price = (TextView) mView.findViewById(R.id.cart_jiage);
		TextView cart_shop_name = (TextView) mView.findViewById(R.id.cart_shopName);

		CartInfo cartInfo = cart_infos.get(position);
		
		cart_img.setImageUrl(cartInfo.getGimage(),R.drawable.tongyong,R.drawable.tongyong);
		cart_name.setText(cartInfo.getGname());
		cart_price.setText("￥"+cartInfo.getGprice());
		cart_shop_name.setText(cartInfo.getShop_name());
		
		return mView;
	}

	private View adapteOrder(View mView,int position) {
		
		System.out.println("程序进来啦！");
		
		mView = View.inflate(context, R.layout.myorder_list_item, null);
		
		SmartImageView order_img = (SmartImageView) mView.findViewById(R.id.orders_image);
		TextView orderName = (TextView) mView.findViewById(R.id.orders_name);
		TextView orderPrice = (TextView) mView.findViewById(R.id.orders_jiage);
		TextView orderTime = (TextView) mView.findViewById(R.id.orders_time);
		TextView orderShopName = (TextView) mView.findViewById(R.id.orders_shopName);
     	Button btn_Order = (Button) mView.findViewById(R.id.orders_pays);
		
		OrderInfo orderInfo = order_infos.get(position);
		
		order_img.setImageUrl(orderInfo.getGimage(),R.drawable.tongyong,R.drawable.tongyong);
		orderName.setText(orderInfo.getGname());
		orderPrice.setText("￥"+orderInfo.getGprice());
		orderTime.setText(orderInfo.getOrders_time());
		orderShopName.setText(orderInfo.getShop_name());
		
		
		return mView;
		
	}





}
