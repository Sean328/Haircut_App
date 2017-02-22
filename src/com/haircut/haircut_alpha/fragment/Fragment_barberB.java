package com.haircut.haircut_alpha.fragment;

import java.util.ArrayList;
import java.util.List;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.adapter.ListFragmentPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class Fragment_barberB extends Fragment implements OnClickListener{
	private RadioButton mFstBtn;
	private RadioButton mSndBtn;
	private RadioButton mThdBtn;
	
	private ViewPager mViewPager;
	private ListFragmentPagerAdapter mPagerAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	private final int FIRST_FRAGMENT = 0;
	private final int SECOND_FRAGMENT = 1;
	private final int THIRD_FRAGMENT = 2;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
			View view = inflater.inflate(R.layout.activity_fragment_barberb, null);
			
			
			initButton(view);
			initViewPager(view);
			return view;
				
	}
	
	/**
	 * 初始化按钮
	 */
	private void initButton(View parentView) {
		mFstBtn = (RadioButton)parentView.findViewById(R.id.id_rb_fst);
		mFstBtn.setOnClickListener(this);
		mSndBtn = (RadioButton)parentView.findViewById(R.id.id_rb_snd);
		mSndBtn.setOnClickListener(this);
		mThdBtn = (RadioButton)parentView.findViewById(R.id.id_rb_thd);
		mThdBtn.setOnClickListener(this);
	}
	
	
	/**
	 * 初始化ViewPager控件
	 */
	private void initViewPager(View parentView) {
		mViewPager = (ViewPager)parentView.findViewById(R.id.id_vp_viewpager);
		//关闭预加载，默认一次只加载一个Fragment
		mViewPager.setOffscreenPageLimit(1);
		//添加Fragment
		mFragments.add(CustomListFragment.newInstance(FIRST_FRAGMENT));
		mFragments.add(CustomListFragment.newInstance(SECOND_FRAGMENT));
		mFragments.add(CustomListFragment.newInstance(THIRD_FRAGMENT));
		//适配器
		mPagerAdapter = new ListFragmentPagerAdapter(getChildFragmentManager(), mFragments);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(onPageChangeListener);
	}

	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			//根据用户选中的按钮修改按钮样式
			switch (position) {
			case FIRST_FRAGMENT:
				mFstBtn.setChecked(true);
				mSndBtn.setChecked(false);
				mThdBtn.setChecked(false);
				break;

			case SECOND_FRAGMENT:
				mFstBtn.setChecked(false);
				mSndBtn.setChecked(true);
				mThdBtn.setChecked(false);
				break;
				
			case THIRD_FRAGMENT:
				mFstBtn.setChecked(false);
				mSndBtn.setChecked(false);
				mThdBtn.setChecked(true);
			break;
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {}
	};
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_rb_fst:
			mViewPager.setCurrentItem(FIRST_FRAGMENT);
			break;

		case R.id.id_rb_snd:
			mViewPager.setCurrentItem(SECOND_FRAGMENT);
			break;
			
		case R.id.id_rb_thd:
			mViewPager.setCurrentItem(THIRD_FRAGMENT);
			break;
		}
	}
   
	 
}
