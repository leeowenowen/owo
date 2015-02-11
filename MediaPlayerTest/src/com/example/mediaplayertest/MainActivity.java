package com.example.mediaplayertest;

import java.io.IOException;

import android.app.Activity;
import android.graphics.drawable.ShapeDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.owo.mediaplayer.view.FastForwardShape;
import com.owo.mediaplayer.view.FullScreenShape;
import com.owo.mediaplayer.view.LockShape;
import com.owo.mediaplayer.view.StartShape;
import com.owo.mediaplayer.view.VF;
import com.owo.mediaplayer.view.VF.ViewID;

public class MainActivity extends Activity {

	private MediaPlayer mMediaPlayer;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

		mSurfaceView = new SurfaceView(this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(mSurfaceHolderCallback);
		layout.addView(mSurfaceView);
		Button btn = new Button(this);
		btn.setText("VideoView play video");
		layout.addView(btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(MainActivity.this,
				// com.example.mediaplayertest.VideoViewActivity.class);
				// startActivity(intent);
			}
		});
		// setContentView(layout);
		/*
		 * 
		 */
		ContextManager.init(this);
		LinearLayout container = new LinearLayout(this);
		container.setOrientation(LinearLayout.VERTICAL);
		// stop
		container.addView(VF.of(this, ViewID.Stop),
				new LinearLayout.LayoutParams(200, 200));
		// start
		container.addView(VF.of(this, ViewID.Start),
				new LinearLayout.LayoutParams(200, 200));
		// fast forward
		container.addView(VF.of(this, ViewID.FastForward),
				new LinearLayout.LayoutParams(200, 200));

		// lock
		container.addView(VF.of(this, ViewID.Lock),
				new LinearLayout.LayoutParams(200, 200));

		// full screen
		container.addView(VF.of(this, ViewID.FullScreen),
				new LinearLayout.LayoutParams(200, 200));
		setContentView(container);

	}

	private MediaPlayer.OnPreparedListener mOnPreparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			int width = mp.getVideoWidth();
			int height = mp.getVideoHeight();

			MetaData metaData = new MetaData(mp);
			metaData.exists();
			mMediaPlayer.start();
		}
	};

	private SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder.setKeepScreenOn(true);

			mMediaPlayer = new MediaPlayer();
			try {
				mMediaPlayer.setDataSource(MediaUrls.sLocal);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setDisplay(mSurfaceHolder);
			mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
			mMediaPlayer.setOnErrorListener(mOnErrorListener);
			mMediaPlayer.prepareAsync();

		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub

		}
	};

	private OnErrorListener mOnErrorListener = new OnErrorListener() {

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Log.v("xxx", "error[what:" + what + "][extra:" + extra + "]");
			return false;
		}
	};
}
