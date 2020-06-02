package com.shk.mao.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.shk.mao.R;
import com.shk.mao.struct.WebViewItem;
import com.shk.mao.util.UrlUtil;
import com.shk.mao.struct.WebViewListener;
import com.shk.mao.util.WebViewUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private ImageView mIcon;
	private TextView mTitle;

	private EditText mAddress;
	private Button mEnter;

	private View mProgress;

	private ViewGroup mContent;

	private List<WebViewItem> mWebViews = new ArrayList<>();
	private WebViewItem mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mIcon = findViewById(R.id.icon);
		mTitle = findViewById(R.id.title);

		mAddress = findViewById(R.id.address);

		mEnter = findViewById(R.id.enter);
		mEnter.setOnClickListener((view) -> {
			String url = mAddress.getText().toString();
			url = UrlUtil.checkUrl(url);
			mAddress.setText(url);

			WebViewItem item = getWebView();
			item.webView.loadUrl(url);
		});

		mProgress = findViewById(R.id.progress);

		mContent = findViewById(R.id.vg_content);
	}

	private WebViewItem getWebView(WebView webView) {
		for (WebViewItem item : mWebViews) {
			if (item.webView == webView) {
				return item;
			}
		}

		return null;
	}

	private WebViewItem getWebView() {
		if (mWebView == null) {
			createWebView();
		}

		return mWebView;
	}

	private void createWebView() {
		WebView webView = WebViewUtil.createWebView(this, new WebViewListener() {
			@Override
			public void onProgressChanged(WebView webView, float progress) {
				WebViewItem item = getWebView(webView);
				if (item != null) {
					item.progress = progress;

					if (item == mWebView) {
						if (progress == 1f) {
							progress = 0f;
						}

						mProgress.setScaleX(progress);
					}
				}
			}

			@Override
			public void onReceivedTitle(WebView webView, String title) {
				WebViewItem item = getWebView(webView);
				if (item != null) {
					item.title = title;

					if (item == mWebView) {
						mTitle.setText(title);
					}
				}
			}

			@Override
			public void onReceivedIcon(WebView webView, Bitmap icon) {
				WebViewItem item = getWebView(webView);
				if (item != null) {
					item.icon = icon;

					if (item == mWebView) {
						mIcon.setImageBitmap(icon);
					}
				}
			}
		});

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		webView.setLayoutParams(lp);
		mContent.addView(webView);

		WebViewItem item = new WebViewItem();
		item.webView = webView;
		mWebViews.add(item);
		mWebView = item;
	}
}
