package com.haircut.haircut_alpha.activity.secondary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.client.UserTokenHandler;

import com.google.gson.Gson;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.CitySelectActivity;
import com.haircut.haircut_alpha.activity.GoodsDetailActivity;
import com.haircut.haircut_alpha.activity.LoginActivity;
import com.haircut.haircut_alpha.activity.MainActivity;
import com.haircut.haircut_alpha.activity.settings.ModificationName;
import com.haircut.haircut_alpha.consts.CONSTS;
import com.haircut.haircut_alpha.entity.GoodsWrapper;
import com.haircut.haircut_alpha.entity.User;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.User.UserInfo;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.haircut.haircut_alpha.view.CircleTransform;
import com.haircut.haircut_alpha.view.Round;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySettings extends Activity implements OnClickListener{
	
	private static int CAMERA_REQUEST_CODE = 1;
	private static int GALLERY_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;
	
	
	private ImageView userImage;
	private ImageView userImageModif;
	private RelativeLayout user_nickname;
	private RelativeLayout user_phoneNumber;
	private RelativeLayout user_gender;
	private RelativeLayout user_address;
	private RelativeLayout user_signature;
	
	private TextView mNickName;
	private TextView mPhonenumber;
	private TextView mGender;
	private TextView mAddress;
	private TextView mSignature;
	private Button mButton;
	private TextView imgBack;
	
	//声明一个AlertDialog构造器
    private AlertDialog.Builder builder;
    
    private SharedUtils sh;
    private Context context;
    //定义一个图片文件
    private File img;
    private String gender = null;
    private String user_tel;
    
    private MyTask mTask;
    private User wrapper;
    private List<UserInfo> UserInfos;
    private UserInfo userInfo;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acticity_mymessage);
		
		initView();
		
		context = this.getApplicationContext();
		sh = new SharedUtils(context);
		
		//注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常 
		mTask = new MyTask();  
        mTask.execute(); 
		
	}

	private void initView() {
		
		userImage = (ImageView) findViewById(R.id.img_touxiang);
		userImageModif = (ImageView) findViewById(R.id.img_xiugai);
		user_nickname = (RelativeLayout) findViewById(R.id.user_nickname);
		user_phoneNumber = (RelativeLayout) findViewById(R.id.user_phoneNumber);
		user_gender = (RelativeLayout) findViewById(R.id.user_gender);
		user_address = (RelativeLayout) findViewById(R.id.user_address);
		user_signature = (RelativeLayout) findViewById(R.id.user_signature);
		mButton = (Button) findViewById(R.id.user_exit);
		imgBack = (TextView) findViewById(R.id.usermsg_imgback);
		
		mNickName = (TextView) findViewById(R.id.tv_nickname);
		mPhonenumber = (TextView) findViewById(R.id.tv_phonenumber);
		mGender = (TextView) findViewById(R.id.tv_gender);
		mAddress = (TextView) findViewById(R.id.tv_address);
		mSignature = (TextView) findViewById(R.id.tv_signature);
		
		userImage.setOnClickListener(this);
		userImageModif.setOnClickListener(this);
		user_nickname.setOnClickListener(this);
		user_gender.setOnClickListener(this);
		user_address.setOnClickListener(this);
		user_signature.setOnClickListener(this);
		mButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_touxiang:
			//显示对话框
			showSimpleListDialog(view);
			break;
			
		case R.id.img_xiugai:
			showSimpleListDialog(view);
			break;
			
		case R.id.user_nickname:
			Intent intent1 = new Intent(MySettings.this,ModificationName.class);
			startActivity(intent1);
			break;
			
		case R.id.user_gender:
			showGenderSelect(view);
			break;
			
		case R.id.user_address:
			Intent intent2 = new Intent(MySettings.this,CitySelectActivity.class);
			startActivityForResult(intent2,MyUtils.RequestCityCode);
			break;
			
		case R.id.user_exit:
			
			context = this.getApplicationContext();
			sh = new SharedUtils(context);
			sh.clearData();
			int a = 5;
			Intent intent = new Intent(MySettings.this,MainActivity.class);
			intent.putExtra("page", a);
//			this.finish();
			startActivity(intent);
			break;

		case R.id.tv_signature:
			
			break;
		case R.id.usermsg_imgback:
			finish();
		}
		
	}
	
	private class MyTask extends AsyncTask<String, Void, String> {
		
		private URL url;
		private HttpURLConnection conn;

		@Override
		protected String doInBackground(String... arg0) {

			StringBuffer sb = new StringBuffer();
			String line = null;
			BufferedReader buffer = null;
			try {
				System.out.println("user_tel="+user_tel);
				if(user_tel==null){
					System.out.println("user_tel="+user_tel);
					Map<String,String> data = sh.read();
					user_tel = data.get("username");
				}
				
				url = new URL(CONSTS.User_Info_URL+"&user_tel="+user_tel);
				
				conn = (HttpURLConnection) url.openConnection();
				
				buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while ((line = buffer.readLine())!=null) {
					sb.append(line);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(buffer != null)
						buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return sb.toString();
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			Gson gson = new Gson();
			wrapper = new User();
			wrapper= gson.fromJson(result, User.class);
			
			UserInfos = wrapper.datas;
			
			System.out.println("能否在这拿到数据？"+wrapper.datas);
			System.out.println("UserInfos"+UserInfos);
			userInfo = UserInfos.get(0);
			
			mNickName.setText(userInfo.getUser_name());
			mPhonenumber.setText(userInfo.getUser_tel());
			mAddress.setText(userInfo.getUser_address());
			String gender;
			String xingbie = userInfo.getUser_gender();
			if (xingbie!=null){
				int i = Integer.parseInt(xingbie);
				if(i==0){
					gender = "男";
				}else{
					gender = "女";
				}
				mGender.setText(gender);
			}
			
			
			Bitmap bm = sh.getBitmapFromSharedPreferences();
			if(bm!=null){
				userImage.setImageBitmap(bm);
			}else{
				Picasso.with(context).load(userInfo.getUser_img()).transform(new CircleTransform()).into(userImage);
			}
		}
		
	}
	

	private void showGenderSelect(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MySettings.this);
        builder.setTitle("请选择性别");
        final String[] sex = {"男", "女"};
        
        int i;
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            	if(which == 0){
            		gender = "男";
            	}else{
            		gender = "女";
            	}
                System.out.println("which= "+which);
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            	SharedUtils.putGender(context, gender);
            	mGender.setText(gender);
            	
            	sendGender(gender);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            	
            }
        });
        builder.show();
		
	}

	protected void sendGender(String gender2) {
		String sendGender = null;
		if(gender=="男"){
			sendGender = "0";
		}else if(gender=="女"){
			sendGender = "1";
		}
		System.out.println("sendGender = "+sendGender);
		
		String upLoadAddress = CONSTS.User_Gender_URL+"&user_tel="+user_tel+"&user_gender="+sendGender;
		new HttpUtils().send(HttpMethod.GET, upLoadAddress, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
			}
		});
		
	}

	private void showSimpleListDialog(View view) {
		 builder=new AlertDialog.Builder(this);
	        builder.setIcon(R.drawable.ic_launcher);
	        builder.setTitle("选择头像");

	        /**
	         * 设置内容区域为简单列表项
	         */
	        final String[] Items={"选择相册","打开相机"};
	        builder.setItems(Items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialogInterface, int i) {
	            	if(i == 0){
	            		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	            		intent.setType("image/*");
	            		startActivityForResult(intent, GALLERY_REQUEST_CODE);
//	            		doTakePhoto();
	            	}else if (i == 1) {
	            		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            		startActivityForResult(intent, CAMERA_REQUEST_CODE);
//	            		doPickPhotoFromGallery();
					}
	            	
	                Toast.makeText(getApplicationContext(), "You clicked "+Items[i], Toast.LENGTH_SHORT).show();
	            }
	        });
	        builder.setCancelable(true);
	        AlertDialog dialog=builder.create();
	        dialog.show();
		
	}
	
	private Uri saveBitmap(Bitmap bm){
		File tmpDir = new File(Environment.getExternalStorageDirectory()+"/com.haircut.haircut_alpha");
		if(!tmpDir.exists()){
			tmpDir.mkdir();
		}
		img = new File(tmpDir.getAbsolutePath() + "haircut_user.png");
		try {
			FileOutputStream fos = new FileOutputStream(img);
			bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(img);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Uri convertUri(Uri uri){
		InputStream is = null;
		try {
			is = getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void startImageZoom(Uri uri){
		Intent intent  = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri,"image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == CAMERA_REQUEST_CODE){
			if(data == null){
				return;
			}else{
				Bundle extras = data.getExtras(); 
				if(extras != null){
					Bitmap bm = extras.getParcelable("data");
					//将bitmap转换成uri
					Uri uri = saveBitmap(bm);
					startImageZoom(uri);
					
				}
			}
		}else if(requestCode == GALLERY_REQUEST_CODE){
			if(data == null){
				return;
			}
			Uri uri;
			uri = data.getData();//获取到的为content类型的uri
			Uri fileUri = convertUri(uri);
			startImageZoom(fileUri);
			
		}else if(requestCode == CROP_REQUEST_CODE){
			if(data == null){
				return;
			}
			
			Bundle extras = data.getExtras();
            if(extras == null){
                return;
            }
			Bitmap bm = extras.getParcelable("data");
			Bitmap bitMap = Round.toRoundBitmap(bm);
			userImage.setImageBitmap(bitMap);
			SharedUtils.saveBitmapToSharedPreferences(this,bitMap);
			sendImage(bitMap);
			
		}else if(resultCode == MyUtils.RequestCityCode){
			mAddress.setText(data.getStringExtra("lngCityName"));
			String address = data.getStringExtra("lngCityName");
			SharedUtils.putAddress(context, address);
			sendAddress(address);
		}
	}

	private void sendAddress(String address) {
		String str1 = null;
		
		try {
			str1 = URLEncoder.encode(address, "utf-8").toString().trim();
			str1 = URLEncoder.encode(str1, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("user_tel="+user_tel+"&&user_address="+str1);
		
		String upLoadAddress = CONSTS.User_Address_URL+"&user_tel="+user_tel+"&user_address="+str1;
		new HttpUtils().send(HttpMethod.GET, upLoadAddress, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
			}
		});
		
	}

	private void sendImage(Bitmap bitMap) {
		String uploadHost=CONSTS.User_Image_URL+"&user_tel="+user_tel;  //服务器接收地址  
    	RequestParams params=new RequestParams();  
    	params.addBodyParameter("msg","上传图片");   
    	params.addBodyParameter("img", new File(img.getAbsolutePath()));  //filePath是手机获取的图片地址  
    	System.out.println("File(img.getAbsolutePath())==="+img.getAbsolutePath());
    	sendImgToServer(params,uploadHost);
    	System.out.println("params="+params);
    	System.out.println("uploadHost="+uploadHost);
	}

	private void sendImgToServer(RequestParams params, String uploadHost) {
		new HttpUtils().send(HttpRequest.HttpMethod.POST,uploadHost, params,new RequestCallBack<String>() {  
	        @Override  
	        public void onStart() {  
	            //上传开始  
	        	System.out.println("开始上传");
	        }  
	        @Override  
	        public void onLoading(long total, long current,boolean isUploading) {  
	            //上传中  
	        	System.out.println("上传中");
	        }  
	        @Override  
	        public void onSuccess(ResponseInfo<String> responseInfo) {  
	            //上传成功，这里面的返回值，就是服务器返回的数据  
	            //使用 String result = responseInfo.result 获取返回值  
	        	System.out.println("上传成功");
	        	System.out.println(responseInfo.result);
	        }  
	        @Override  
	        public void onFailure(HttpException error, String msg) {  
	            //上传失败  
	        	System.out.println("上传失败");
	        }  
	    }); 
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK)
        {  
			int a = 5;
            Intent intent = new Intent(MySettings.this,MainActivity.class);
            intent.putExtra("page", a);
            this.finish();
            startActivity(intent);
        }
        return false;

	}

	@Override
	protected void onStart() {
		super.onStart();

		
		Map<String,String> data = sh.read();
		
		String nickName = data.get("nickname");
		String uerTel = data.get("username");
		String userGender = data.get("gender");
		String userAddress = data.get("address");
		Bitmap bm = sh.getBitmapFromSharedPreferences();
		
		user_tel = uerTel;
		
		if(nickName!=" "){
			mNickName.setText(nickName);
		}
		if(uerTel!=" "){
			mPhonenumber.setText(uerTel);
		}
		if(userGender!= " "){
			mGender.setText(userGender);
		}
		if(userAddress != " "){
			mAddress.setText(userAddress);
		}
		if(bm!=null){
			userImage.setImageBitmap(bm);
		}
	}
	
}
