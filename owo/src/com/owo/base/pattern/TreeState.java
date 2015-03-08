package com.owo.base.pattern;

import java.util.ArrayList;
import java.util.List;

import com.owo.base.common.Param;

public abstract class TreeState implements State {

	public TreeState(State... states) {
		for (State state : states) {
			add(state);
		}
	}

	/*
	 * if handle message in the order that root handle first
	 */
	private boolean mRootFirst = true;

	public void rootFirst(boolean rootFirst) {
		mRootFirst = rootFirst;
	}

	/*
	 * Sub states
	 */
	List<State> mSubStates = new ArrayList<State>();

	public void add(State state) {
		mSubStates.add(state);
	}

	public void remove(State state) {
		mSubStates.remove(state);
	}

	@Override
	public boolean handleMessage(int id, Param in, Param out) {
		if (mRootFirst) {
			if (handleMessageImpl(id, in, out)) {
				return true;
			}
		}
		for (State state : mSubStates) {
			if (state.handleMessage(id, in, out)) {
				return true;
			}
		}
		if (!mRootFirst) {
			if (handleMessageImpl(id, in, out)) {
				return true;
			}
		}
		return false;
	}

	public abstract boolean handleMessageImpl(int id, Param in, Param out);
}
