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
	
	// ��־����
		private static final int GALLERY_REQUEST_CODE = 897;
		private static int CAMERA_REQUEST_CODE = 896;
		private static final int SUCCESS = 1;
		private static final int ERROR = 0;
		private Dialog setHeadDialog;  
		private View mDialogView;  
		private Button mButton;
		private Button mDoAction;
		private Button btn_saveImg;
		// ͼƬ·��
		private String mPicStr;
		
		private ImageView mImageView;
		private ProgressDialog mDialog;
		
		private TextView mGender;
		private TextView mAge;
		private TextView mSmile;
		private TextView mTips;
		
		private Bitmap mBitmap;
		
//		private static final String TAG = "SmartMatchFragment";
		private ImageLoader  imageLoader = ImageLoader.getInstance();  //��ȡͼƬ���й���Ĺ�����ʵ����
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
	        mDialog.setMessage("ϵͳ���ʶ���У����Ժ�..");
	        
	        
	        viewpager = (ViewPager) view.findViewById(R.id.sm_viewpager); //��ȡviewpager
	        

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
	                
	                System.out.println("��᷵�ص�uri+"+uri);
	                
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
	        
	                mImageView.setImageBitmap(mBitmap);  //������Ƭ��ʵ�ڽ�����
	                mDoAction.setEnabled(true);
	                setHeadDialog.dismiss();
		                
	            }
	        }
		}
		
		

		private void reSize(String mPicStr) {
			// ���ݻ�ȡ����ͼƬ·������ȡͼƬ������ͼƬѹ��
	       BitmapFactory.Options options = new BitmapFactory.Options();
	       // ��Options��inJustDecodeBounds��������Ϊtrueʱ��������ʾͼƬ��ֻ�᷵�ظ�ͼƬ�ľ�������
	       options.inJustDecodeBounds = true;
	       // ������ѡʵ�ʵĿ�߼���ѹ����������ͼƬѹ��
	       BitmapFactory.decodeFile(mPicStr, options);
	       Double reSize = Math.max(options.outWidth * 1.0 / 1024f, options.outHeight * 1.0 / 1024f);
	       options.inSampleSize = (int) Math.ceil(reSize);
	       options.inJustDecodeBounds = false;
			
	       // ����Bitmap
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
				
	    		// ��ȡ�ֻ�ͼ����Ϣ
	            intent.setType("image/*");
	            startActivityForResult(intent, GALLERY_REQUEST_CODE);
	            
				break;
			
			case R.id.iv_userinfo_cancle:
				setHeadDialog.dismiss();
				break;
			
			case R.id.btn_savepic:
				Toast.makeText(getActivity(), "����ɹ�\n"+"sdcard" + imgSavePath, Toast.LENGTH_SHORT).show();
                System.out.println("����ɹ�·���ǣ�"+"sdcard" + imgSavePath);
				break;
				
			case R.id.btn_jiance:
				 // ���ʶ��ť����ͼƬʶ�����
	            mDialog.show();
	             FaceHelper.uploadFaces(mBitmap, new FaceHelper.CallBack() {

	                 @Override

	                 public void success(JSONObject result) {
	                     // ����ʶ��ɹ����ص���ȡ����
	                    Message msg = Message.obtain();
	                     msg.what = SUCCESS;
	                    msg.obj = result;
	                    handler.sendMessage(msg);
	                 }

	                 @Override
	                public void error(FaceppParseException e) {
	                    // ����ʶ��ʧ�ܣ��ص���ȡ������Ϣ
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
			               // �ɹ�
			              JSONObject object = (JSONObject) msg.obj;
			             // ����Json���ݣ��ع�Bitmap����
			              reMakeBitmap(object);
			              mImageView.setImageBitmap(mBitmap);
			                 break;
			            case ERROR:
			                 // ʧ��
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
		               // �����Ƭ�ж���������
		                int facesCount = faces.length();
		                
		                if(facesCount<1){
		                	mTips.setText("δ��⵽������������ѡȡ��Ƭ��");
		                	mTips.setVisibility(View.VISIBLE);
		                	
		                	mGender.setVisibility(View.INVISIBLE);
		                	mAge.setVisibility(View.INVISIBLE);
		                	mSmile.setVisibility(View.INVISIBLE);
		                	
		                }else if(facesCount>1){
		                	mTips.setText("��⵽����������������ѡȡ��Ƭ��");
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
		   	                    
		   	                 // position�����µ��������ݣ���λ����λ��
		   	                    Float x = (float) position.getJSONObject("center").getDouble("x");
		   	                    Float y = (float) position.getJSONObject("center").getDouble("y");
		   	                    
		   	                    // ��ȡ���䣬�Ա�
		   	                    JSONObject attribute = faces.getJSONObject(i).getJSONObject("attribute");
		   	                    Integer age = attribute.getJSONObject("age").getInt("value");
		   	                    String sex = attribute.getJSONObject("gender").getString("value");
		   	                    double smile = attribute.getJSONObject("smiling").getDouble("value");
		   	                    
		   	                    getGender = sex;
		   	                    getAge = age;
		   	
		   	                    if(sex.equals("Male")){
		   	                    	mGender.setText("�����Ա� �� ");
		   	                    }else if(sex.equals("Female")){
		   	                    	mGender.setText("�����Ա� Ů ");
		   	                    }
		   	                    
		   	                    mAge.setText("�������䣺  "+age);
		   	                    if(smile<60){
			   	                    mSmile.setText("΢Ц�ȣ�  û��΢Ц");
			   	                    }else if(60<=smile && smile<=75){
			   	                    	mSmile.setText("΢Ц�ȣ�  ��΢΢Ц");
			   	                    }else{
			   	                    	mSmile.setText("΢Ц�ȣ�  ΢Ц");
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
		 * ��ʾ�ײ��Ի���
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
	        lp.width = (int) (display.getWidth()); // ���ÿ��  
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
				utils.configDefaultLoadingImage(R.drawable.hdp1);//���ü��ع�����Ĭ����ʾ��ͼƬ
				
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
				
				//�ɵ�ǰʱ�����ͼƬ·��
			    final String imgPath = "/com.haircut.haircut_alpha/image/" + fileName;
			    imgSavePath = imgPath;
			        //ʹ�� xutils.HttpUtils ��������ͼƬuri�����ַ����ͼƬ
			      HttpUtils httpUtils = new HttpUtils();
			        
			        //ִ�����أ���������ͼƬ·���ͱ���λ��
			        httpUtils.download(url, "sdcard" + imgPath, new RequestCallBack<File>() {
			            @Override
			            public void onSuccess(ResponseInfo<File> responseInfo) {
//			                Toast.makeText(getActivity(), "����ɹ�\n"+"sdcard" + imgPath, Toast.LENGTH_SHORT).show();
//			                System.out.println("����ɹ�·���ǣ�"+"sdcard" + imgPath);
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
						
						 Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);//��ͼƬ������
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
