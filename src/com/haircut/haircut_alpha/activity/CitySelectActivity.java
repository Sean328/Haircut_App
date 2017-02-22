package com.haircut.haircut_alpha.activity;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haircut.haircut_alpha.R;


import com.haircut.haircut_alpha.entity.City;
import com.haircut.haircut_alpha.utils.LocationUtil;
import com.haircut.haircut_alpha.utils.MyUtils;
import com.haircut.haircut_alpha.utils.SharedUtils;
import com.haircut.haircut_alpha.view.MyLetterListView;
import com.haircut.haircut_alpha.view.MyLetterListView.OnTouchingLetterChangedListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CitySelectActivity extends Activity{
	
	private ListAdapter adapter;
	private ListView personList;
	private TextView imgback;
	private TextView overlay; // �Ի�������ĸtextview
	private MyLetterListView letterListView; // A-Z listview
	private HashMap<String, Integer> alphaIndexer;// ��Ŵ��ڵĺ���ƴ������ĸ����֮��Ӧ���б�λ��
	private String[] sections;// ��Ŵ��ڵĺ���ƴ������ĸ
	private Handler handler;
	private OverlayThread overlayThread; // ��ʾ����ĸ�Ի���
	private ArrayList<City> allCity_lists; // ���г����б�
	private ArrayList<City> ShowCity_lists; // ��Ҫ��ʾ�ĳ����б�-���������ı�
	private ArrayList<City> city_lists;// �����б�
	private String lngCityName ="";//��ŷ��صĳ�����
	private JSONArray chineseCities ;
	private LocationClient locationClient = null;
	private EditText sh;
	private TextView lng_city;
	private LinearLayout lng_city_lay;
	private ProgressDialog progress;
	private static final int SHOWDIALOG = 2;
	private static final int DISMISSDIALOG = 3;
	
	private Context context;
	private SharedUtils she;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.acitivty_city_select);
		
		context = this.getApplicationContext();
		she = new SharedUtils(context);
		
		personList = (ListView) findViewById(R.id.list_view);
		allCity_lists = new ArrayList<City>();
		letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
		lng_city_lay = (LinearLayout) findViewById(R.id.lng_city_lay);
		sh = (EditText) findViewById(R.id.sh);
		lng_city = (TextView) findViewById(R.id.lng_city);
		imgback = (TextView) findViewById(R.id.imgback);
		
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		personList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(CitySelectActivity.this,MainActivity.class);
				intent.putExtra("lngCityName", ShowCity_lists.get(arg2).name);
				SharedUtils.putCityName(context, ShowCity_lists.get(arg2).name);
//				setResult(MyUtils.RequestCityCode,intent);
				startActivity(intent);
				finish();
			}
		});
		lng_city_lay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CitySelectActivity.this,MainActivity.class);
				intent.putExtra("lngCityName",lngCityName);
//				setResult(MyUtils.RequestCityCode,intent);
				SharedUtils.putCityName(context, lngCityName);
				startActivity(intent);
				finish();
			}
		});
		imgback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		initGps();
		initOverlay();
		handler2.sendEmptyMessage(SHOWDIALOG);
		Thread thread = new Thread(){
			@Override
			public void run() {
				hotCityInit();
				handler2.sendEmptyMessage(DISMISSDIALOG);
				super.run();
			}
		};
		thread.start();
	}

	/**
	 * ���ų���
	 */
	public void hotCityInit() {
		City city;   
		city = new City("�Ϻ�", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city = new City("�人", "");
		allCity_lists.add(city);
		city = new City("���", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city = new City("�Ͼ�", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city = new City("�ɶ�", "");
		allCity_lists.add(city);
		city = new City("����", "");
		allCity_lists.add(city);
		city_lists = getCityList();
		allCity_lists.addAll(city_lists);
		ShowCity_lists=allCity_lists;
	}

	private ArrayList<City> getCityList() {
		ArrayList<City> list = new ArrayList<City>();
		try {
			chineseCities = new JSONArray(getResources().getString(R.string.citys));
			for(int i=0;i<chineseCities.length();i++){
				JSONObject jsonObject = chineseCities.getJSONObject(i);
				City city = new City(jsonObject.getString("name"), jsonObject.getString("pinyin"));
				list.add(city);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * a-z����
	 */
	Comparator comparator = new Comparator<City>() {
		@Override
		public int compare(City lhs, City rhs) {
			String a = lhs.getPinyi().substring(0, 1);
			String b = rhs.getPinyi().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}

		}
	};

//	private void setAdapter(List<City> list) {
//		adapter = new ListAdapter(this, list);
//		personList.setAdapter(adapter);
//	}

	public class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		final int VIEW_TYPE = 3;

		public ListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[ShowCity_lists.size()];
			for (int i = 0; i < ShowCity_lists.size(); i++) {
				// ��ǰ����ƴ������ĸ
				String currentStr = getAlpha(ShowCity_lists.get(i).getPinyi());
				// ��һ������ƴ������ĸ�����������Ϊ�� ��
				String previewStr = (i - 1) >= 0 ? getAlpha(ShowCity_lists.get(i - 1)
						.getPinyi()) : " ";
				if (!previewStr.equals(currentStr)) {
					String name = getAlpha(ShowCity_lists.get(i).getPinyi());
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}
		}

		@Override
		public int getCount() {
			return ShowCity_lists.size();
		}

		@Override
		public Object getItem(int position) {
			return ShowCity_lists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			int type = 2;
			
			if (position == 0&&sh.getText().length()==0) {//����������״̬��
				type = 0;
			}
			return type;
		}

		@Override
		public int getViewTypeCount() {// ������Ҫ������Ҫ���в������ͣ��ܴ�СΪ���͵��������±�
			return VIEW_TYPE;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			int viewType = getItemViewType(position);
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.list_item, null);
					holder = new ViewHolder();
					holder.alpha = (TextView) convertView
							.findViewById(R.id.alpha);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}

				
					holder.name.setText(ShowCity_lists.get(position).getName());
					String currentStr = getAlpha(ShowCity_lists.get(position).getPinyi());//����ƴ��
					String previewStr = (position-1) >= 0 ? getAlpha(ShowCity_lists.get(position-1).getPinyi()) : " ";//��һ��ƴ��
					if (!previewStr.equals(currentStr)) {//��һ������ʾ
						holder.alpha.setVisibility(View.VISIBLE);
						if (currentStr.equals("#")) {
							currentStr = "���ų���";
						}
						holder.alpha.setText(currentStr);
					} else {
						holder.alpha.setVisibility(View.GONE);
					}
//				}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha; // ����ĸ����
			TextView name; // ��������
		}
	}

	// ��ʼ������ƴ������ĸ������ʾ��
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// �ӳ�һ���ִ�У���overlayΪ���ɼ�
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// ����overlay���ɼ�
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	// ��ú���ƴ������ĸ
	private String getAlpha(String str) {

		if (str.equals("-")) {
			return "&";
		}
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else {
			return "#";
		}
	}

	private void initGps() {
		try{
			MyLocationListenner myListener = new MyLocationListenner();
			locationClient = new LocationClient(CitySelectActivity.this); 
			locationClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setAddrType("all");
			option.setCoorType("bd09ll");
			option.setScanSpan(5000);
			option.disableCache(true);
//			option.setPoiNumber(5); 
//			option.setPoiDistance(1000); 
//			option.setPoiExtraInfo(true); 
			option.setPriority(LocationClientOption.GpsFirst);
			locationClient.setLocOption(option);
			locationClient.start();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		locationClient.stop();
	}


	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
//				sb.append(location.getAddrStr());
				sb.append(location.getCity());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append(location.getCity());
			}
			if (sb.toString() != null && sb.toString().length() > 0) {
				lngCityName=sb.toString();
				lng_city.setText(lngCityName);
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}
	
	Handler handler2 = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOWDIALOG:
				progress = LocationUtil.showProgress(CitySelectActivity.this, "���ڼ������ݣ����Ժ�...");
				break;
			case DISMISSDIALOG:
				if (progress != null)
				{
					progress.dismiss();
				}
				adapter = new ListAdapter(CitySelectActivity.this);
				personList.setAdapter(adapter);
				
				sh.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
					}
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}
					@Override
					public void afterTextChanged(Editable s) {
						//���������û�����ĳ�����
						if(s.length()>0){
							ArrayList<City> changecity = new ArrayList<City>();
							for(int i=0;i<city_lists.size();i++){
								if(city_lists.get(i).name.indexOf(sh.getText().toString())!=-1){
									changecity.add(city_lists.get(i));
								}
							}
							ShowCity_lists = changecity;
						}else{
							ShowCity_lists = allCity_lists;
						}
						adapter.notifyDataSetChanged();
					}
				});
				break;
			default:
				break;
			}
		};
	};

}
