package com.wxxiaomi.myschool.view.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Comment.CommentItem;
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.view.custom.CircularImageView;

public class SocialDetailAdapter extends RecyclerView.Adapter<ViewHolder> {

	private List<CommentItem> list;
	private Context ct;
	private int TYPE_HEADER = 1;
	private int TYPE_FOOTER = 2;
	private int TYPE_ITEM = 3;
	BitmapUtils bitmapUtil;
	private SocialItem detail;
	private boolean noData;
	private int TYPE_NODATA = 4;
	TextView text;
	
	public void setNoData(boolean noData){
//		this.noData = noData;
		text.setText("没有数据了");
		
	}
	
	
	public SocialDetailAdapter(Context ct,List<CommentItem> list,SocialItem detail) {
		super();
		this.list = list;
		this.ct = ct;
		bitmapUtil = new BitmapUtils(ct);
		this.detail = detail;
	}
	@Override
	public int getItemViewType(int position) {
		Log.i("wang", "adapter中调用了 getItemViewType，position="+position);
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER ;
		} else if(position == 0){
			return TYPE_HEADER;
		}else if(position ==1 &&list.size() == 0 && noData){
			Log.i("wang", "list.size() == 0 && noData");
			return TYPE_NODATA ;
		}else {
			return TYPE_ITEM ;
		}
	}

	@Override
	public int getItemCount() {
		return list.size()+2;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		if(viewHolder instanceof ItemViewHolder)
		{
			CommentItem item = list.get(position-1);
			ItemViewHolder hold = (ItemViewHolder)viewHolder;
			bitmapUtil.display(hold.iv_head, ConstantValue.LOTTERY_URI
					+ item.userInfo.pic);
			hold.tv_name.setText(item.userInfo.name);
			hold.tv_date.setText(item.date);
			hold.tv_content.setText(item.content);
			hold.tv_name.setText(item.userInfo.name);
			if(item.reply == 1){
				//回复型评论
				hold.tv_reply.setVisibility(View.VISIBLE);
				hold.tv_reply.setText("回复@"+item.replyComment.userInfo.name+":"+item.replyComment.content);
			}else{
				hold.tv_reply.setVisibility(View.GONE);
			}
		}else if(viewHolder instanceof HeaderViewHolder){
			HeaderViewHolder hold = (HeaderViewHolder)viewHolder;
			bitmapUtil.display(hold.iv_head, ConstantValue.LOTTERY_URI
					+ detail.userInfo.pic);
			bitmapUtil.display(hold.iv_pic, ConstantValue.LOTTERY_URI
					+ detail.pic);
//			hold.tv_likecount.setText(detail.likes);
			hold.tv_name.setText(detail.userInfo.name);
			hold.tv_title.setText(detail.title);
		}
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if(viewType == TYPE_ITEM){
			View view = LayoutInflater.from(ct).inflate(R.layout.item_social_comment,
					viewGroup, false);
			return new ItemViewHolder(view);
		}else if(viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(ct).inflate(R.layout.item_footer,
					viewGroup, false);
			return new FooterViewHolder(view);
		}else if(viewType == TYPE_HEADER){
			View view = LayoutInflater.from(ct).inflate(R.layout.header_social_detail,
					viewGroup, false);
			return new HeaderViewHolder(view);
		}
//		else if(viewType == TYPE_NODATA){
//			View view = LayoutInflater.from(ct).inflate(R.layout.footer_nodata,
//					viewGroup, false);
//			return new NoDataViewHolder(view);
//		}
		return null;
	}
	
	class ItemViewHolder extends ViewHolder{
		CircularImageView iv_head;
		TextView tv_name;
		TextView tv_date;
		TextView tv_content;
		TextView tv_reply;
		public ItemViewHolder(View view) {
			super(view);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_date = (TextView) view.findViewById(R.id.tv_date);
			tv_content = (TextView) view.findViewById(R.id.tv_content);
			tv_reply = (TextView) view.findViewById(R.id.tv_reply);
		}
	}
	class HeaderViewHolder extends ViewHolder{
		CircularImageView iv_head;
		TextView tv_name;
		ImageView iv_pic;
		TextView tv_title;
		TextView tv_likecount;
		public HeaderViewHolder(View view) {
			super(view);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_likecount = (TextView) view.findViewById(R.id.tv_likecount);
		}
	}
	class FooterViewHolder extends ViewHolder {
	
		public FooterViewHolder(View view) {
			super(view);
//			foot = view;
			text = (TextView) view.findViewById(R.id.text);
		}
	}
	class NoDataViewHolder extends ViewHolder{

		public NoDataViewHolder(View view) {
			super(view);
		}
		
	}

}
