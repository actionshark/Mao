package com.shk.mao.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.shk.mao.R;
import com.shk.mao.struct.Page;
import com.shk.mao.struct.PageMgr;

public class ActivityPage extends AppCompatActivity {
	private PageMgr mPageMgr;
	private ListView mPages;
	private AdapterPage mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page);

		mPageMgr = PageMgr.getInstance();

		mAdapter = new AdapterPage(this);

		mPages = findViewById(R.id.pages);
		mPages.setAdapter(mAdapter);
		mPages.setOnItemClickListener((parent, view, position, id) -> {
			Page page = mAdapter.getData().get(position);
			mPageMgr.selectPage(page);
			finish();
		});

		findViewById(R.id.close_all).setOnClickListener((view) -> {
			int length = mPageMgr.getPages().size();
			for (int i = 0; i < length; i++) {
				mPageMgr.removePage(0);
			}

			mAdapter.setData(mPageMgr.getPages());

			finish();
		});

		findViewById(R.id.add).setOnClickListener((view) -> {
			mPageMgr.addPage();
			finish();
		});

		findViewById(R.id.back).setOnClickListener((view) -> {
			finish();
		});

		mAdapter.setData(mPageMgr.getPages());
	}
}