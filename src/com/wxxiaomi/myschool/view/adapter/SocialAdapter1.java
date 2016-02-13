package com.wxxiaomi.myschool.view.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.view.custom.CircularImageView;

public class SocialAdapter1 extends RecyclerView.Adapter<ViewHolder> {

	private List<SocialItem> list;
	private Context ct;
	BitmapUtils bitmapUtil;
	List<Integer> hasDot;
	private int TYPE_FOOTER = 1;
	private int TYPE_ITEM = 2;
	public View foot;
	MySocialCommentItemClickListener lis;
	
	public void setSocialCommentItemClickListener(MySocialCommentItemClickListener lis){
		this.lis = lis;
	}

	public SocialAdapter1(Context ct, List<SocialItem> list,
			List<Integer> hasDot) {
		this.ct = ct;
		this.list = list;
		bitmapUtil = new BitmapUtils(ct);
		this.hasDot = hasDot;
	}
	
	public void setNoData(boolean noData){
		if(foot!=null){
			if(noData){
				foot.setVisibility(View.GONE);
			}else{
				foot.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER ;
		} else {
			return TYPE_ITEM ;
		}
	}

	@Override
	public int getItemCount() {
		
		return list.size() + 1;
	}

	/**
	 * 绑定ViewHolder的数据。
	 * 
	 * @param viewHolder
	 * @param i
	 *            数据源list的下标
	 */
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		if (viewHolder instanceof ItemViewHolder) {
			SocialItem social = list.get(position);
			ItemViewHolder hold = (ItemViewHolder) viewHolder;
			hold.tv_name.setText(social.userInfo.name);
			bitmapUtil.display(hold.iv_pic, ConstantValue.LOTTERY_URI
					+ social.pic);
			hold.tv_title.setText(social.title);
			hold.tv_likecount.setText(social.likes + "");
			bitmapUtil.display(hold.iv_pic, ConstantValue.LOTTERY_URI
					+ social.pic);
			bitmapUtil.display(hold.iv_head, ConstantValue.LOTTERY_URI
					+ social.userInfo.pic);
		}else if(viewHolder instanceof FooterViewHolder){
			
		}

	}

	/**
	 * 渲染具体的ViewHolder
	 * 
	 * @param viewGroup
	 *            ViewHolder的容器
	 * @param i
	 *            一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
	 * @return
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if(viewType == TYPE_ITEM){
			View view = LayoutInflater.from(ct).inflate(R.layout.item_social1,
					viewGroup, false);
			return new ItemViewHolder(view);
		}else if(viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(ct).inflate(R.layout.item_footer,
					viewGroup, false);
			return new FooterViewHolder(view);
		}
		return null;
	}

	public class ItemViewHolder extends ViewHolder implements OnClickListener{

		CircularImageView iv_head;
		ImageView iv_pic;
		public TextView tv_name;
		TextView tv_title;
		TextView tv_likecount;
		

		public ItemViewHolder(View view) {
			super(view);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
			iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_likecount = (TextView) view.findViewById(R.id.tv_likecount);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			lis.onClick(getPosition());
		}

	}

	class FooterViewHolder extends ViewHolder {

		public FooterViewHolder(View view) {
			
			super(view);
			foot = view;
			
		}
	}
	
	public interface MySocialCommentItemClickListener{
		void onClick(int position);
	}
}
