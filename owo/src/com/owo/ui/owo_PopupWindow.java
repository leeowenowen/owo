package com.owo.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class owo_PopupWindow extends PopupWindow {
	private OnTouchOutsideListener mOnTouchOutsideListener = null;
	private OnTouchInsideListener mOnTouchInsideListener = null;
	private OnKeyEventListener mOnKeyEventListener = null;

	private owo_PopupViewContainer mViewContainer = null;

	Context mContext = null;

	public owo_PopupWindow(Context context) {
		super(context);
		// Must set PopupWindow's background to null, otherwise we can't process
		// touch or key event.
		// super.setBackgroundDrawable(null);
		setBackgroundDrawable(new ColorDrawable(Color.argb(100, 255, 0, 0)));
		//setOutsideTouchable(true);
		setTouchInterceptor(mTouchInterceptor);
		mViewContainer = new owo_PopupViewContainer(context);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		mViewContainer.setLayoutParams(params);
		mOnTouchOutsideListener = mDefaultOnTouchOutsideListener;
		mOnKeyEventListener = mDefaultOnKeyEventListener;
	}

	private View mContentView;

	@Override
	public View getContentView() {
		return mContentView;
	}

	@Override
	public void setContentView(View contentView) {
		mContentView = contentView;
		if (mViewContainer.getChildCount() > 0) {
			mViewContainer.removeAllViews();
			hideSoftInputWhenRemoveViews();
		}

		if (null != mContentView) {
			mViewContainer.addView((View) mContentView);
		}
		super.setContentView(mViewContainer);
	}

	public void removeContentView() {
		if (null != mContentView) {
			mViewContainer.removeAllViews();
		}
	}

	private void hideSoftInputWhenRemoveViews() {
		InputMethodManager inputManager = (InputMethodManager) mViewContainer.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		try {
			inputManager.hideSoftInputFromWindow(mViewContainer.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	public void setContentViewGravity(int gravity) {
		mViewContainer.setGravity(gravity);
	}

	public void setOnTouchOutsideListener(OnTouchOutsideListener listener) {
		mOnTouchOutsideListener = listener;
	}

	public void setOnTouchInsideListener(OnTouchInsideListener listener) {
		mOnTouchInsideListener = listener;
	}

	private OnTouchOutsideListener mDefaultOnTouchOutsideListener = new OnTouchOutsideListener() {

		@Override
		public void onTouchOutside(owo_PopupWindow popup, View v, MotionEvent event) {
			dismiss();
		}
	};

	private OnKeyEventListener mDefaultOnKeyEventListener = new OnKeyEventListener() {
		@Override
		public void OnKeyBackClicked(owo_PopupWindow popup, KeyEvent event) {
			dismiss();
		};

		@Override
		public void OnKeyMenuClicked(owo_PopupWindow popup, KeyEvent event) {
		};
	};

	public static interface OnTouchOutsideListener {
		void onTouchOutside(owo_PopupWindow popup, View v, MotionEvent event);
	}

	public static interface OnTouchInsideListener {
		void onTouchInside(owo_PopupWindow popup, View v, MotionEvent event);
	}

	private OnTouchListener mTouchInterceptor = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final int x = (int) event.getX();
			final int y = (int) event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_DOWN:
				if ((x < 0) || (x > v.getWidth()) || (y < 0) || (y > v.getHeight())) {
					if (mOnTouchOutsideListener != null) {
						mOnTouchOutsideListener.onTouchOutside(owo_PopupWindow.this, v, event);
					}
					return true;
				} else {
					if (mOnTouchInsideListener != null) {
						mOnTouchInsideListener.onTouchInside(owo_PopupWindow.this, v, event);
					}
				}
				break;

			case MotionEvent.ACTION_OUTSIDE:
				if (mOnTouchOutsideListener != null) {
					mOnTouchOutsideListener.onTouchOutside(owo_PopupWindow.this, v, event);
				}
				return true;
			}

			return false;
		}
	};

	public void setOnKeyClickedListener(OnKeyEventListener l) {
		mOnKeyEventListener = l;
	}

	public static class OnKeyEventListener {
		public void OnKeyBackClicked(owo_PopupWindow popup, KeyEvent event) {
		};

		public void OnKeyMenuClicked(owo_PopupWindow popup, KeyEvent event) {
		};
	}

	private class owo_PopupViewContainer extends RelativeLayout {
		@SuppressWarnings("unused")
		private static final String TAG = "owo_PopupWindow2.UcPopupViewContainer";

		public owo_PopupViewContainer(Context context) {
			super(context);
		}

		@Override
		public boolean dispatchKeyEvent(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
				if (getKeyDispatcherState() == null) {
					return super.dispatchKeyEvent(event);
				}

				if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
					KeyEvent.DispatcherState state = getKeyDispatcherState();
					if (state != null) {
						state.startTracking(event, this);
					}
					return true;
				} else if (event.getAction() == KeyEvent.ACTION_UP) {
					KeyEvent.DispatcherState state = getKeyDispatcherState();
					if (state != null && state.isTracking(event) && !event.isCanceled()) {
						if (mOnKeyEventListener != null) {
							if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
								mOnKeyEventListener.OnKeyBackClicked(owo_PopupWindow.this, event);
							} else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
								mOnKeyEventListener.OnKeyMenuClicked(owo_PopupWindow.this, event);
							}
						}
						return true;
					}
				}
				return super.dispatchKeyEvent(event);
			} else {
				return super.dispatchKeyEvent(event);
			}
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			if (mTouchInterceptor != null && mTouchInterceptor.onTouch(this, ev)) {
				return true;
			}

			boolean isHandled = true;

			// dispatchTouchEvent() may throw exception on ASUS TF300T, we don't
			// know the real reason,
			// just catch the exception here. It's a compatibility problem.
			try {
				isHandled = super.dispatchTouchEvent(ev);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return isHandled;
		}

		@Override
		public void sendAccessibilityEvent(int eventType) {
			// clients are interested in the content not the container, make it
			// event source
			if (mContentView != null) {
				((View) mContentView).sendAccessibilityEvent(eventType);
			} else {
				super.sendAccessibilityEvent(eventType);
			}
		}
	}
}
