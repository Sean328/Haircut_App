package com.haircut.haircut_alpha.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	
	/** Fragment当前状是否可用 */
	protected boolean isVisible;
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if(getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	
	
	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();		
	}
	
	
	/**
	 * 不可�?
	 */
	protected void onInvisible() {
		
		
	}
	
	
	/** 
	 * 延迟加载
	 * 子类必须重写此方�?
	 */
	protected abstract void lazyLoad();
}
