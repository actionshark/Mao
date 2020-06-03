package com.shk.mao.struct;

import android.graphics.Bitmap;
import android.webkit.WebView;

public interface WebViewListener {
	void onUrlChanged(WebView webView, String url);
	void onProgressChanged(WebView webView, float progress);
	void onReceivedTitle(WebView webView, String title);
	void onReceivedIcon(WebView webView, Bitmap icon);
}