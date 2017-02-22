package com.haircut.haircut_alpha.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Cart;
import com.haircut.haircut_alpha.entity.Order;
import com.haircut.haircut_alpha.entity.Order.OrderInfo;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderSubmitActivity extends Activity implements OnClickListener{
	
	private GoodsInfo order_Datalist = null;
	private TextView order_GoodsName;
	private TextView order_ShopName;
	private TextView order_GoodsPrice;
	private TextView order_xiaoji;
	private TextView order_heji;
	private Button btn_Submit;

	private Context context;
	private SharedUtils sh;
	private String userTel;
	private TextView imgBack;
	private String str;
	private String str1;
	
	private Order order = null;
	public static String order_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ordersubmit);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			order_Datalist = (GoodsInfo) bundle.get("datas");
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		str= formatter.format(curDate);
		
		System.out.println("系统当前时间为："+str);
		
		initView();
		
		
	}

	private void initView() {
		
		context = this.getApplicationContext();
		sh = new SharedUtils(context);
		Map<String,String> data = sh.read();
		if(data != null){
			userTel = data.get("username").toString().trim();
		}
		
		order_GoodsName = (TextView) findViewById(R.id.order_goods_name);
		order_ShopName = (TextView) findViewById(R.id.order_shop_name);
		order_GoodsPrice = (TextView) findViewById(R.id.order_goods_price);
		order_xiaoji = (TextView) findViewById(R.id.order_xiaoji);
		order_heji = (TextView) findViewById(R.id.order_heji);
		btn_Submit = (Button) findViewById(R.id.oder_submit);
		imgBack = (TextView) findViewById(R.id.order_imgback);
		
		order_GoodsName.setText(order_Datalist.getGname());
		order_ShopName.setText(order_Datalist.getShop_name());
		order_GoodsPrice.setText("￥"+order_Datalist.getGprice());
		order_xiaoji.setText("￥"+order_Datalist.getGprice());
		order_heji.setText("￥"+order_Datalist.getGprice());
		
		btn_Submit.setOnClickListener(this);
		imgBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_imgback:
			finish();
			break;

		case R.id.oder_submit:
			
			orderSubmit();
			
			break;
		}
		
	}

	private void orderSubmit() {
		
		try {
			str1 = URLEncoder.encode(str, "utf-8").toString().trim();
			str1 = URLEncoder.encode(str1, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final String goods_id = order_Datalist.getGood_id().toString().trim();
		final String user_tel = userTel.toString().trim();
		final String orders_price = order_Datalist.getGprice();
		final String orders_time = str1;
		System.out.println("orders_time"+orders_time);
		

		new HttpUtils().send(HttpMethod.GET, CONSTS.Order_Submit_URL+"&goods_id="+goods_id+"&user_tel="+user_tel+"&orders_time="+orders_time+"&orders_price="+orders_price,
		new RequestCallBack<String>() {
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(OrderSubmitActivity.this, msg, Toast.LENGTH_SHORT ).show();
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				ResponseObject<Order> object = new GsonBuilder().create().fromJson(responseInfo.result,
						new TypeToken<ResponseObject<Order>>(){}.getType());
				System.out.println("object.getState()---"+object.getState());
				if(object.getState() == 1){
					
					
					Toast.makeText(OrderSubmitActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
					Intent intent = new Intent(OrderSubmitActivity.this,PayActivity.class);
					intent.putExtra("datas", order_Datalist);
					startActivity(intent);
					
					
				}else{
					Toast.makeText(OrderSubmitActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
				}
				
			}
		});
		
	}


	
}
