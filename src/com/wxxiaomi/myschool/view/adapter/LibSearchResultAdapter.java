package com.wxxiaomi.myschool.view.adapter;

import java.util.List;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.lib.BookInfo;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class LibSearchResultAdapter extends RecyclerView.Adapter<ViewHolder> {

	private Context ct;
	private List<BookInfo> list;
	private int TYPE_FOOTER = 1;
	private int TYPE_ITEM = 2;
	@SuppressWarnings("unused")
	private View foot;
	private OnResultClickListener lis;
	public void setOnResultClickListener(OnResultClickListener lis){
		this.lis = lis;
	}
	
	public LibSearchResultAdapter(Context context, List<BookInfo> list) {
		super();
		this.ct = context;
		this.list = list;
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

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		if (viewHolder instanceof ItemViewHolder) {
			 BookInfo column = list.get(position);
			ItemViewHolder holder = (ItemViewHolder) viewHolder;
			holder.tv_name.setText(column.getName());
			holder.tv_info.setText("信息："+column.getAuthor());
			holder.tv_collectCount.setText("馆藏："+column.getCollectionCount());
			holder.tv_number.setText("索取号："+column.getNumber());
			holder.tv_borrow.setText("可借："+column.getIsBorrow());
		}else if(viewHolder instanceof FooterViewHolder){
			
		}
		
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		if(viewType == TYPE_ITEM){
			View view = LayoutInflater.from(ct).inflate(R.layout.item_lib_searchresult,
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
		public TextView tv_name;
		public TextView tv_info;
		public TextView tv_collectCount;
		public TextView tv_borrow;
		public TextView tv_number;
		public ItemViewHolder(View view) {
			super(view);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_info = (TextView) view.findViewById(R.id.tv_info);
			tv_collectCount = (TextView) view.findViewById(R.id.tv_collectCount);
			tv_borrow = (TextView) view.findViewById(R.id.tv_borrow);
			tv_number = (TextView) view.findViewById(R.id.tv_number);
			view.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			lis.click(getPosition());
		}
	}

	class FooterViewHolder extends ViewHolder {
		public FooterViewHolder(View view) {
			super(view);
			foot = view;
		}
	}
	public interface OnResultClickListener{
		void click(int position);
	}
}
