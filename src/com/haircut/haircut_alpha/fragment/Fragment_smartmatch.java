package com.haircut.haircut_alpha.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.google.gson.Gson;
import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.secondary.ImageDetailsActivity;
import com.haircut.haircut_alpha.consts.Images;
import com.haircut.haircut_alpha.entity.BannerWrapper;
import com.haircut.haircut_alpha.entity.BannerWrapper.BannerInfo;
import com.haircut.haircut_alpha.utils.FaceHelper;
import com.haircut.haircut_alpha.utils.ImageLoader;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class Fragment_smartmatch extends Fragment implements OnClickListener,OnPageChangeListener{
	
	// 标志变量
		private static final int GALLERY_REQUEST_CODE = 897;
		private static int CAMERA_REQUEST_CODE = 896;
		private static final int SUCCESS = 1;
		private static final int ERROR = 0;
		private Dialog setHeadDialog;  
		private View mDialogView;  
		private Button mButton;
		private Button mDoAction;
		private Button btn_saveImg;
		// 图片路径
		private String mPicStr;
		
		private ImageView mImageView;
		private ProgressDialog mDialog;
		
		private TextView mGender;
		private TextView mAge;
		private TextView mSmile;
		private TextView mTips;
		
		private Bitmap mBitmap;
		
//		private static final String TAG = "SmartMatchFragment";
		private ImageLoader  imageLoader = ImageLoader.getInstance();  //获取图片进行管理的工具类实例。
		private ViewPager viewpager;
//		private ArrayList<View> viewList;
		
		private String fileName = "cacheFile";
		public static int PhotoCount;
		public static List<BannerInfo> bannerData;
		
		private String getGender;
		private int getAge;
		private String ImgUrl;
		private String imgSavePath;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view=inflater.inflate(R.layout.activity_fragment_smartmatch, container,false);
			
			initView(view);
			
			return view;
		}

		private void initView(View view) {
			mButton = (Button) view.findViewById(R.id.btn_selectImg);
			mDoAction = (Button) view.findViewById(R.id.btn_jiance);
			mImageView = (ImageView) view.findViewById(R.id.imageView1);
			mGender = (TextView) view.findViewById(R.id.tv_gender_sm);
			mAge = (TextView) view.findViewById(R.id.tv_age_sm);
			mSmile = (TextView) view.findViewById(R.id.tv_smile_sm);
			mTips = (TextView) view.findViewById(R.id.tv_tip_sm);
			btn_saveImg = (Button) view.findViewById(R.id.btn_savepic);
			
			mDialog = new ProgressDialog(getActivity());
	        mDialog.setMessage("系统检测识别中，请稍后..");
	        
	        
	        viewpager = (ViewPager) view.findViewById(R.id.sm_viewpager); //获取viewpager
	        

			mButton.setOnClickListener(this);
			mDoAction.setOnClickListener(this);
			btn_saveImg.setOnClickListener(this);
		}
		
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			super.onActivityResult(requestCode, resultCode, intent);
	        if (requestCode == GALLERY_REQUEST_CODE) {
	            if (intent != null) {
	                Uri uri = intent.getData();
	                
	                System.out.println("相册返回的uri+"+uri);
	                
	                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
	                if (cursor.moveToFirst()) {
		                int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		                mPicStr = cursor.getString(index);
		                cursor.close();
		                    
		                reSize(mPicStr);
		
		                mDoAction.setEnabled(true);
		                setHeadDialog.dismiss();

	               }
	           }

	        }else if(requestCode == CAMERA_REQUEST_CODE ){
	        	if(intent == null){
	                return;
	            }else{
	            	
	            	Bundle extras = intent.getExtras();
	            	mBitmap = (Bitmap) extras.get("data");
	        
	                mImageView.setImageBitmap(mBitmap);  //设置照片现实在界面上
	                mDoAction.setEnabled(true);
	                setHeadDialog.dismiss();
		                
	            }
	        }
		}
		
		

		private void reSize(String mPicStr) {
			// 根据获取到的图片路径，获取图片并进行图片压缩
	       BitmapFactory.Options options = new BitmapFactory.Options();
	       // 当Options的inJustDecodeBounds属性设置为true时，不会显示图片，只会返回该图片的具体数据
	       options.inJustDecodeBounds = true;
	       // 根据所选实际的宽高计算压缩比例并将图片压缩
	       BitmapFactory.decodeFile(mPicStr, options);
	       Double reSize = Math.max(options.outWidth * 1.0 / 1024f, options.outHeight * 1.0 / 1024f);
	       options.inSampleSize = (int) Math.ceil(reSize);
	       options.inJustDecodeBounds = false;
			
	       // 创建Bitmap
	       mBitmap = BitmapFactory.decodeFile(mPicStr, options);
	       mImageView.setImageBitmap(mBitmap);
	       
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_selectImg:
				 showDialog(); 
				break;

			case R.id.iv_userinfo_takepic:
				Intent intent110 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(intent110, CAMERA_REQUEST_CODE);
				
				break;
			
			case R.id.iv_userinfo_choosepic:
				Intent intent = new Intent(Intent.ACTION_PICK);
				
	    		// 获取手机图库信息
	            intent.setType("image/*");
	            startActivityForResult(intent, GALLERY_REQUEST_CODE);
	            
				break;
			
			case R.id.iv_userinfo_cancle:
				setHeadDialog.dismiss();
				break;
			
			case R.id.btn_savepic:
				Toast.makeText(getActivity(), "保存成功\n"+"sdcard" + imgSavePath, Toast.LENGTH_SHORT).show();
                System.out.println("保存成功路径是："+"sdcard" + imgSavePath);
				break;
				
			case R.id.btn_jiance:
				 // 点击识别按钮进行图片识别操作
	            mDialog.show();
	             FaceHelper.uploadFaces(mBitmap, new FaceHelper.CallBack() {

	                 @Override

	                 public void success(JSONObject result) {
	                     // 人脸识别成功，回调获取数据
	                    Message msg = Message.obtain();
	                     msg.what = SUCCESS;
	                    msg.obj = result;
	                    handler.sendMessage(msg);
	                 }

	                 @Override
	                public void error(FaceppParseException e) {
	                    // 人脸识别失败，回调获取错误信息
	                     Message msg = Message.obtain();
	                     msg.what = ERROR;
	                     msg.obj = e.getErrorMessage();
	                     handler.sendMessage(msg);
	                 }
	             });
				break;
			}
		}
		
		private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
			             case SUCCESS:
			               // 成功
			              JSONObject object = (JSONObject) msg.obj;
			             // 解析Json数据，重构Bitmap对象
			              reMakeBitmap(object);
			              mImageView.setImageBitmap(mBitmap);
			                 break;
			            case ERROR:
			                 // 失败
			              String errorInfo = (String) msg.obj;
			                if (errorInfo == null || "".equals(errorInfo)) {
			                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
			                } else {
			                    Toast.makeText(getActivity(), errorInfo, Toast.LENGTH_LONG).show();
			                 }
			                 break;
			
			            }
			
			         }
			 private void reMakeBitmap(JSONObject json) {
					
		            mDialog.dismiss();
		
		             
		             try {
		                 JSONArray faces = json.getJSONArray("face");
		                 System.out.println("faces:"+faces);
		               // 检测照片有多少张人脸
		                int facesCount = faces.length();
		                
		                if(facesCount<1){
		                	mTips.setText("未检测到人脸，请重新选取照片！");
		                	mTips.setVisibility(View.VISIBLE);
		                	
		                	mGender.setVisibility(View.INVISIBLE);
		                	mAge.setVisibility(View.INVISIBLE);
		                	mSmile.setVisibility(View.INVISIBLE);
		                	
		                }else if(facesCount>1){
		                	mTips.setText("检测到多张人脸，请重新选取照片！");
		                	mTips.setVisibility(View.VISIBLE);
		                	
		                	mGender.setVisibility(View.INVISIBLE);
		                	mAge.setVisibility(View.INVISIBLE);
		                	mSmile.setVisibility(View.INVISIBLE);
		                	
		                }else{
		                	
		                	
		                	mTips.setVisibility(View.GONE);
		                	mGender.setVisibility(View.VISIBLE);
		                	mAge.setVisibility(View.VISIBLE);
		                	mSmile.setVisibility(View.VISIBLE);
		                	
		                	
		                	for (int i = 0; i < facesCount; i++) {
		   	                 JSONObject position = faces.getJSONObject(i).getJSONObject("position");
		   	                    
		   	                 // position属性下的所需数据，定位人脸位置
		   	                    Float x = (float) position.getJSONObject("center").getDouble("x");
		   	                    Float y = (float) position.getJSONObject("center").getDouble("y");
		   	                    
		   	                    // 获取年龄，性别
		   	                    JSONObject attribute = faces.getJSONObject(i).getJSONObject("attribute");
		   	                    Integer age = attribute.getJSONObject("age").getInt("value");
		   	                    String sex = attribute.getJSONObject("gender").getString("value");
		   	                    double smile = attribute.getJSONObject("smiling").getDouble("value");
		   	                    
		   	                    getGender = sex;
		   	                    getAge = age;
		   	
		   	                    if(sex.equals("Male")){
		   	                    	mGender.setText("您的性别： 男 ");
		   	                    }else if(sex.equals("Female")){
		   	                    	mGender.setText("您的性别： 女 ");
		   	                    }
		   	                    
		   	                    mAge.setText("您的年龄：  "+age);
		   	                    if(smile<60){
			   	                    mSmile.setText("微笑度：  没有微笑");
			   	                    }else if(60<=smile && smile<=75){
			   	                    	mSmile.setText("微笑度：  轻微微笑");
			   	                    }else{
			   	                    	mSmile.setText("微笑度：  微笑");
			   	                    }
		   	                 }
		                	
		                	HairStyleHttpDownloader hairstyleDownloader = new HairStyleHttpDownloader();
		                	hairstyleDownloader.execute();
		                }
		                     
		
		             } catch (JSONException e) {
		
		            	 e.printStackTrace();
		
		             }
		
		             
			 }
		
		};
			
			
		/**
		 * 显示底部对话框
		 */
		public void showDialog() {  
	        setHeadDialog = new Builder(getActivity()).create();  
	        setHeadDialog.show();  
	        mDialogView = View.inflate(getActivity().getApplicationContext(),  
	                R.layout.layout_dialog, null);  
	  
	        setHeadDialog.getWindow().setContentView(mDialogView);  
	  
	        WindowManager windowManager = getActivity().getWindowManager();  
	        Display display = windowManager.getDefaultDisplay();  
	        WindowManager.LayoutParams lp = setHeadDialog.getWindow()  
	                .getAttributes();  
	        lp.width = (int) (display.getWidth()); // 设置宽度  
	        setHeadDialog.getWindow().setAttributes(lp);  
	        bindDialogEvent();  
	  
	    }  
		
		private void bindDialogEvent() {  
	        Button cameraButton = (Button) mDialogView  
	                .findViewById(R.id.iv_userinfo_takepic);  
	        Button photoButton = (Button) mDialogView  
	                .findViewById(R.id.iv_userinfo_choosepic);  
	        Button cancelButton = (Button) mDialogView  
	                .findViewById(R.id.iv_userinfo_cancle); 
	        
	        cameraButton.setOnClickListener(this);
	        photoButton.setOnClickListener(this);
	        cancelButton.setOnClickListener(this);
	  
	    }
		
		
		class HairStyleHttpDownloader extends AsyncTask<String, Void, String>{

			private URL url;
			private HttpURLConnection conn;
			
			@Override
			protected String doInBackground(String... arg0) {
				StringBuffer sb = new StringBuffer();
				String line = null;
				BufferedReader buffer = null;
				
				System.out.println("getGender = "+getGender);
				System.out.println("getAge = "+getAge);
				
				if(getGender.equals("Male")){
					if(getAge <26){
						ImgUrl = Images.ManYoung;
						System.out.println("ImgUrl = Images.ManYoung ="+Images.ManYoung);
					}else{
						ImgUrl = Images.Man_Middle;
					}
				}else if(getGender.equals("Female")){
					if(getAge <25){
						ImgUrl = Images.FemaleYoung;
					}else{
						ImgUrl = Images.Female_Middle;
					}
				}
				
				try {
					System.out.println("ImgUrl ="+ImgUrl);
					url = new URL(ImgUrl);
					conn = (HttpURLConnection) url.openConnection();
					
					buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					
					while ((line = buffer.readLine())!=null) {
						sb.append(line);
					}
					
				} catch (Exception e) {
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
				
				Log.d("banner", ""+result);
				
				Gson gson = new Gson();
				BannerWrapper wrapper = gson.fromJson(result, BannerWrapper.class);
				
				if(wrapper !=null){
					HairStyleAdapter adapter = new HairStyleAdapter(wrapper.banner);
					viewpager.setAdapter(adapter);
					
					Fragment_home.mIndicator.setViewPager(viewpager);
					Fragment_home.mIndicator.onPageSelected(0);
				}

			}
			
		}
		
		class HairStyleAdapter extends PagerAdapter{

			
			private BitmapUtils utils;
			private List<BannerInfo> banner;
			
			public HairStyleAdapter(List<BannerInfo> banner) {
				this.banner = banner;
				
				utils = new BitmapUtils(getActivity());
				utils.configDefaultLoadingImage(R.drawable.hdp1);//设置加载过程中默认显示的图片
				
			}

			@Override
			public int getCount() {
				bannerData = banner;
				PhotoCount = banner.size();
				return banner.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView image = new ImageView(getActivity());
				image.setImageResource(R.drawable.hdp1);
				image.setScaleType(ScaleType.FIT_XY);
				
				
				String cachePath;
				BitmapUtils bitmapUtils;

				final int position1 = position;
				
				BannerInfo info = banner.get(position);
				utils.display(image,info.getImage());
				String url = info.getImage();
				container.addView(image);
				
				int lastSlashIndex = url.lastIndexOf("/");
		        fileName = url.substring(lastSlashIndex + 1);
				
				//由当前时间组成图片路径
			    final String imgPath = "/com.haircut.haircut_alpha/image/" + fileName;
			    imgSavePath = imgPath;
			        //使用 xutils.HttpUtils 工具利用图片uri网络地址下载图片
			      HttpUtils httpUtils = new HttpUtils();
			        
			        //执行下载，传入网络图片路径和保存位置
			        httpUtils.download(url, "sdcard" + imgPath, new RequestCallBack<File>() {
			            @Override
			            public void onSuccess(ResponseInfo<File> responseInfo) {
//			                Toast.makeText(getActivity(), "保存成功\n"+"sdcard" + imgPath, Toast.LENGTH_SHORT).show();
//			                System.out.println("保存成功路径是："+"sdcard" + imgPath);
			            	btn_saveImg.setEnabled(true);
			            	
			            	getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("sdcard"+imgPath))));
			            	
			            }

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							
						}
			          
			        }); 
			        
		        
				image.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						 Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);//打开图片详情类
		                 intent.putExtra("image_position", position1);
		                 getActivity().startActivity(intent);
						
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
		

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}  

}
