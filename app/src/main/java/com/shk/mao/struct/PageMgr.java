package com.shk.mao.struct;

import android.webkit.WebView;

import com.shk.mao.ui.ActivityMain;

import java.util.ArrayList;
import java.util.List;

public class PageMgr {
	private static PageMgr sInstance;

	public static void init(ActivityMain activity) {
		sInstance = new PageMgr(activity);
	}

	public static PageMgr getInstance() {
		return sInstance;
	}

	private ActivityMain mActivity;

	private List<Page> mPages = new ArrayList<>();
	private Page mSelected;

	private PageMgr(ActivityMain activity) {
		mActivity = activity;

		checkPage();
	}

	public List<Page> getPages() {
		return mPages;
	}

	public Page getPage(WebView webView) {
		for (Page page : mPages) {
			if (page.webView == webView) {
				return page;
			}
		}

		return null;
	}

	public Page addPage() {
		Page page = new Page(mActivity);
		page.webView = mActivity.createWebView();

		mPages.add(page);
		selectPage(page);

		return page;
	}

	public Page getSelectedPage() {
		return mSelected;
	}

	public void selectPage(Page page) {
		for (int i = 0; i < mPages.size(); i++) {
			if (mPages.get(i) == page) {
				selectPage(i);
				return;
			}
		}
	}

	public void selectPage(int index) {
		if (index < 0 || index >= mPages.size()) {
			return;
		}

		Page page = mPages.get(index);
		mSelected = page;
		mActivity.selectPage(page);
	}

	public void removePage(Page page) {
		for (int i = 0; i < mPages.size(); i++) {
			if (mPages.get(i) == page) {
				removePage(i);
				break;
			}
		}
	}

	public void removePage(int index) {
		if (index < 0 || index >= mPages.size()) {
			return;
		}

		Page page = mPages.remove(index);
		mActivity.removePage(page);

		checkPage();

		if (page == mSelected) {
			selectPage(mPages.size() - 1);
		}
	}

	public void checkPage() {
		if (mPages.size() < 1) {
			addPage();
		}
	}
}