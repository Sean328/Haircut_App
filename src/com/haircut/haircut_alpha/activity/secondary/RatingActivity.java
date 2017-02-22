package com.haircut.haircut_alpha.activity.secondary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.MainActivity;
import com.haircut.haircut_alpha.activity.OrderSubmitActivity;
import com.haircut.haircut_alpha.activity.PayActivity;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.Order;
import com.haircut.haircut_alpha.entity.ResponseObject;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.fragment.Fragment_user;
import com.haircut.haircut_alpha.utils.GetUserTel;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.CursorUtils.FindCacheSequence;
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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class RatingActivity extends Activity{
	
	private GoodsInfo goods_Datalist = null;
	
	private TextView comment_score;
	private EditText comment_editText;
	private Button comment_submit;
	private RatingBar rat_comment;
	
	private String user_tel1;
	private String score;
	private Context context;
	private String timenow;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rating);
		
		
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			goods_Datalist = (GoodsInfo) bundle.get("datas");
			
			System.out.println("goods_id=="+goods_Datalist.getGood_id());
		}
		

		
		initView();
		initData();
		
	}


	private void initData() {
		context = this.getApplicationContext();
		user_tel1 = GetUserTel.getUer_tel(context,user_tel1);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		timenow= formatter.format(curDate);
		
	}


	private void initView() {

		comment_score = (TextView) findViewById(R.id.rar_score);
		comment_editText = (EditText) findViewById(R.id.edit_comment);
		comment_submit = (Button) findViewById(R.id.comment_submit);
		rat_comment = (RatingBar) findViewById(R.id.ratingBar1);
		
		rat_comment.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				if(fromUser){  
					score = Float.toString(rating);
					comment_score.setText(score);
	            } 
				
			}
		});
		
		comment_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				submitComment();
			}
		});
		
	}


	protected void submitComment() {
		
		String time = null;
		String comment = comment_editText.getText().toString();
		String comments = null;
		
		try {
			time = URLEncoder.encode(timenow, "utf-8").toString().trim();
			time = URLEncoder.encode(time, "utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			comments = URLEncoder.encode(comment, "utf-8");
			comments = URLEncoder.encode(comments, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("goods_id"+goods_Datalist.getGoods_id());
		
		final String goods_id = goods_Datalist.getGood_id();
		final String user_tel = user_tel1.trim();
		final String cpermoney = goods_Datalist.getGprice();
		final String name = user_tel;
		final String clevel = score;
		final String orders_id = goods_id;
		
		System.out.println("goods_id"+goods_id);
		System.out.println("user_tel"+user_tel);
		System.out.println("name"+name);
		System.out.println("cpermoney"+cpermoney);
		System.out.println("clevel"+clevel);
		System.out.println("comments"+comments);
		System.out.println("time"+time);
		
		new HttpUtils().send(HttpMethod.GET, CONSTS.Comments_Submit_URL+"&goods_id="+goods_id+"&user_tel="+user_tel+"&name="+user_tel+"&comments="+comments+"&clevel="+clevel+"&cpermoney="+cpermoney+"&time="+time+"&orders_id="+goods_id,
				new RequestCallBack<String>() {
					
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(RatingActivity.this, msg, Toast.LENGTH_SHORT ).show();
						
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ResponseObject<Order> object = new GsonBuilder().create().fromJson(responseInfo.result,
								new TypeToken<ResponseObject<Order>>(){}.getType());
						System.out.println("object.getState()---"+object.getState());
						if(object.getState() == 1){
							
							
							Toast.makeText(RatingActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
							Intent intent = new Intent(RatingActivity.this,MainActivity.class);
//							intent.putExtra("datas", order_Datalist);
							startActivity(intent);
							
							
						}else{
							Toast.makeText(RatingActivity.this, object.getMsg(), Toast.LENGTH_SHORT ).show();
						}
						
					}
				});
		
	}
	
}
