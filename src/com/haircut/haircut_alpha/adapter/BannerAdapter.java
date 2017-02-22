package com.haircut.haircut_alpha.adapter;

import java.util.List;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.Home_huodong;
import com.haircut.haircut_alpha.entity.BannerWrapper.BannerInfo;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;




public class BannerAdapter extends PagerAdapter{

	private List<BannerInfo> banner;
	private Context context;
	private BitmapUtils utils;
	
	public BannerAdapter(Context context, List<BannerInfo> banner) {
		this.context = context;
		this.banner = banner;
		System.out.println("bannerinfo = " +banner);
		
		utils = new BitmapUtils(context);
		utils.configDefaultLoadingImage(R.drawable.hdp1);//设置加载过程中默认显示的图片
		
	}

	@Override
	public int getCount() {
		return banner.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView image = new ImageView(context);
		image.setImageResource(R.drawable.hdp1);
		image.setScaleType(ScaleType.FIT_XY);
		
		BannerInfo info = banner.get(position);
		utils.display(image,info.getImage());
		container.addView(image);
		
		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		
		return image;
		

	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}
	
	
}
