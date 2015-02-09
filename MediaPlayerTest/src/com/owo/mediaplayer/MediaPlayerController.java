package com.owo.mediaplayer;

import android.content.Context;
import android.view.SurfaceHolder;

import com.owo.mediaplayer.android.SysMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayer;
import com.owo.mediaplayer.interfaces.IMediaPlayerController;
import com.owo.mediaplayer.interfaces.IMetaInfo;

public class MediaPlayerController implements IMediaPlayerController,
		IMediaPlayer.Listener {

	private IMediaPlayer mMediaPlayer;

	@Override
	public void create(Context context, SurfaceHolder surface) {
		mMediaPlayer = new SysMediaPlayer();
		mMediaPlayer.addListener(this);
		mMediaPlayer.create(context, surface);
	}

	@Override
	public void destroy() {
		mMediaPlayer.destroy();
	}

	@Override
	public void stop() {
		mMediaPlayer.stop();
	}

	@Override
	public void pause() {
		mMediaPlayer.pause();
	}

	@Override
	public void resume() {
		mMediaPlayer.resume();
	}

	@Override
	public int width() {
		return mMediaPlayer.width();
	}

	@Override
	public int height() {
		return mMediaPlayer.height();
	}

	@Override
	public int duration() {
		return mMediaPlayer.duration();
	}

	@Override
	public int current() {
		return mMediaPlayer.current();
	}

	@Override
	public void seek(int position) {
		mMediaPlayer.seek(position);
	}

	@Override
	public void pre() {
		// TODO Auto-generated method stub

	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fastForward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fastBackward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fullScreen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void seek(int progress, int max) {
		int position = progress * mMediaPlayer.duration() / max;
		mMediaPlayer.seek(position);
	}

	@Override
	public void start() {
		if (mMediaPlayer == null) {
			return;
		}
		mMediaPlayer.start();
	}

	@Override
	public void reset() {
		mMediaPlayer.reset();
	}

	@Override
	public void uri(String uri) {
		mMediaPlayer.uri(uri);
	}

	private Client mClient;

	@Override
	public void client(Client client) {
		mClient = client;
	}

	/**
	 * IMediaPlayer.Listener
	 */
	@Override
	public void onStart() {
		mClient.onStart();
	}

	@Override
	public void onStop() {
		mClient.onStop();
	}

	@Override
	public void onPause() {
		mClient.onPause();
	}

	@Override
	public void onResume() {
		mClient.onResume();
	}

	@Override
	public void onProgressChanged(int position) {
		int progress = position * 100 / duration();
		mClient.onProgressChanged(progress);
	}

	@Override
	public void onLoadStart(String msg) {
		mClient.onLoadStart(msg);
	}

	@Override
	public void onLoadStop() {
		mClient.onLoadStop();
	}

	@Override
	public void onSizeChanged(int w, int h) {
		mClient.onSizeChanged(w, h);
	}

	@Override
	public void onError(int error) {
		mClient.onError(error);
	}

	@Override
	public void onReset() {
		mClient.onReset();
	}

	@Override
	public void onMetaInfo(IMetaInfo metaInfo) {
		mClient.onMetaInfo(metaInfo);
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBufferingUpdate(int percent) {
		// TODO Auto-generated method stub

	}

}
