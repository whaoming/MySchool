package com.wxxiaomi.myschool.view.adapter;


import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Lost.LostItem;
import com.wxxiaomi.myschool.view.custom.CircularImageView;

public class LostItemAdapter2 extends  RecyclerView.Adapter<ViewHolder> {

	private Context ctx;
	private List<LostItem> list;
	BitmapUtils bitmapUtil;
	boolean isMe;
	private int TYPE_FOOTER = 1;
	private int TYPE_ITEM = 2;
	public View foot;

	
	public LostItemAdapter2(Context ctx, List<LostItem> list, boolean isMe) {
		this.ctx = ctx;
		this.list = list;
		bitmapUtil = new BitmapUtils(ctx);
		bitmapUtil.configDefaultLoadingImage(R.drawable.default_unknow_user);
		this.isMe = isMe;
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
		// TODO Auto-generated method stub
		return list.size()+1;
	}


	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		if(viewHolder instanceof ItemViewHolder)
		{
			LostItem item = list.get(position);
			ItemViewHolder hold = (ItemViewHolder)viewHolder;
			bitmapUtil.display(hold.iv_head, ConstantValue.LOTTERY_URI
					+ item.userInfo.pic);
			hold.tv_name.setText(item.userInfo.name);
			hold.tv_title.setText(item.title);
			hold.tv_time.setText(item.ptime);
		}
		
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		
		if(viewType == TYPE_ITEM){
			View view = LayoutInflater.from(ctx).inflate(R.layout.item_lost,
					viewGroup, false);
			return new ItemViewHolder(view);
		}else if(viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(ctx).inflate(R.layout.item_footer,
					viewGroup, false);
			return new FooterViewHolder(view);
		}
		return null;
	}
	
	public class ItemViewHolder extends ViewHolder {
		CircularImageView iv_head;
		TextView tv_name;
		TextView tv_title;
		TextView tv_time;
		public ItemViewHolder(View view) {
			super(view);
			iv_head = (CircularImageView) view.findViewById(R.id.iv_head);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
		}
		
	}
	
	class FooterViewHolder extends ViewHolder {

		public FooterViewHolder(View view) {
			
			super(view);
			foot = view;
			
		}
		

	}

//	@Override
//	public int getCount() {
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		Holder hold;
//		final LostItem lost = list.get(position);
//		if (convertView == null) {
//			hold = new Holder();
//			convertView = View.inflate(ctx, R.layout.item_lost, null);
////			hold.upic = (ImageView) convertView.findViewById(R.id.upic);
//			hold.uname = (TextView) convertView.findViewById(R.id.uname);
////			hold.udescription = (TextView) convertView
////					.findViewById(R.id.udescription);
//			hold.title = (TextView) convertView.findViewById(R.id.title);
////			hold.noScrollgridview = (MyGridView) convertView
////					.findViewById(R.id.noScrollgridview);
//			hold.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
//			hold.type = (TextView) convertView.findViewById(R.id.type);
////			hold.iv_edit = (ImageView) convertView.findViewById(R.id.iv_edit);
//			convertView.setTag(hold);
//		} else {
//			hold = (Holder) convertView.getTag();
//		}
//		if (isMe) {
////			hold.iv_edit.setVisibility(View.VISIBLE);
////			hold.iv_edit.setOnClickListener(new OnClickListener() {
////				@Override
////				public void onClick(View v) {
////					new AlertDialog(ctx)
////							.builder()
////							.setTitle("请选择操作")
////							.addSheetItem("编辑", SheetItemColors.Blue,
////									new OnSheetItemClickListeners() {
////										@Override
////										public void onClick(int which) {
////											Intent intent = new Intent(
////													ctx,
////													LostItemPublishActivity.class);
////											intent.putExtra("position",
////													position);
////											Bundle bund = new Bundle();
////											bund.putSerializable("LostItem",
////													lost);
////											intent.putExtra("value", bund);
////											intent.putExtra("isEdit", true);
////											intent.putExtra("isLost", lost
////													.lost == 1 ? true
////													: false);
////											Activity activity = (Activity) ctx;
////
////											activity.startActivityForResult(
////													intent, 1);
////										}
////									})
////							.addSheetItem("删除", SheetItemColors.Blue,
////									new OnSheetItemClickListeners() {
////										@Override
////										public void onClick(int which) {
////											new AlertDialog(ctx)
////													.builder()
////													.setTitle("确定要删除此信息吗？")
////													.addSheetItem(
////															"是",
////															SheetItemColors.Blue,
////															new OnSheetItemClickListeners() {
////																@Override
////																public void onClick(
////																		int which) {
////																	
////																}
////															})
////													.addSheetItem(
////															"否",
////															SheetItemColors.Blue,
////															new OnSheetItemClickListeners() {
////
////																@Override
////																public void onClick(
////																		int which) {
////
////																}
////															}).show();
////										}
////									}).show();
////				}
////			});
//		} else {
//			hold.iv_edit.setVisibility(View.GONE);
//		}
//		hold.title.setText(lost.title);
////		hold.uname.setText(lost.userInfo.uname);
////		hold.udescription.setText(lost.userInfo.udescription);
////		bitmapUtil.display(hold.upic,
////				ConstantValue.LOTTERY_URI + lost.userInfo.upic);
//		if (lost.lost == 1) {
//			hold.type.setText("寻物启事");
//			hold.tv_time.setText("丢失时间:" + lost.ltime);
//		} else {
//			hold.type.setText("失物招领");
//			hold.tv_time.setText("捡到时间:" + lost.ltime);
//		}
//
////		final String[] piclist = lost.pic.split(",");
////		MyPhotoGridAdapter adapter = new MyPhotoGridAdapter(ctx, piclist, false);
////		hold.noScrollgridview.setAdapter(adapter);
////		hold.noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
////			@Override
////			public void onItemClick(AdapterView<?> parent, View view,
////					int position, long id) {
////				Intent intent = new Intent(ctx, PhotoActivity.class);
////				intent.putExtra("ID", position);
////				intent.putExtra("isNet", true);
////				for (String pic : piclist) {
////					Bimp.drr.add(pic);
////					Bimp.max = piclist.length;
////				}
////				ctx.startActivity(intent);
////
////			}
////		});
//
//		return convertView;
//	}
//
//	static class Holder {
//		ImageView upic;
//		TextView uname;
//		TextView udescription;
//		TextView title;
////		MyGridView noScrollgridview;
//		TextView type;
//		TextView tv_time;
//		ImageView iv_edit;
//	}

}
