package com.owo.widget;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.owo.app.util.ReflectHelper;

/**
 * Container for a tab window view. This object holds two children: a set of tab
 * labels that the user clicks to select a specific tab, and a FrameLayout
 * object that displays the contents of that page. The individual elements are
 * typically controlled using this container object, rather than setting values
 * on the child elements themselves.
 * 
 */
public class owo_TabHost extends LinearLayout implements
		ViewTreeObserver.OnTouchModeChangeListener {

	public static final int owo_TabWidget_LOCATION_LEFT = 0;
	public static final int owo_TabWidget_LOCATION_TOP = 1;
	public static final int owo_TabWidget_LOCATION_RIGHT = 2;
	public static final int owo_TabWidget_LOCATION_BOTTOM = 3;
	private owo_TabWidget mowo_TabWidget;
	private FrameLayout mTabContent;
	private List<TabSpec> mTabSpecs = new ArrayList<TabSpec>(2);

	protected int mCurrentTab = -1;
	private View mCurrentView = null;
	private OnTabChangeListener mOnTabChangeListener;
	private OnKeyListener mTabKeyListener;

	public owo_TabHost(Context context) {
		super(context);
		initTabHost();
	}

	private void initTabHost() {
		setFocusableInTouchMode(true);
		setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);

		mCurrentTab = -1;
		mCurrentView = null;

		mowo_TabWidget = new owo_TabWidget(getContext());

		// KeyListener to attach to all tabs. Detects non-navigation keys
		// and relays them to the tab content.
		mTabKeyListener = new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case KeyEvent.KEYCODE_DPAD_LEFT:
				case KeyEvent.KEYCODE_DPAD_RIGHT:
				case KeyEvent.KEYCODE_DPAD_UP:
				case KeyEvent.KEYCODE_DPAD_DOWN:
				case KeyEvent.KEYCODE_ENTER:
					return false;

				}
				mTabContent.requestFocus(View.FOCUS_FORWARD);
				return mTabContent.dispatchKeyEvent(event);
			}

		};

		mowo_TabWidget
				.setTabSelectionListener(new owo_TabWidget.OnTabSelectionChanged() {
					public void onTabSelectionChanged(int tabIndex,
							boolean clicked) {
						setCurrentTab(tabIndex);
						if (clicked) {
							mTabContent.requestFocus(View.FOCUS_FORWARD);
						}
					}
				});

		mTabContent = new FrameLayout(getContext());

		setOrientation(LinearLayout.VERTICAL);
		addView(mowo_TabWidget);
		addView(mTabContent);
	}

	/**
	 * Get a new {@link TabSpec} associated with this tab host.
	 * 
	 * @param tag
	 *            required tag of tab.
	 */
	public TabSpec newTabSpec(String tag) {
		return new TabSpec(tag);
	}

	@Override
	public void sendAccessibilityEvent(int eventType) {
		/* avoid super class behavior - owo_TabWidget sends the right events */
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		final ViewTreeObserver treeObserver = getViewTreeObserver();
		treeObserver.addOnTouchModeChangeListener(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		final ViewTreeObserver treeObserver = getViewTreeObserver();
		treeObserver.removeOnTouchModeChangeListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onTouchModeChanged(boolean isInTouchMode) {
		if (!isInTouchMode) {
			// leaving touch mode.. if nothing has focus, let's give it to
			// the indicator of the current tab
			if (mCurrentView != null
					&& (!mCurrentView.hasFocus() || mCurrentView.isFocused())) {
				mowo_TabWidget.getChildTabViewAt(mCurrentTab).requestFocus();
			}
		}
	}

	/**
	 * Add a tab.
	 * 
	 * @param tabSpec
	 *            Specifies how to create the indicator and content.
	 */
	public void addTab(TabSpec tabSpec) {

		if (tabSpec.mIndicatorStrategy == null) {
			throw new IllegalArgumentException(
					"you must specify a way to create the tab indicator.");
		}

		if (tabSpec.mContentStrategy == null) {
			throw new IllegalArgumentException(
					"you must specify a way to create the tab content");
		}
		View tabIndicator = tabSpec.mIndicatorStrategy.createIndicatorView();
		tabIndicator.setOnKeyListener(mTabKeyListener);

		// If this is a custom view, then do not draw the bottom strips for
		// the tab indicators.
		if (tabSpec.mIndicatorStrategy instanceof ViewIndicatorStrategy) {
			mowo_TabWidget.setStripEnabled(false);
		}

		mowo_TabWidget.addView(tabIndicator);
		mTabSpecs.add(tabSpec);

		if (mCurrentTab == -1) {
			setCurrentTab(0);
		}
	}

	/**
	 * Removes all tabs from the tab widget associated with this tab host.
	 */
	public void clearAllTabs() {
		mowo_TabWidget.removeAllViews();
		initTabHost();
		mTabContent.removeAllViews();
		mTabSpecs.clear();
		requestLayout();
		invalidate();
	}

	public owo_TabWidget getTabWidget() {
		return mowo_TabWidget;
	}

	public int getCurrentTab() {
		return mCurrentTab;
	}

	public String getCurrentTabTag() {
		if (mCurrentTab >= 0 && mCurrentTab < mTabSpecs.size()) {
			return mTabSpecs.get(mCurrentTab).getTag();
		}
		return null;
	}

	public View getCurrentTabView() {
		if (mCurrentTab >= 0 && mCurrentTab < mTabSpecs.size()) {
			return mowo_TabWidget.getChildTabViewAt(mCurrentTab);
		}
		return null;
	}

	public View getCurrentView() {
		return mCurrentView;
	}

	public void setCurrentTabByTag(String tag) {
		int i;
		for (i = 0; i < mTabSpecs.size(); i++) {
			if (mTabSpecs.get(i).getTag().equals(tag)) {
				setCurrentTab(i);
				break;
			}
		}
	}

	/**
	 * Get the FrameLayout which holds tab content
	 */
	public FrameLayout getTabContentView() {
		return mTabContent;
	}

	/**
	 * Get the location of the owo_TabWidget.
	 * 
	 * @return The owo_TabWidget location.
	 */
	private int getowo_TabWidgetLocation() {
		int location = owo_TabWidget_LOCATION_TOP;

		switch (mowo_TabWidget.getOrientation()) {
		case LinearLayout.VERTICAL:
			location = (mTabContent.getLeft() < mowo_TabWidget.getLeft()) ? owo_TabWidget_LOCATION_RIGHT
					: owo_TabWidget_LOCATION_LEFT;
			break;
		case LinearLayout.HORIZONTAL:
		default:
			location = (mTabContent.getTop() < mowo_TabWidget.getTop()) ? owo_TabWidget_LOCATION_BOTTOM
					: owo_TabWidget_LOCATION_TOP;
			break;
		}
		return location;
	}

	public void setTabWidgetLocation(int location) {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		final boolean handled = super.dispatchKeyEvent(event);

		// unhandled key events change focus to tab indicator for embedded
		// activities when there is nothing that will take focus from default
		// focus searching
		Object obj = ReflectHelper.invoke(mCurrentView, "isRootNamespace");
		boolean flag = (obj == null) ? false : (Boolean) obj;
		if (!handled && (event.getAction() == KeyEvent.ACTION_DOWN)
				&& (mCurrentView != null) && (flag)
				&& (mCurrentView.hasFocus())) {
			int keyCodeShouldChangeFocus = KeyEvent.KEYCODE_DPAD_UP;
			int directionShouldChangeFocus = View.FOCUS_UP;
			int soundEffect = SoundEffectConstants.NAVIGATION_UP;

			switch (getowo_TabWidgetLocation()) {
			case owo_TabWidget_LOCATION_LEFT:
				keyCodeShouldChangeFocus = KeyEvent.KEYCODE_DPAD_LEFT;
				directionShouldChangeFocus = View.FOCUS_LEFT;
				soundEffect = SoundEffectConstants.NAVIGATION_LEFT;
				break;
			case owo_TabWidget_LOCATION_RIGHT:
				keyCodeShouldChangeFocus = KeyEvent.KEYCODE_DPAD_RIGHT;
				directionShouldChangeFocus = View.FOCUS_RIGHT;
				soundEffect = SoundEffectConstants.NAVIGATION_RIGHT;
				break;
			case owo_TabWidget_LOCATION_BOTTOM:
				keyCodeShouldChangeFocus = KeyEvent.KEYCODE_DPAD_DOWN;
				directionShouldChangeFocus = View.FOCUS_DOWN;
				soundEffect = SoundEffectConstants.NAVIGATION_DOWN;
				break;
			case owo_TabWidget_LOCATION_TOP:
			default:
				keyCodeShouldChangeFocus = KeyEvent.KEYCODE_DPAD_UP;
				directionShouldChangeFocus = View.FOCUS_UP;
				soundEffect = SoundEffectConstants.NAVIGATION_UP;
				break;
			}
			if (event.getKeyCode() == keyCodeShouldChangeFocus
					&& mCurrentView.findFocus().focusSearch(
							directionShouldChangeFocus) == null) {
				mowo_TabWidget.getChildTabViewAt(mCurrentTab).requestFocus();
				playSoundEffect(soundEffect);
				return true;
			}
		}
		return handled;
	}

	@Override
	public void dispatchWindowFocusChanged(boolean hasFocus) {
		if (mCurrentView != null) {
			mCurrentView.dispatchWindowFocusChanged(hasFocus);
		}
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(owo_TabHost.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(owo_TabHost.class.getName());
	}

	public void setCurrentTab(int index) {
		if (index < 0 || index >= mTabSpecs.size()) {
			return;
		}

		if (index == mCurrentTab) {
			return;
		}

		// notify old tab content
		if (mCurrentTab != -1) {
			mTabSpecs.get(mCurrentTab).mContentStrategy.tabClosed();
		}

		mCurrentTab = index;
		final owo_TabHost.TabSpec spec = mTabSpecs.get(index);

		// Call the tab widget's focusCurrentTab(), instead of just
		// selecting the tab.
		mowo_TabWidget.focusCurrentTab(mCurrentTab);

		// tab content
		mCurrentView = spec.mContentStrategy.getContentView();

		if (mCurrentView.getParent() == null) {
			mTabContent.addView(mCurrentView, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
		}

		if (!mowo_TabWidget.hasFocus()) {
			// if the tab widget didn't take focus (likely because we're in
			// touch mode)
			// give the current tab content view a shot
			mCurrentView.requestFocus();
		}

		// mTabContent.requestFocus(View.FOCUS_FORWARD);
		invokeOnTabChangeListener();
	}

	/**
	 * Register a callback to be invoked when the selected state of any of the
	 * items in this list changes
	 * 
	 * @param l
	 *            The callback that will run
	 */
	public void setOnTabChangedListener(OnTabChangeListener l) {
		mOnTabChangeListener = l;
	}

	private void invokeOnTabChangeListener() {
		if (mOnTabChangeListener != null) {
			mOnTabChangeListener.onTabChanged(getCurrentTabTag());
		}
	}

	/**
	 * Interface definition for a callback to be invoked when tab changed
	 */
	public interface OnTabChangeListener {
		void onTabChanged(String tabId);
	}

	/**
	 * Makes the content of a tab when it is selected. Use this if your tab
	 * content needs to be created on demand, i.e. you are not showing an
	 * existing view or starting an activity.
	 */
	public interface TabContentFactory {
		/**
		 * Callback to make the tab contents
		 * 
		 * @param tag
		 *            Which tab was selected.
		 * @return The view to display the contents of the selected tab.
		 */
		View createTabContent(String tag);
	}

	/**
	 * A tab has a tab indicator, content, and a tag that is used to keep track
	 * of it. This builder helps choose among these options.
	 * 
	 * For the tab indicator, your choices are: 1) set a label 2) set a label
	 * and an icon
	 * 
	 * For the tab content, your choices are: 1) the id of a {@link View} 2) a
	 * {@link TabContentFactory} that creates the {@link View} content. 3) an
	 * {@link Intent} that launches an {@link android.app.Activity}.
	 */
	public class TabSpec {

		private String mTag;

		private IndicatorStrategy mIndicatorStrategy;
		private ContentStrategy mContentStrategy;

		private TabSpec(String tag) {
			mTag = tag;
		}

		/**
		 * Specify a view as the tab indicator.
		 */
		public TabSpec setIndicator(View view) {
			mIndicatorStrategy = new ViewIndicatorStrategy(view);
			return this;
		}

		/**
		 * Specify a {@link android.widget.TabHost.TabContentFactory} to use to
		 * create the content of the tab.
		 */
		public TabSpec setContent(TabContentFactory contentFactory) {
			mContentStrategy = new FactoryContentStrategy(mTag, contentFactory);
			return this;
		}

		public String getTag() {
			return mTag;
		}
	}

	/**
	 * Specifies what you do to create a tab indicator.
	 */
	private static interface IndicatorStrategy {

		/**
		 * Return the view for the indicator.
		 */
		View createIndicatorView();
	}

	/**
	 * Specifies what you do to manage the tab content.
	 */
	private static interface ContentStrategy {

		/**
		 * Return the content view. The view should may be cached locally.
		 */
		View getContentView();

		/**
		 * Perhaps do something when the tab associated with this content has
		 * been closed (i.e make it invisible, or remove it).
		 */
		void tabClosed();
	}

	/**
	 * How to create a tab indicator by specifying a view.
	 */
	private class ViewIndicatorStrategy implements IndicatorStrategy {

		private final View mView;

		private ViewIndicatorStrategy(View view) {
			mView = view;
		}

		public View createIndicatorView() {
			return mView;
		}
	}

	/**
	 * How tab content is managed using {@link TabContentFactory}.
	 */
	private class FactoryContentStrategy implements ContentStrategy {
		private View mTabContent;
		private final CharSequence mTag;
		private TabContentFactory mFactory;

		public FactoryContentStrategy(CharSequence tag,
				TabContentFactory factory) {
			mTag = tag;
			mFactory = factory;
		}

		public View getContentView() {
			if (mTabContent == null) {
				mTabContent = mFactory.createTabContent(mTag.toString());
			}
			mTabContent.setVisibility(View.VISIBLE);
			return mTabContent;
		}

		public void tabClosed() {
			mTabContent.setVisibility(View.GONE);
		}
	}
}
