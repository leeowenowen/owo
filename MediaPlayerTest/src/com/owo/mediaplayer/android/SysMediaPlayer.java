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

import com.example.mediaplayertest.MetaData;
import com.owo.mediaplayer.core.SysMetaInfo;
import com.owo.mediaplayer.interfaces.AbsMediaPlayer;

public class SysMediaPlayer extends AbsMediaPlayer {

	private static final String TAG = "SysMediaPlayer";
	private MediaPlayer mMediaPlayer;
	private SurfaceHolder mSurfaceHolder;

	@Override
	public void create(Context context, SurfaceHolder surface) {
		mSurfaceHolder = surface;
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setDisplay(mSurfaceHolder);
		mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
		mMediaPlayer.setOnCompletionListener(mCompletionListener);
		mMediaPlayer.setOnErrorListener(mErrorListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
	}

	@Override
	public void destroy() {
		mMediaPlayer.release();
	}

	private OnErrorListener mErrorListener = new OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Log.v("xxx", "error[what:" + what + "][extra:" + extra + "]");

			for (Listener observer : observers()) {
				// TODO: convert error code
				observer.onError(what);
			}
			return false;
		}
	};

	private MediaPlayer.OnPreparedListener mPreparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			int width = mp.getVideoWidth();
			int height = mp.getVideoHeight();
			SysMetaInfo metaInfo = new SysMetaInfo();
			metaInfo.width(width);
			metaInfo.height(height);
			MetaData metaData = new MetaData(mp);
			metaData.exists();
			mMediaPlayer.start();
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
			// TODO Auto-generated method stub
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

	@Override
	public void start() {
		// 1) do prepare and start asynchronously
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
		mMediaPlayer.seekTo(position);
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
