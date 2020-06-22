package com.shk.mao.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shk.mao.R;
import com.shk.mao.struct.Page;
import com.shk.mao.struct.PageMgr;

import java.util.ArrayList;
import java.util.List;

public class AdapterPage extends BaseAdapter {
	private Context mContext;
	private List<Page> mData;

	public AdapterPage(Context context) {
		mContext = context;
	}

	public void setData(List<Page> data) {
		this.mData = new ArrayList<>();

		for (int i = data.size() - 1; i >= 0; i--) {
			mData.add(data.get(i));
		}

		notifyDataSetChanged();
	}

	public List<Page> getData() {
		return mData;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Page data = mData.get(position);

		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.grid_page, null);

			view.findViewById(R.id.close).setOnClickListener((v) -> {
				PageMgr.getInstance().removePage(data);
				setData(PageMgr.getInstance().getPages());
			});
		}

		ImageView icon = view.findViewById(R.id.icon);
		icon.setImageBitmap(data.icon);

		TextView title = view.findViewById(R.id.title);
		title.setText(data.title);

		return view;
	}
}