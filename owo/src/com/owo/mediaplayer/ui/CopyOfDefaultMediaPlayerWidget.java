package com.owo.mediaplayer.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.owo.app.theme.Theme;
import com.owo.base.common.Param;
import com.owo.base.pattern.Singleton;
import com.owo.base.pattern.State;
import com.owo.base.pattern.TreeState;
import com.owo.base.util.DimensionUtil;
import com.owo.ui.utils.LP;

public class CopyOfDefaultMediaPlayerWidget extends AbsMediaPlayerWidget {

	public CopyOfDefaultMediaPlayerWidget(Context context) {
		super(context);
		initComponents();
	}

	@SuppressLint("NewApi")
	@Override
	protected void setupLayout() {
		// top: title
		// center: loading or lock
		FrameLayout center = new FrameLayout(getContext());
		center.addView(mLoadingBar, LP.FWWC);
		center.addView(mLock, LP.FWWC);
		// bottom: loading progress

		// 1) surface view has add as first child in parent
		// 2) progress bar
		LinearLayout playProgressLinearLayout = new LinearLayout(getContext());
		playProgressLinearLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams timeLP = new LinearLayout.LayoutParams(
				DimensionUtil.w(150), LinearLayout.LayoutParams.WRAP_CONTENT);
		playProgressLinearLayout.addView(mCurrentTime, timeLP);
		playProgressLinearLayout.addView(mSeekBar,
				new LinearLayout.LayoutParams(0,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		playProgressLinearLayout.addView(mEndTime, timeLP);

		// control bar
		SwitchView startStop = new SwitchView(getContext());
		startStop.of(mPause, mResume);

		SwitchView fullScreen = new SwitchView(getContext());
		fullScreen.of(mEnterFullScreen, mExitFullScreen);

		LinearLayout controlBar = new LinearLayout(getContext());
		controlBar.setGravity(Gravity.CENTER);
		int size = DimensionUtil.rowHeight();
		LinearLayout.LayoutParams iconLP = new LinearLayout.LayoutParams(size,
				size);
		controlBar.addView(mPre, iconLP);
		controlBar.addView(startStop, iconLP);
		controlBar.addView(mNext, iconLP);
		controlBar.addView(fullScreen, iconLP);

		// bottom bar
		LinearLayout bottomBar = new LinearLayout(getContext());
		bottomBar.setOrientation(LinearLayout.VERTICAL);
		bottomBar.setGravity(Gravity.CENTER);
		bottomBar.addView(playProgressLinearLayout, LP.LMW());
		bottomBar.addView(controlBar, LP.LMW());

		addView(bottomBar, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
		bottomBar.setBackgroundColor(Theme.alpha(Singleton.of(Theme.class)
				.bgColor(), 100));

		LinearLayout overlay = new LinearLayout(getContext());
		overlay.setOrientation(LinearLayout.VERTICAL);
		overlay.addView(mTitle, LP.LMW());
		overlay.addView(center, LP.LW01);
		overlay.addView(bottomBar, LP.LMW());
		addView(overlay);
		addView(mUnLock, LP.FWWC);
	}

	private void initComponents() {
		mGestureDetector = new GestureDetector(getContext(),
				new OnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onDown(MotionEvent e) {
						mState.handleMessage(MI_TOUCH, null, null);
						return false;
					}
				});
	}

	private GestureDetector mGestureDetector;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		mGestureDetector.onTouchEvent(ev);
		return super.onInterceptTouchEvent(ev);
	}

	List<Integer> mStates = new ArrayList<>();
	private int mCurStateIndex = -1;
	private static final int STATE_FLOAT = 0;
	private static final int STATE_NOT_FLOAT = 1;
	private static final int STATE_OVERLAY = 2;
	private static final int STATE_NOT_OVERLAY = 3;
	private static final int STATE_LOCKED = 4;
	private static final int STATE_NOT_LOCKED = 5;
	private static final int STATE_LOADING = 6;
	private static final int STATE_NOT_LOADING = 7;

	private State mState;

	private void setupStateMachine() {
		StateLoading stateLoading = new StateLoading();
		StateNotLoading stateNotLoading = new StateNotLoading();

		StateLocked stateLocked = new StateLocked();
		StateNotLocked stateNotLocked = new StateNotLocked();
		stateNotLocked.add(stateLoading);
		stateNotLocked.add(stateNotLoading);

		StateOverlay stateOverlay = new StateOverlay();
		StateNotOverlay stateNotOverlay = new StateNotOverlay();
		stateOverlay.add(stateLocked);
		stateOverlay.add(stateNotLocked);

		StateFloat stateFloat = new StateFloat();
		StateNotFloat stateNotFloat = new StateNotFloat();
		stateFloat.add(stateOverlay);
		stateFloat.add(stateNotOverlay);
		stateNotFloat.add(stateOverlay);
		stateNotFloat.add(stateNotOverlay);

		StateMain stateMain = new StateMain();
		stateMain.add(stateFloat);
		stateMain.add(stateNotFloat);

		mState = stateMain;
	}

	/*
	 * Message Ids
	 */
	private static final int MI_START_LOADING = 0;
	private static final int MI_STOP_LOADING = 1;
	private static final int MI_LOCK = 2;
	private static final int MI_UNLOCK = 3;
	private static final int MI_SHOW_OVERLAY = 4;
	private static final int MI_HIDE_OVERLAY = 5;
	private static final int MI_FLOAT = 6;
	private static final int MI_EXIT_FLOAT = 7;

	private static final int MI_TOUCH = 0;

	private class StateMain extends TreeState {

		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateFloat extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateNotFloat extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateOverlay extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateNotOverlay extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateLocked extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateNotLocked extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateLoading extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private class StateNotLoading extends TreeState {
		@Override
		public void onSwitchIn() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean handleMessageImpl(int id, Param in, Param out) {
			// TODO Auto-generated method stub
			return false;
		}
	}

}
