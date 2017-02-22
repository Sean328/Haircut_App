package com.haircut.haircut_alpha.adapter;

import java.util.ArrayList;
import java.util.List;

import com.haircut.haircut_alpha.R;
import com.haircut.haircut_alpha.activity.GoodsDetailActivity;
import com.haircut.haircut_alpha.entity.Comment;
import com.haircut.haircut_alpha.entity.Comment.CommentsInfo;
import com.haircut.haircut_alpha.entity.GoodsWrapper.GoodsInfo;
import com.haircut.haircut_alpha.entity.ShopWrapper.ShopInfo;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class CommentAdapter extends BaseAdapter{
	
	public static List<CommentsInfo> infos = new ArrayList<CommentsInfo>();
	private Context context;
	
	public static List<CommentsInfo> mDatalist = new ArrayList<CommentsInfo>();
	
	public CommentAdapter(Context context,List<CommentsInfo> commentInfo) {
		this.context = context;
		this.infos = commentInfo;
		
		mDatalist.clear();
		
		initData();
	}
	
	private void initData() {
		for(CommentsInfo data:infos){
			if(data.getGoods_id().equals(GoodsDetailActivity.goods_id)){
				mDatalist.add(data);
				System.out.println("mDatalist---------"+mDatalist);
			}
		}
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
	if (observer != null) {
	       super.unregisterDataSetObserver(observer);
	   }
	}
	

	@Override
	public int getCount() {
		return mDatalist.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatalist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View mView;
		mView = convertView;
		mView = View.inflate(context, R.layout.comment_list_item, null);
		
		RatingBar ratBar = (RatingBar) mView.findViewById(R.id.comment_rb);
		TextView score = (TextView) mView.findViewById(R.id.comment_score);
		TextView user = (TextView) mView.findViewById(R.id.comment_user);
		TextView content = (TextView) mView.findViewById(R.id.comment_content);
		TextView time = (TextView) mView.findViewById(R.id.comment_time);
		
		CommentsInfo commentsInfo = mDatalist.get(position);
		float Score = Float.parseFloat(commentsInfo.getClevel());
		ratBar.setRating(Score);
		score.setText(commentsInfo.getClevel());
		user.setText(commentsInfo.getName());
		content.setText(commentsInfo.getComments());
		time.setText(commentsInfo.getTime());
		
		return mView;
	}

}
