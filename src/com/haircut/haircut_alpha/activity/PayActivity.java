package com.haircut.haircut_alpha.activity;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.secondary.OrderFinish;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class PayActivity extends Activity implements OnClickListener{
	
	private GoodsInfo pay_mDatalist = null;
	private TextView mGoods;
	private TextView mPrice;
	private Button btn_pay;
	private CheckBox yinhangka;
	private CheckBox weixin;
	private CheckBox zhifubao;
	
	public static int temp = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pay);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			pay_mDatalist = (GoodsInfo) bundle.get("datas");
		}
		
		initView();
		
//		if(yinhangka.isChecked()==true){
//			weixin.setClickable(false);
//			zhifubao.setClickable(false);
//		}else if(weixin.isChecked()==true){
//			zhifubao.setClickable(false);
//			yinhangka.setClickable(false);
//		}else if(zhifubao.isChecked()==true){
//			yinhangka.setClickable(false);
//			weixin.setClickable(false);
//		}

		yinhangka.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(yinhangka.isChecked()==true){
					weixin.setChecked(false);
					zhifubao.setChecked(false);
				}
			}
		});
		
		weixin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(weixin.isChecked()==true){
					zhifubao.setChecked(false);
					yinhangka.setChecked(false);
				}
				
			}
		});
		
		zhifubao.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if(zhifubao.isChecked()==true){
					yinhangka.setChecked(false);
					weixin.setChecked(false);
				}
			}
		});
		
	}

	private void initView() {
		mGoods = (TextView) findViewById(R.id.pay_shangpin);
		mPrice = (TextView) findViewById(R.id.pay_jiage);
		btn_pay = (Button) findViewById(R.id.pay_pay);
		yinhangka = (CheckBox) findViewById(R.id.ckb_yinhangka);
		weixin = (CheckBox) findViewById(R.id.ckb_weixin);
		zhifubao = (CheckBox) findViewById(R.id.ckb_zhifubao);
		
		mPrice.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		mGoods.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		mPrice.setText("гд"+pay_mDatalist.getGprice());
		mGoods.setText(pay_mDatalist.getGname()+"-"+pay_mDatalist.getShop_name());
		
		btn_pay.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_pay:
			Intent intent = new Intent(PayActivity.this,OrderFinish.class);
			
			intent.putExtra("datas", pay_mDatalist);
			
			startActivity(intent);
			break;
	
			
		}
		
	}

}
