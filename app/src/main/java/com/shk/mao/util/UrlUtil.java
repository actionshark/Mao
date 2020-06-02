package com.shk.mao.util;

public class UrlUtil {
	public static String checkUrl(String url) {
		if (url == null || url.length() == 0) {
			return "about:blank";
		}

		if (!url.startsWith("http")) {
			url = "http://" + url;
		}

		return url;
	}
}
