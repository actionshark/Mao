package com.shk.mao.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shk.mao.struct.WebViewListener;

public class WebViewUtil {
	public static WebView createWebView(Context context, WebViewListener listener) {
		WebView webView = new WebView(context);

		WebSettings ws = webView.getSettings();
		ws.setAllowContentAccess(true);
		ws.setAllowFileAccess(true);
		ws.setAllowFileAccessFromFileURLs(true);
		ws.setAllowUniversalAccessFromFileURLs(true);
		ws.setAppCacheEnabled(true);
		ws.setDatabaseEnabled(true);
		ws.setDomStorageEnabled(true);
		ws.setJavaScriptCanOpenWindowsAutomatically(true);
		ws.setJavaScriptEnabled(true);
		ws.setUseWideViewPort(true);
		ws.setLoadWithOverviewMode(true);
		ws.setSupportZoom(true);

		CookieManager.getInstance().setAcceptCookie(true);
		if (Build.VERSION.SDK_INT >= 21) {
			CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
		}

		if (Build.VERSION.SDK_INT >= 21) {
			ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView webView, int progress) {
				super.onProgressChanged(webView, progress);

				if (listener != null) {
					listener.onProgressChanged(webView, progress / 100f);
				}
			}

			@Override
			public void onReceivedTitle(WebView webView, String title) {
				super.onReceivedTitle(webView, title);

				if (listener != null) {
					listener.onReceivedTitle(webView, title);
				}
			}

			@Override
			public void onReceivedIcon(WebView webView, Bitmap icon) {
				super.onReceivedIcon(webView, icon);

				if (listener != null) {
					listener.onReceivedIcon(webView, icon);
				}
			}

			@Override
			public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
				return true;
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				return shouldOverrideUrlLoadingInternal(webView, url, null);
			}

			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest wrq) {
				return shouldOverrideUrlLoadingInternal(webView, null, wrq.getUrl());
			}

			private boolean shouldOverrideUrlLoadingInternal(WebView webView, String url, Uri uri) {
				if (url == null) {
					try {
						url = uri.toString();
					} catch (Exception e) {
					}
				}

				if (uri == null) {
					try {
						uri = Uri.parse(url);
					} catch (Exception e) {
					}
				}

				if (url != null) {
					if (url.startsWith("http:") || url.startsWith("https:")) {
						return false;
					}
				}

				if (uri != null) {
					try {
						Intent intent = Intent.parseUri(uri.toString(), Intent.URI_ALLOW_UNSAFE);
						context.startActivity(intent);
						return true;
					} catch (Exception e) {
					}
				}

				return false;
			}

			@Override
			public void onReceivedSslError(WebView webView, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});

		webView.setDownloadListener((String url, String userAgent, String contentDisposition, String mimeType, long contentLength) -> {
			// TODO
		});

		return webView;
	}
}
