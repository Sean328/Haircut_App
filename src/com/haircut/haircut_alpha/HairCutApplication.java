package com.haircut.haircut_alpha;

import com.hyphenate.easeui.controller.EaseUI;

import android.app.Application;


public class HairCutApplication extends Application{
	
	 @Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			EaseUI.getInstance().init(this, null);
		}

}
