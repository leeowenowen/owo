package com.owo.app;

import android.os.Environment;

public class MediaUrls {
	public static final String sLocal = Environment
			.getExternalStorageDirectory().getPath() + "/a.mp4";
	public static final String sNetAD = "http://g3.letv.cn/24/6/3/letv-uts/2522145-AVC-1611234-AAC-122967-2076960-463162247-274c0504575322ddd3fbc512818de8ae-1366891476558.flv";
	public static final String sRealStream = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
}
