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
import com.owo.mediaplayer.view.StopShape;

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
		LinearLayout container = new LinearLayout(this);
		container.setOrientation(LinearLayout.VERTICAL);
		// stop
		ShapeDrawable sp = new ShapeDrawable();
		sp.setShape(new StopShape());
		View view = new View(this);
		view.setBackgroundDrawable(sp);
		container.addView(view, new LinearLayout.LayoutParams(200, 200));
		// start
		ShapeDrawable sp2 = new ShapeDrawable();
		sp2.setShape(new StartShape());
		View view2 = new View(this);
		view2.setBackgroundDrawable(sp2);
		container.addView(view2, new LinearLayout.LayoutParams(200, 200));
		// fast forward
		ShapeDrawable sp3 = new ShapeDrawable();
		sp3.setShape(new FastForwardShape());
		View view3 = new View(this);
		view3.setBackgroundDrawable(sp3);
		container.addView(view3, new LinearLayout.LayoutParams(200, 200));

		// lock
		ShapeDrawable sp4 = new ShapeDrawable();
		sp4.setShape(new LockShape());
		View view4 = new View(this);
		view4.setBackgroundDrawable(sp4);
		container.addView(view4, new LinearLayout.LayoutParams(200, 200));

		// full screen
		ShapeDrawable sp5 = new ShapeDrawable();
		sp5.setShape(new FullScreenShape());
		View view5 = new View(this);
		view5.setBackgroundDrawable(sp5);
		container.addView(view5, new LinearLayout.LayoutParams(200, 200));
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
