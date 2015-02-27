package com.owo.app.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

	private VideoView mVideoView;
	private MediaController mMediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mVideoView = new VideoView(this);
		mMediaController = new MediaController(this);
		mVideoView.setMediaController(mMediaController);
		String dataSource = Environment.getExternalStorageDirectory().getPath()
				+ "/a.mp4";
		// mVideoView
		// .setVideoPath("http://v.qq.com/cover/8/8gcph2sck1u9wsn.html?vid=w001545h79f");
		mVideoView.setVideoPath(MediaUrls.sRealStream);
		mVideoView.start();
		setContentView(mVideoView);
	}
}
