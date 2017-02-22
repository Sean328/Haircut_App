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
    private FragmentManager fragmentManager;//����fragment����
    
    public static UserInfo mainUserInfo;
  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_content);
        ViewUtils.inject(this);
        
        
        //��ʼ��FragmentManager
        fragmentManager=getSupportFragmentManager();
          
        //����Ĭ��ѡ��
        main_home.setChecked(true);
        group.setOnCheckedChangeListener(this);
        //�л���ͬ��fragment
        changeFragment(new Fragment_home(), false);
        
    }
     
    
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home://��ҳ
			changeFragment(new Fragment_home(), true);
			break;
		case R.id.rb_barber://��
			changeFragment(new Fragment_barberB(), true);
			break;
		case R.id.rb_message://��Ϣ
			changeFragment(new Fragment_message(), true);
			break;
		case R.id.rb_users://��������
			changeFragment(new Fragment_user(), true);
			break;
		case R.id.rb_smartmatch://����ƥ��
			changeFragment(new Fragment_smartmatch(), true);
			break;

		default:
			break;
		}
	}
	//�л���ͬ��fragment
	public void changeFragment(Fragment fragment,boolean isFirst){
		//��������
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
				.setTitle("Ӧ����ʾ")
				.setMessage(
						"ȷ��Ҫ�˳�" + getResources().getString(R.string.app_name)
								+ "�ͻ�����")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AppManager.getInstance().AppExit(MainActivity.this);
						MainActivity.this.finish();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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
            //5�����ҵľ�����������Ŀ��λ�ã��ο������Դ�뼴�����
        	main_user.setChecked(true);
        }
        
        super.onResume();
	}
	
}
