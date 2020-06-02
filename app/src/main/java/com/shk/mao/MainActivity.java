package com.shk.mao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private EditText mEtAddress;
	private Button mBtnEnter;

	private ViewGroup mVgContent;

	private List<WebView> mWebViews = new ArrayList<>();
	private int mCurrIndex = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mEtAddress = findViewById(R.id.et_address);

		mBtnEnter = findViewById(R.id.btn_enter);
		mBtnEnter.setOnClickListener((view) -> {
			String url = mEtAddress.getText().toString();
			url = UrlUtil.checkUrl(url);
			mEtAddress.setText(url);

			WebView webView;

			if (mCurrIndex < 0) {
				webView = WebViewUtil.createWebView(this);
				mWebViews.add(webView);
				mCurrIndex = 0;

				LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				webView.setLayoutParams(lp);
				mVgContent.addView(webView);
			} else {
				webView = mWebViews.get(mCurrIndex);
			}

			webView.loadUrl(url);
		});

		mVgContent = findViewById(R.id.vg_content);
	}
}
