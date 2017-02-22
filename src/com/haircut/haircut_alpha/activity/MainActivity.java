package com.haircut.haircut_alpha.activity;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.User.UserInfo;
import com.haircut.haircut_alpha.fragment.Fragment_barberB;
import com.haircut.haircut_alpha.fragment.Fragment_home;
import com.haircut.haircut_alpha.fragment.Fragment_message;
import com.haircut.haircut_alpha.fragment.Fragment_smartmatch;
import com.haircut.haircut_alpha.fragment.Fragment_user;
import com.haircut.haircut_alpha.utils.AppManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{

	@ViewInject(R.id.rg_group)
	private RadioGroup group;
    @ViewInject(R.id.rb_home)
    private RadioButton main_home;
    @ViewInject(R.id.rb_users)
    private RadioButton main_user;
    private FragmentManager fragmentManager;//管理fragment的类
    
    public static UserInfo mainUserInfo;
  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_content);
        ViewUtils.inject(this);
        
        
        //初始化FragmentManager
        fragmentManager=getSupportFragmentManager();
          
        //设置默认选中
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        //切换不同的fragment
        changeFragment(new Fragment_home(), false);
        
    }
     
    
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home://首页
			changeFragment(new Fragment_home(), true);
			break;
		case R.id.rb_barber://理发
			changeFragment(new Fragment_barberB(), true);
			break;
		case R.id.rb_message://消息
			changeFragment(new Fragment_message(), true);
			break;
		case R.id.rb_users://个人中心
			changeFragment(new Fragment_user(), true);
			break;
		case R.id.rb_smartmatch://智能匹配
			changeFragment(new Fragment_smartmatch(), true);
			break;

		default:
			break;
		}
	}
	//切换不同的fragment
	public void changeFragment(Fragment fragment,boolean isFirst){
		//开启事务
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		transaction.replace(R.id.vp_content, fragment);
		if(!isFirst){
			transaction.addToBackStack(null);
		}
		
		transaction.commit();
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			showExitDialog();
		}

		return super.onKeyDown(keyCode, event);
	}
	
	private void showExitDialog() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("应用提示")
				.setMessage(
						"确定要退出" + getResources().getString(R.string.app_name)
								+ "客户端吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AppManager.getInstance().AppExit(MainActivity.this);
						MainActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).show();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		
		Bundle bundle = getIntent().getExtras();
		String cityName;
		if(bundle != null){
			mainUserInfo = (UserInfo) bundle.get("userdatas");
			cityName = (String) bundle.get("lngCityName");
		}
		
		int id = 1;
		id = getIntent().getIntExtra("page", 1);

        if (id == 5) {
        	changeFragment(new Fragment_user(), true);
            //5代表”我的京东“所在条目的位置，参考下面的源码即可理解
        	main_user.setChecked(true);
        }
        
        super.onResume();
	}
	
}
