package com.haircut.haircut_alpha.adapter;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.example.smart.SmartImageView;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.entity.UserInfoWrapper;
import com.haircut.haircut_alpha.entity.UserInfoWrapper.UserInfo;
import com.haircut.haircut_alpha.view.CircleTransform;
import com.haircut.haircut_alpha.view.Round;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyMessageAdapter extends BaseAdapter{
	private List<UserInfo> infos;
	private Context context;
	
	public MyMessageAdapter(Context context,List<UserInfo>userinfo) {
		this.context = context;
		this.infos = userinfo;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView;
		if(convertView!=null){
			mView = convertView;
		}else {
			mView = View.inflate(context, R.layout.list_message, null);
		}

		SmartImageView smartImageView = (SmartImageView) mView.findViewById(R.id.smartImageView);
		TextView name = (TextView) mView.findViewById(R.id.name);
		TextView signature = (TextView) mView.findViewById(R.id.signature);
		TextView shop = (TextView) mView.findViewById(R.id.shop_from);
		
		UserInfoWrapper.UserInfo userInfo = infos.get(position);
		
		//1、请求的URL地址  2、显示请求失败的图片  3、正在请求的图片
		
//		smartImageView.setImageUrl(userInfo.getHead(),R.drawable.test2,R.drawable.lifashi);
		Picasso.with(context).load(userInfo.getHead()).transform(new CircleTransform()).into(smartImageView);
//		smartImageView.setDrawingCacheEnabled(true);

//		
//		smartImageView.setImageBitmap(cBitmap);
//		
		
		name.setText(userInfo.getName());
		signature.setText(userInfo.getSignature());
		shop.setText(userInfo.getShop());
		
		return mView;
	}


}
