package com.shk.mao.struct;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.WebView;

import com.shk.mao.R;

public class Page {
	public WebView webView;
	public Bitmap icon;
	public String title;
	public float progress;

	public Page(Context context) {
		icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
		title = context.getString(R.string.title_default);
	}
}