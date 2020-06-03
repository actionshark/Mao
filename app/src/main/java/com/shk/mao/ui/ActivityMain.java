package com.shk.mao.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.shk.mao.R;
import com.shk.mao.struct.Page;
import com.shk.mao.struct.PageMgr;
import com.shk.mao.util.UrlUtil;
import com.shk.mao.struct.WebViewListener;
import com.shk.mao.util.WebViewUtil;

public class ActivityMain extends AppCompatActivity {
	private ImageView mIcon;
	private TextView mTitle;

	private EditText mAddress;

	private View mProgress;

	private ViewGroup mContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		mIcon = findViewById(R.id.icon);
		mTitle = findViewById(R.id.title);

		mAddress = findViewById(R.id.address);
		mAddress.setSelectAllOnFocus(true);
		mAddress.setOnEditorActionListener((textView, actionId, event) -> {
			onInputAddress();
			return true;
		});
		mAddress.setOnFocusChangeListener((view, hasFocus) -> {
			if (!hasFocus) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		});

		findViewById(R.id.go).setOnClickListener((view) -> {
			onInputAddress();
		});

		mProgress = findViewById(R.id.progress);

		mContainer = findViewById(R.id.container);

		findViewById(R.id.setting).setOnClickListener((view) -> {
		});

		findViewById(R.id.page).setOnClickListener((view) -> {
			Intent intent = new Intent(this, ActivityPage.class);
			startActivity(intent);
		});

		findViewById(R.id.menu).setOnClickListener((view) -> {
		});

		PageMgr.init(this);
	}

	public WebView createWebView() {
		WebView webView = WebViewUtil.createWebView(this, new WebViewListener() {
			@Override
			public void onReceivedIcon(WebView webView, Bitmap icon) {
				Page page = PageMgr.getInstance().getPage(webView);
				if (page != null) {
					page.icon = icon;

					if (page == PageMgr.getInstance().getSelectedPage()) {
						mIcon.setImageBitmap(icon);
					}
				}
			}

			@Override
			public void onReceivedTitle(WebView webView, String title) {
				Page page = PageMgr.getInstance().getPage(webView);
				if (page != null) {
					page.title = title;

					if (page == PageMgr.getInstance().getSelectedPage()) {
						mTitle.setText(title);
					}
				}
			}

			@Override
			public void onUrlChanged(WebView webView, String url) {
				Page page = PageMgr.getInstance().getPage(webView);
				if (page != null) {
					if (page == PageMgr.getInstance().getSelectedPage()) {
						mAddress.setText(url);
					}
				}
			}

			@Override
			public void onProgressChanged(WebView webView, float progress) {
				Page page = PageMgr.getInstance().getPage(webView);
				if (page != null) {
					page.progress = progress;

					if (page == PageMgr.getInstance().getSelectedPage()) {
						float width = mContainer.getWidth() * progress;
						mProgress.getLayoutParams().width = (int) width;
					}
				}
			}
		});

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		webView.setLayoutParams(lp);
		mContainer.addView(webView);

		return webView;
	}

	public void selectPage(Page page) {
		page.webView.bringToFront();

		mIcon.setImageBitmap(page.icon);

		mTitle.setText(page.title);

		mAddress.setText(page.webView.getUrl());

		float width = mContainer.getWidth() * page.progress;
		mProgress.getLayoutParams().width = (int) width;
	}

	public void removePage(Page page) {
		mContainer.removeView(page.webView);
		WebViewUtil.destroyWebView(page.webView);
	}

	private void onInputAddress() {
		String url = mAddress.getText().toString();
		url = UrlUtil.checkUrl(url);
		mAddress.setText(url);
		mAddress.clearFocus();

		Page page = PageMgr.getInstance().getSelectedPage();
		page.webView.loadUrl(url);
	}
}