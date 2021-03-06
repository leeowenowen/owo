package com.owo.mediaplayer.android;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import android.view.SurfaceHolder;

import com.owo.app.test.MetaData;
import com.owo.mediaplayer.interfaces.AbsMediaPlayer;

public class SysMediaPlayer extends AbsMediaPlayer {

	private static final String TAG = "SysMediaPlayer";
	private MediaPlayer mMediaPlayer;

	@Override
	public void create(Context context) {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
		mMediaPlayer.setOnCompletionListener(mCompletionListener);
		mMediaPlayer.setOnErrorListener(mErrorListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
	}

	@Override
	public void attachSurface(SurfaceHolder surface) {
		mMediaPlayer.setDisplay(surface);
	}

	@Override
	public void detachSurface() {
		//mMediaPlayer.setDisplay(null);
	}

	@Override
	public void destroy() {
		mIsValid = false;
		mMediaPlayer.release();
	}

	private OnErrorListener mErrorListener = new OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Log.e(TAG, "onError[what:" + what + "][extra:" + extra + "]");

			for (Listener observer : observers()) {
				// TODO: convert error code
				observer.onError(what);
			}
			return false;
		}
	};

	private boolean mIsValid;
	private MediaPlayer.OnPreparedListener mPreparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			mIsValid = true;
			int width = mp.getVideoWidth();
			int height = mp.getVideoHeight();
			SysMetaInfo metaInfo = new SysMetaInfo();
			metaInfo.width(width);
			metaInfo.height(height);
			MetaData metaData = new MetaData(mp);
			metaData.exists();
			mMediaPlayer.start();
			if (mPendingSeekOffset != 0) {
				mMediaPlayer.seekTo(mPendingSeekOffset);
				mPendingSeekOffset = 0;
			}
			for (Listener observer : observers()) {
				observer.onLoadStop();
				observer.onMetaInfo(metaInfo);
				observer.onStart();
			}
		}
	};

	MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
			for (Listener observer : observers()) {
				observer.onSizeChanged(width, height);
			}
		}
	};

	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			for (Listener observer : observers()) {
				observer.onComplete();
			}
		}
	};

	private MediaPlayer.OnInfoListener mOnInfoListener = new OnInfoListener() {
		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra) {
			Log.v(TAG, "onInfo[what:" + what + "][extra:" + extra + "]");
			return false;
		}
	};

	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			for (Listener observer : observers()) {
				observer.onBufferingUpdate(percent);
			}
		}
	};

	@Override
	public void uri(String uri) {
		try {
			mMediaPlayer.setDataSource(uri);
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
	}

	private int mPendingSeekOffset;

	@Override
	public void start() {
		// 1) do prepare and start asynchronously
		mIsValid = false;
		mMediaPlayer.setOnPreparedListener(mPreparedListener);
		mMediaPlayer.prepareAsync();

		// 2) notify observers
		for (Listener observer : observers()) {
			observer.onLoadStart("Preparing");
		}
	}

	@Override
	public void stop() {
		// 1) do stop
		mMediaPlayer.stop();
		// 2) notify observers
		for (Listener observer : observers()) {
			observer.onStop();
		}
	}

	@Override
	public void pause() {
		// 1) do pause
		mMediaPlayer.pause();
		// 2) notify observers
		for (Listener observer : observers()) {
			observer.onPause();
		}
	}

	@Override
	public void resume() {
		// 1) do resume
		mMediaPlayer.start();
		// 2) notify observers
		for (Listener observer : observers()) {
			observer.onResume();
		}
	}

	@Override
	public int width() {
		return mMediaPlayer.getVideoWidth();
	}

	@Override
	public int height() {
		return mMediaPlayer.getVideoHeight();
	}

	@Override
	public int duration() {
		return mMediaPlayer.getDuration();
	}

	@Override
	public int current() {
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public void seek(int position) {
		if (mIsValid) {
			mMediaPlayer.seekTo(position);
		} else {
			mPendingSeekOffset = position;
		}
	}

	@Override
	public void reset() {
		// 1)TODO: check state
		// 2) do reset
		mMediaPlayer.reset();
		// 3) notify observers
		for (Listener observer : observers()) {
			observer.onReset();
		}
	}
}
